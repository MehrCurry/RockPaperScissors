package de.gzockoll.rps.boundary;

import com.jayway.restassured.RestAssured;
import de.gzockoll.rps.RockPaperScissorsApplication;
import de.gzockoll.rps.control.GameController;
import de.gzockoll.rps.domain.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;

/**
 * Created by guido on 22.06.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(RockPaperScissorsApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class MatchResourceIT {
    @Value("${local.server.port}")
    private int port;

    @Autowired
    private GameController gameController;
    private Game game;

    @Before
    public void setUp() {
        RestAssured.port = port;
        this.game = gameController.createStandardGame();
    }

    @Test
    public void testNonExistingGame() {

        given()
                .contentType("application/json").
                body(createJsonRequest("not existing", "not existing")).
                when()
                .post("/match")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("exception", containsString("UnknownGameException"));
    }

    @Test
    public void testNonExistingChoice() {
        given()
                .contentType("application/json").
                body(createJsonRequest(game.getId(), "not existing")).
                when()
                .post("/match")
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .body("exception", containsString("IllegalChoiceException"))
                .body("message", containsString("Your choice is invalid in this game. Choose one of:"));
    }

    @Test
    public void testValidChoice() {
        given()
                .contentType("application/json").
                body(createJsonRequest(game.getId(), "Schere")).
                when()
                .post("/match")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("result", anyOf(is("WIN"), is("LOOSE"), is("DRAW")));
    }

    private String createJsonRequest(String gameId, String choice) {
        return String.format("{\"gameId\":\"%s\", \"choice\":\"%s\"}", gameId, choice);
    }
}