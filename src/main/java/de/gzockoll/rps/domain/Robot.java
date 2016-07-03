package de.gzockoll.rps.domain;

/**
 * Created by guido on 03.07.16.
 */
public interface Robot {
    /**
     * @param game which holds the possible choices
     * @return a choice taken from the possible choices
     */
    Choice makeYourChoice(Game game);
}
