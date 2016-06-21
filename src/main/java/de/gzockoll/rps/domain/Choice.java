package de.gzockoll.rps.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static de.gzockoll.rps.domain.GameResult.*;

@EqualsAndHashCode(exclude = "loosers")
@RequiredArgsConstructor
public class Choice {
    private final String name;
    private Collection<Choice> loosers = new HashSet<>();

    public void beats(Choice ... others) {
        checkArgument(!Arrays.asList(others).contains(this),"Loosing against itself is not possible");
        Arrays.stream(others).forEach(c -> checkState(!c.loosers.contains(this),"Contradictionary Rules"));
        Collections.addAll(loosers,others);
    }

    public GameResult matchAgains(Choice other) {
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
