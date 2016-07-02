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
 * == Class Diagram
 * [plantuml]
 * ....
 * package "domain" {
 *   class Choice {
 *       -name : String
 *       +matchAgainst(other:Choice) : GameResult
 *       +isBeating(other:Choice) : boolean
 *       +isNamedLike(name:String) : boolean
 *   }
 *   class DumpRobot {
 *       {static} +makeYourChoice(game:Game) : Choice
 *   }
 *   class Game {
 *       -id : String
 *       {static} +createGame(type:GameType) : Game
 *       +match(c1:Choice,c2:Choice) : GameResult
 *       +getChoiceByName(name:String) : Optional<Choice>
 *       +save(game:Game)
 *   }
 *   enum GameType {
 *      STANDARD
 *      EXTENDED
 *      +createGame() : Game
 *   }
 *   enum GameResult {
 *      WIN
 *      LOOSE
 *      DRAW
 *   }
 * Game --> "*" Choice : possibleChoices >
 * Game ..> GameType
 * Game ..> GameResult
 * DumpRobot ..> Game
 * DumpRobot ..> Choice
 * Choice --> "*" Choice : loosers >
 * }
 * ....
 *
 * == Sequence Diagram
 * [plantuml]
 * ....
 *
 * actor Client
 * participant GameType << enum >>
 * participant Game
 * participant Choice
 *
 * Client -> GameType : createGame()
 * note right
 *   GameType is an enum which implements
 *   an abstract factory pattern inside.
 *   Many of the GoF patterns can be implemented
 *   inside a java enum as some sort of a lightweight
 *   implementation.
 * end note
 * activate GameType
 *
 * create Choice
 * GameType -> Choice : new Choice()
 * activate Choice
 *
 * Choice --> GameType
 * deactivate Choice
 *
 * create Game
 * GameType -> Game : new Game()
 * activate Game
 * Game --> GameType : game
 * deactivate Game
 *
 * GameType --> Client : game
 * deactivate GameType
 *
 * Client -> Game : match(choice1,choice2)
 * activate Game
 *
 * Game -> Choice : matchAgainst(choice2)
 * activate Choice
 *
 * Choice -> Choice : isBeating(choice2)
 * activate Choice
 * deactivate Choice
 * Choice --> Game : result
 * deactivate Choice
 * Game --> Client : result
 * deactivate Game
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
        return c1.matchAgainst(c2);
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
