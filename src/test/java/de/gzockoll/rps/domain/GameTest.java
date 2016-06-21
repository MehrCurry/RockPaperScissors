package de.gzockoll.rps.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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

        game = Game.createStandardGame();
        assertThat(game.getChoices()).hasSize(3);
    }

    @Test
    public void testUnmodifiable() throws Exception {
        Game game = Game.createStandardGame();
        Collection<Choice> choices = game.getChoices();
        Choice c = new Choice("junit");
        thrown.expect(UnsupportedOperationException.class);
        choices.add(c);
    }

    @Test
    public void testIllegalChoice() throws Exception {
        Game game = Game.createStandardGame();
        Collection<Choice> choices = game.getChoices();
        Choice c = new Choice("junit");
        thrown.expect(IllegalArgumentException.class);
        game.match(choices.iterator().next(), c);
    }

    @Test
    public void testGetChoiceByName() {
        Game game = Game.createStandardGame();
        Optional<Choice> schere = game.getChoiceByName("schere");
        assertThat(schere).isPresent();
        Optional<Choice> brunnen = game.getChoiceByName("Brunnen");
        assertThat(brunnen).isNotPresent();
    }
}