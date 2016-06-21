package de.gzockoll.rps.boundary;

import de.gzockoll.rps.control.GameController;
import de.gzockoll.rps.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by guido on 21.06.16.
 */
@RestController
@RequestMapping("/game")
public class GameBoundary {
    @Autowired
    private GameController gameController;

    @RequestMapping(method = RequestMethod.POST)
    public Game createGame() {
        return gameController.createStandardGame();
    }
}
