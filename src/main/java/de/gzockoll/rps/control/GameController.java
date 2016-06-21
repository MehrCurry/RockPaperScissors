package de.gzockoll.rps.control;

import de.gzockoll.rps.boundary.ResultTO;
import de.gzockoll.rps.domain.Choice;
import de.gzockoll.rps.domain.DumpRobot;
import de.gzockoll.rps.domain.Game;
import de.gzockoll.rps.domain.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class GameController {
    private GameRepository gameRepository;

    @Autowired
    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createStandardGame() {
        Game game = Game.createStandardGame();
        gameRepository.save(game);
        return game;
    }

    public ResultTO makeMatch(String gameId, String aChoicesName) {
        Game game = gameRepository.findById(gameId).orElseThrow(NoSuchElementException::new);
        Choice choice = game.getChoiceByName(aChoicesName).orElseThrow(NoSuchElementException::new);
        Choice opponentsChoice = DumpRobot.makeYourChoice(game);
        return ResultTO.builder()
                .gameId(game.getId())
                .yourChoice(choice.getName())
                .opponentsChoice(opponentsChoice.getName())
                .result(game.match(choice, opponentsChoice))
                .build();
    }
}
