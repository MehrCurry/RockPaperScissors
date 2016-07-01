package de.gzockoll.rps.domain;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * = Game Class
 *
 * This class acts as a bounded context in a DDD setup.
 *
 *
 * [plantuml]
 * ....
 * package "domain" {
 *   class Game {
 *       {static} +createGame(type) : Game
 *       +match(Choice c1,Choice c2) : GameResult
 *       +getChoiceByName(String name) : Optional<Choice>
 *       +save(Game game)
 *   }
 * Game -->* Choice
 * DumpRobot ..> Game
 * DumpRobot ..> Choice
 * }
 * ....
 * [plantuml]
 * ....
 *
 * actor Client
 * activate Client
 * create Game
 * Client -> Game : createGame(type)
 * Game --> Client : game
 * Client -> Game : match(choice1,choice2)
 * Game --> Client : result
 * ....
 *
 * NOTE: Created by guido on 21.06.16.
 */
public class Game {
    private Collection<Choice> choices = new HashSet<>();
    @Getter
    private String id = UUID.randomUUID().toString();

    public Game(Collection<Choice> choices) {
        this.choices = choices;
    }

    public static Game createGame(GameType type) {
        return type.createGame();
    }

    public static Game createStandardGame() {
        Choice schere = new Choice("Schere");
        Choice stein = new Choice("Stein");
        Choice papier = new Choice("Papier");
        schere.beats(papier);
        papier.beats(stein);
        stein.beats(schere);
        return new Game(ImmutableSet.of(schere, stein, papier));
    }

    public static Game createExtendedGame() {
        Choice schere = new Choice("Schere");
        Choice stein = new Choice("Stein");
        Choice papier = new Choice("Papier");
        Choice brunnen = new Choice("Brunnen");
        schere.beats(papier);
        papier.beats(stein, brunnen);
        stein.beats(schere);
        brunnen.beats(schere, stein);
        return new Game(ImmutableSet.of(schere, stein, papier, brunnen));
    }

    public Collection<Choice> getChoices() {
        return Collections.unmodifiableCollection(choices);
    }

    public GameResult match(Choice c1, Choice c2) {
        checkArgument(choices.contains(c1), "Illegal Choice");
        checkArgument(choices.contains(c2), "Illegal Choice");
        return c1.matchAgains(c2);
    }

    public Optional<Choice> getChoiceByName(String aName) {
        return choices.stream().filter(c -> c.isNamedLike(aName)).findFirst();
    }

    public void save(GameRepository gameRepository) {
        checkState(choices != null, "You have to initialize the game properly");
        checkState(!choices.isEmpty(), "You must have at least one choice for the simplest game");
        gameRepository.save(this);
    }
}
