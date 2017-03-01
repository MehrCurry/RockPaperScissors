package de.gzockoll.rps.boundary;

import com.jayway.restassured.RestAssured;
import de.gzockoll.rps.control.GameController;
import de.gzockoll.rps.domain.Game;
import de.gzockoll.rps.domain.GameType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MatchResourceIT {
    @Value("${local.server.port}")
    private int port;

    @Autowired
    private GameController gameController;
    private Game game;

    @Before
    public void setUp() {
        RestAssured.port = port;
        this.game = gameController.createGame(GameType.STANDARD);
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
                .statusCode(HttpStatus.CREATED.value())
                .body("result", anyOf(is("WIN"), is("LOOSE"), is("DRAW")));
    }

    private String createJsonRequest(String gameId, String choice) {
        return String.format("{\"gameId\":\"%s\", \"choice\":\"%s\"}", gameId, choice);
    }
}