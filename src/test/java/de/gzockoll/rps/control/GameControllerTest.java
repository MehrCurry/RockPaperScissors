package de.gzockoll.rps.control;

import de.gzockoll.rps.domain.Game;
import de.gzockoll.rps.domain.GameRepository;
import org.junit.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by guido on 21.06.16.
 */
public class GameControllerTest {
    @Test
    public void createStandardGame() throws Exception {
        GameRepository gameRepository = mock(GameRepository.class);
        GameController gameController = new GameController(gameRepository);
        Game game = gameController.createStandardGame();
        verify(gameRepository, times(1)).save(eq(game));
    }
}