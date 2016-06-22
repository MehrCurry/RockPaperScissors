package de.gzockoll.rps.control;

import de.gzockoll.rps.RockPaperScissorsApplication;
import de.gzockoll.rps.domain.Game;
import de.gzockoll.rps.domain.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by guido on 21.06.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(RockPaperScissorsApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class GameControllerIT {
    @Autowired
    private GameController gameController;
    @Autowired
    private GameRepository gameRepository;

    @Test
    public void createStandardGame() throws Exception {
        Game game = gameController.createStandardGame();
        assertThat(game).isNotNull();
        assertThat(gameRepository.findById(game.getId()).get()).isEqualTo(game);
    }

}