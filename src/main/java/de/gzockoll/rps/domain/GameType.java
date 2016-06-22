package de.gzockoll.rps.domain;

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

    public abstract Game createGame();
}
