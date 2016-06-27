package de.gzockoll.rps.boundary;

import de.gzockoll.rps.control.GameController;
import de.gzockoll.rps.domain.Game;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by guido on 21.06.16.
 */
@RestController
@RequestMapping("/match")
public class MatchResource {
    @Autowired
    private GameController gameController;

    @ApiOperation(value = "creates a single match",
            notes = "The match is transient and will be destroyed after the request",
            response = ResultTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create game", response = Game.class),
            @ApiResponse(code = 404, message = "Your referenced an game which could not be found."),
            @ApiResponse(code = 422, message = "Your have given a choice value which is not valid in the referenced game"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = POST)
    public ResultTO singleMatch(@RequestBody MatchRequestTO request) {
        return gameController.makeMatch(request.getGameId(), request.getChoice());
    }
}
