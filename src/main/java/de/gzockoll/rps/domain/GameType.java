package de.gzockoll.rps.domain;

import com.google.common.collect.ImmutableSet;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

/**
 * A simple enum based factory
 * <p>
 * Created by guido on 22.06.16.
 */
public enum GameType {
    STANDARD {
        @Override
        public Game createGame() {
            Choice schere = new Choice("Schere");
            Choice stein = new Choice("Stein");
            Choice papier = new Choice("Papier");
            schere.beats(papier);
            papier.beats(stein);
            stein.beats(schere);
            return new Game(ImmutableSet.of(schere, stein, papier));
        }
    }, EXTENDED {
        @Override
        public Game createGame() {
            Choice schere = new Choice("Schere");
            Choice stein = new Choice("Stein");
            Choice papier = new Choice("Papier");
            Choice brunnen = new Choice("Brunnen");
            schere.beats(papier);
            papier.beats(stein, brunnen);
            stein.beats(schere);
            brunnen.beats(schere, stein);
            return new Game(ImmutableSet.of(schere, stein, papier, brunnen));
        }
    };

    public static String allowedTypes() {
        return Arrays.stream(values()).map(GameType::name).collect(joining(", "));
    }

    public abstract Game createGame();

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public static class UnknownGameTypeException extends ApplicationException {
        public UnknownGameTypeException(String message) {
            super(message);
        }
    }
}
