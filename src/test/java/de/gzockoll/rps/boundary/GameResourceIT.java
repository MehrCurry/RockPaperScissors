package de.gzockoll.rps.boundary;

import com.jayway.restassured.RestAssured;
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

import static com.jayway.restassured.RestAssured.when;
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
        when()
                .post("/game")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", not(isEmptyString()));
    }
}