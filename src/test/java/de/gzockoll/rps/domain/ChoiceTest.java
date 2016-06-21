package de.gzockoll.rps.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static de.gzockoll.rps.domain.GameResult.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by guido on 21.06.16.
 */
public class ChoiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Choice schere;
    private Choice stein;
    private Choice papier;

    @Before
    public void setUp() {
        schere = new Choice("Schere");
        stein = new Choice("Stein");
        papier = new Choice("Papier");
        schere.beats(papier);
        papier.beats(stein);
        stein.beats(schere);
    }

    @Test
    public void testLoose() {
        assertThat(papier.matchAgains(schere)).isEqualTo(LOOSE);
        assertThat(schere.matchAgains(stein)).isEqualTo(LOOSE);
        assertThat(stein.matchAgains(papier)).isEqualTo(LOOSE);
    }

    @Test
    public void testWin() {
        assertThat(schere.matchAgains(papier)).isEqualTo(WIN);
        assertThat(stein.matchAgains(schere)).isEqualTo(WIN);
        assertThat(papier.matchAgains(stein)).isEqualTo(WIN);
    }

    @Test
    public void testDraw() {
        assertThat(schere.matchAgains(schere)).isEqualTo(DRAW);
        assertThat(stein.matchAgains(stein)).isEqualTo(DRAW);
        assertThat(papier.matchAgains(papier)).isEqualTo(DRAW);
    }

    @Test
    public void testMatchAgainst() {
        Choice c1=new Choice("c1");
        Choice c2=new Choice("c2");
        assertThat(c1.matchAgains(c2)).isEqualTo(LOOSE);
        assertThat(c2.matchAgains(c1)).isEqualTo(LOOSE);
        assertThat(c1.matchAgains(c1)).isEqualTo(DRAW);
    }

    @Test
    public void testContradiconaryRules() {
        Choice c1=new Choice("c1");
        Choice c2=new Choice("c2");
        c1.beats(c2);
        thrown.expect(IllegalStateException.class);
        c2.beats(c1);
    }

    @Test
    public void testSelfReference() {
        Choice c1=new Choice("c1");
        thrown.expect(IllegalArgumentException.class);
        c1.beats(c1);
    }

    @Test
    public void testIsNamedLike() {
        Choice c = new Choice("junit");
        assertThat(c.isNamedLike("junit")).isTrue();
        assertThat(c.isNamedLike("Junit")).isTrue();
        assertThat(c.isNamedLike("Test")).isFalse();
    }

}