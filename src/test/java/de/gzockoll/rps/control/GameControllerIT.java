package de.gzockoll.rps.control;

import de.gzockoll.rps.domain.Game;
import de.gzockoll.rps.domain.GameRepository;
import de.gzockoll.rps.domain.GameType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)public class GameControllerIT {
    @Autowired
    private GameController gameController;
    @Autowired
    private GameRepository gameRepository;

    @Test
    public void createStandardGame() throws Exception {
        Game game = gameController.createGame(GameType.STANDARD);
        assertThat(game).isNotNull();
        assertThat(gameRepository.findById(game.getId()).get()).isEqualTo(game);
    }

}