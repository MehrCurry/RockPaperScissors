package de.gzockoll.rps.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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
     * Initialzes the loosers list.
     * NOTE: It take care not to setup contradictonary rules
     *
     * @param others Array of other choices that will loose against this
     */
    void beats(Choice... others) {
        checkArgument(!Arrays.asList(others).contains(this),"Loosing against itself is not possible");
        Arrays.stream(others).forEach(c -> checkState(!c.isBeating(this), "Contradictionary Rules"));
        Collections.addAll(loosers,others);
    }

    GameResult matchAgainst(Choice other) {
        if (this.equals(other)) {
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

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public static class IllegalChoiceException extends IllegalArgumentException {
        public IllegalChoiceException(String message) {
            super(message);
        }
    }
}
