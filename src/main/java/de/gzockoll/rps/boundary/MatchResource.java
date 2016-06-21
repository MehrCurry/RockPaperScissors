package de.gzockoll.rps.boundary;

import de.gzockoll.rps.control.GameController;
import de.gzockoll.rps.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by guido on 21.06.16.
 */
@RestController
@RequestMapping("/match")
public class MatchResource {
    @Autowired
    private GameController gameController;

    @RequestMapping(method = POST)
    public
    @ResponseBody
    ResultTO singleMatch(@RequestBody MatchRequest request) {
        return gameController.makeMatch(request.getGameId(), request.getChoice());
    }

    @RequestMapping(method = GET)
    public
    @ResponseBody
    MatchRequest hurz() {
        Game game = gameController.createStandardGame();
        return MatchRequest.builder().gameId(game.getId()).choice("Stein").build();
    }
}
