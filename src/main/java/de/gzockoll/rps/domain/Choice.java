package de.gzockoll.rps.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static de.gzockoll.rps.domain.GameResult.*;

/**
 * = Choice class
 *
 * This represents a single choice in the game and decides the outcome
 * of a match. To do so it has knowledge about other choices that will
 * loose a match.
 *
 * NOTE: The method names are choosen in a DDD manor so hopefully additional JavaDoc comments are not needed.
 * If someone complains i will add them later (travel light).
 */
@EqualsAndHashCode(exclude = "loosers")
@RequiredArgsConstructor
@ToString(exclude = "loosers")
public class Choice {
    @Getter
    private final String name;
    private Collection<Choice> loosers = new HashSet<>();

    /**
     * Initialzes the looses list.
     * NOTE: It take care not to setup contradictonary rules
     *
     * @param others Array of other choises that will loose against this
     */
    void beats(Choice... others) {
        checkArgument(!Arrays.asList(others).contains(this),"Loosing against itself is not possible");
        Arrays.stream(others).forEach(c -> checkState(!c.loosers.contains(this),"Contradictionary Rules"));
        Collections.addAll(loosers,others);
    }

    GameResult matchAgains(Choice other) {
        if (equals(other)) {
            return DRAW;
        } else {
            return this.isBeating(other) ? WIN : LOOSE;
        }
    }

    public boolean isBeating(Choice other) {
        return (loosers.contains(other));
    }

    public boolean isNamedLike(String aName) {
        return name.equalsIgnoreCase(aName);
    }
}
