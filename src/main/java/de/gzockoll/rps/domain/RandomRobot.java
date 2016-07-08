package de.gzockoll.rps.domain;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Makes a random choice out of the games possible choices.
 */
@Service
public class RandomRobot implements Robot {
    @Override
    public Choice makeYourChoice(Game game) {
        checkArgument(!game.getChoices().isEmpty());
        List<Choice> choices = new ArrayList(game.getChoices());
        Collections.shuffle(choices);
        return choices.iterator().next();
    }
}
