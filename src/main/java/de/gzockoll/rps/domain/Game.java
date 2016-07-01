package de.gzockoll.rps.domain;

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
 *   class DumpRobot {
 *       {static} +makeYourChoice(game:Game) : Choice
 *   }
 *   class Game {
 *       {static} +createGame(type:GameType) : Game
 *       +match(c1:Choice,c2:Choice) : GameResult
 *       +getChoiceByName(name:String) : Optional<Choice>
 *       +save(game:Game)
 *   }
 *   enum GameType {
 *      STANDARD
 *      EXTENDED
 *   }
 *   enum GameResult {
 *      WIN
 *      LOOSE
 *      DRAW
 *   }
 * Game --> "*" Choice
 * Game ..> GameType
 * Game ..> GameResult
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
