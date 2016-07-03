package de.gzockoll.rps.control;

import de.gzockoll.rps.boundary.ResultTO;
import de.gzockoll.rps.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class GameController {
    private final Robot robot;
    private final GameRepository gameRepository;

    @Autowired
    public GameController(GameRepository gameRepository, Robot aRobot) {
        this.gameRepository = gameRepository;
        this.robot = aRobot;
    }

    public ResultTO makeMatch(String gameId, String aChoicesName) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new UnknownGameException("The specified game does not exist."));
        Choice choice = game.getChoiceByName(aChoicesName)
                .orElseThrow(() -> new Choice.IllegalChoiceException("Your choice is invalid in this game. Choose one of: "
                        + game.getChoices().stream().map(c -> c.getName()).collect(Collectors.joining(", "))));
        Choice opponentsChoice = game.makeRobotsChoice(robot);
        return ResultTO.builder()
                .gameId(game.getId())
                .yourChoice(choice.getName())
                .opponentsChoice(opponentsChoice.getName())
                .result(game.match(choice, opponentsChoice))
                .build();
    }

    public Game createGame() {
        return createGame(GameType.STANDARD);
    }

    public Game createGame(GameType type) {
        Game game = type.createGame();
        game.save(gameRepository);
        return game;
    }

}
