package de.gzockoll.rps.domain;

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
            return Game.createStandardGame();
        }
    }, EXTENDED {
        @Override
        public Game createGame() {
            return Game.createExtendedGame();
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
