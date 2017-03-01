package de.gzockoll.rps.boundary;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.IsNot.not;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameResourceIT {
    @Value("${local.server.port}")
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testCreateGame() {
        // @formatter:off
        given()
                .contentType(ContentType.JSON).
        when()
                .post("/game").
        then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", not(isEmptyString()));
        // @formatter:on
    }

    @Test
    public void testCreateStandardGameWithRequenstObject() {
        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .body("{ \"type\": \"standard\"}").
        when()
                .post("/game").
        then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", not(isEmptyString()));
        // @formatter:on
    }

    @Test
    public void testCreateExtendedGameWithRequenstObject() {
        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .body("{ \"type\": \"extended\"}").
        when()
                .post("/game").
        then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", not(isEmptyString()));
        // @formatter:on
    }

    @Test
    public void testCreateInvalidGameWithRequenstObject() {
        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .body("{ \"type\": \"invalid\"}").
        when()
                .post("/game").
        then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        // @formatter:on
    }
}