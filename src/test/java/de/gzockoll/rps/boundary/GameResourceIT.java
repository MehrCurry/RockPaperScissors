package de.gzockoll.rps.boundary;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import de.gzockoll.rps.RockPaperScissorsApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by guido on 22.06.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(RockPaperScissorsApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class GameResourceIT {
    @Value("${local.server.port}")
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testCreateGame() {
        given().contentType(ContentType.JSON).
        when()
                .post("/game").
                then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", not(isEmptyString()));
    }

    @Test
    public void testCreateStandardGameWithRequenstObject() {
        given().contentType(ContentType.JSON)
                .body("{ \"type\": \"standard\"}").
                when()
                .post("/game")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", not(isEmptyString()));
    }

    @Test
    public void testCreateExtendedGameWithRequenstObject() {
        given().contentType(ContentType.JSON)
                .body("{ \"type\": \"extended\"}").
                when()
                .post("/game")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", not(isEmptyString()));
    }

    @Test
    public void testCreateInvalidGameWithRequenstObject() {
        given().contentType(ContentType.JSON)
                .body("{ \"type\": \"invalid\"}").
                when()
                .post("/game")
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
}