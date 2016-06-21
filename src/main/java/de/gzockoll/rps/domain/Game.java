package de.gzockoll.rps.domain;

import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by guido on 21.06.16.
 */
public class Game {
    private Collection<Choice> choices = new HashSet<>();

    public Game(Collection<Choice> choices) {
        this.choices = choices;
    }

    public static Game createStandardGame() {
        Choice schere = new Choice("Schere");
        Choice stein = new Choice("Stein");
        Choice papier = new Choice("Papier");
        schere.beats(papier);
        papier.beats(stein);
        stein.beats(schere);
        return new Game(ImmutableSet.of(schere, stein, papier));
    }

    public Collection<Choice> getChoices() {
        return Collections.unmodifiableCollection(choices);
    }

    public GameResult match(Choice c1, Choice c2) {
        checkArgument(choices.contains(c1), "Illegal Choice");
        checkArgument(choices.contains(c2), "Illegal Choice");
        return c1.matchAgains(c2);
    }

    public Optional<Choice> getChoiceByName(String aName) {
        return choices.stream().filter(c -> c.isNamedLike(aName)).findFirst();
    }
}
