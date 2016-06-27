package de.gzockoll.rps.boundary;

import de.gzockoll.rps.control.GameController;
import de.gzockoll.rps.domain.Game;
import de.gzockoll.rps.domain.GameType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by guido on 23.06.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameResourceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private GameController gameController;

    @Mock
    private Game game;

    @InjectMocks
    private GameResource cut;

    @Test
    public void createStandardGame() throws Exception {
        GameRequestTO request = new GameRequestTO("standard");
        when(gameController.createGame(any())).thenReturn(game);
        Game result = cut.createGame(request);
        assertThat(result).isEqualTo(game);
        verify(gameController, times(1)).createGame(any());
    }

    @Test
    public void createExtendedGame() throws Exception {
        GameRequestTO request = new GameRequestTO("extenDeD");
        when(gameController.createGame(any())).thenReturn(game);
        Game result = cut.createGame(request);
        assertThat(result).isEqualTo(game);
        verify(gameController, times(1)).createGame(any());
    }

    @Test
    public void createIllegalGame() throws Exception {
        GameRequestTO request = new GameRequestTO("junit");
        thrown.expect(GameType.UnknownGameTypeException.class);
        Game result = cut.createGame(request);
    }

    @Test
    public void checkArgumentWithTrue() throws Exception {
        cut.checkArgument(true, "junit");
        // No Exception Expected
    }

    @Test
    public void checkArgumentWithFalse() throws Exception {
        thrown.expect(GameType.UnknownGameTypeException.class);
        thrown.expectMessage("Junit");
        cut.checkArgument(false, "Junit");
    }

}