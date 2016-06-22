package de.gzockoll.rps.control;

import de.gzockoll.rps.boundary.ResultTO;
import de.gzockoll.rps.domain.Game;
import de.gzockoll.rps.domain.GameRepository;
import de.gzockoll.rps.domain.GameType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by guido on 21.06.16.
 */
public class GameControllerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private GameRepository gameRepository;
    private GameController gameController;
    private Game game;

    @Before
    public void setUp() {
        gameRepository = mock(GameRepository.class);
        gameController = new GameController(gameRepository);
        game = gameController.createGame(GameType.STANDARD);
        when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));
    }

    @Test
    public void createStandardGame() throws Exception {
        game = gameController.createGame(GameType.STANDARD);
        verify(gameRepository, times(1)).save(eq(game));
    }

    @Test
    public void testMakeLegalMatch() {
        ResultTO result = gameController.makeMatch(game.getId(), "Schere");
        assertThat(result.getGameId()).isEqualTo(game.getId());
        assertThat(result.getYourChoice()).isEqualTo("Schere");
        assertThat(result.getOpponentsChoice()).isNotEmpty();
        assertThat(result.getResult()).isNotNull();
    }

    @Test
    public void testMakeMatchWithUnknownGame() {
        when(gameRepository.findById(anyString())).thenReturn(Optional.empty());
        thrown.expect(GameController.UnknownGameException.class);
        ResultTO result = gameController.makeMatch("unknown", "Schere");

    }

    @Test
    public void testMakeMatchWithUnknownChoice() {
        thrown.expect(GameController.IllegalChoiceException.class);
        ResultTO result = gameController.makeMatch(game.getId(), "junit");
    }

}