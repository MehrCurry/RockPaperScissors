package de.gzockoll.rps.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Makes a random choice out of the games possible choices.
 */
public class DumpRobot {

    public static Choice makeYourChoice(Game game) {
        checkArgument(!game.getChoices().isEmpty());
        List<Choice> choices = new ArrayList(game.getChoices());
        Collections.shuffle(choices);
        return choices.iterator().next();
    }
}
