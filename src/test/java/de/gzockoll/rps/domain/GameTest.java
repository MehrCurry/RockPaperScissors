package de.gzockoll.rps.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static de.gzockoll.rps.domain.GameResult.LOOSE;
import static de.gzockoll.rps.domain.GameResult.WIN;
import static de.gzockoll.rps.domain.GameType.EXTENDED;
import static de.gzockoll.rps.domain.GameType.STANDARD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by guido on 21.06.16.
 */
public class GameTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getChoices() throws Exception {
        Game game = new Game(Collections.emptySet());
        assertThat(game.getChoices()).hasSize(0);

        game = STANDARD.createGame();
        assertThat(game.getChoices()).hasSize(3);
    }

    @Test
    public void testUnmodifiable() throws Exception {
        Game game = STANDARD.createGame();
        Collection<Choice> choices = game.getChoices();
        Choice c = new Choice("junit");
        thrown.expect(UnsupportedOperationException.class);
        choices.add(c);
    }

    @Test
    public void testIllegalChoice() throws Exception {
        Game game = STANDARD.createGame();
        Collection<Choice> choices = game.getChoices();
        Choice c = new Choice("junit");
        thrown.expect(IllegalArgumentException.class);
        game.match(choices.iterator().next(), c);
    }

    @Test
    public void testGetChoiceByName() {
        Game game = STANDARD.createGame();
        Optional<Choice> schere = game.getChoiceByName("schere");
        assertThat(schere).isPresent();
        Optional<Choice> brunnen = game.getChoiceByName("Brunnen");
        assertThat(brunnen).isNotPresent();
    }

    @Test
    public void testAlternateGame() {
        Game game = EXTENDED.createGame();
        assertThat(game.getChoices()).hasSize(4);
        Choice brunnen = game.getChoiceByName("brunnen").get();
        Choice papier = game.getChoiceByName("papier").get();
        Choice schere = game.getChoiceByName("schere").get();

        assertThat(game.match(brunnen, papier)).isEqualTo(LOOSE);
        assertThat(game.match(brunnen, schere)).isEqualTo(WIN);
    }

    @Test
    public void testValidationOfNullChoices() {
        GameRepository repository = mock(GameRepository.class);
        Game game = new Game(null);
        thrown.expect(IllegalStateException.class);
        game.save(repository);
    }

    @Test
    public void testValidationOfEmptryChoices() {
        GameRepository repository = mock(GameRepository.class);
        Game game = new Game(Collections.emptySet());
        thrown.expect(IllegalStateException.class);
        game.save(repository);
    }

    @Test
    public void testCreateByType() {
        Game game = Game.createGame(STANDARD);
        assertThat(game.getChoices()).hasSize(3);

        game = Game.createGame(GameType.EXTENDED);
        assertThat(game.getChoices()).hasSize(4);
    }
}