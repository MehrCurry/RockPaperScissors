package de.gzockoll.rps.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;

import static de.gzockoll.rps.domain.GameType.STANDARD;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by guido on 21.06.16.
 */
public class RandomRobotTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Robot robot = new RandomRobot();

    @Test
    public void makeYourChoice() throws Exception {
        Game game = STANDARD.createGame();
        Choice c = game.makeRobotsChoice(robot);
        assertThat(c).isNotNull();
        assertThat(game.getChoices()).contains(c);
    }

    @Test
    public void emptyGame() throws Exception {
        Game game = new Game(Collections.emptySet());
        thrown.expect(IllegalArgumentException.class);
        Choice c = game.makeRobotsChoice(robot);
    }

}