package de.gzockoll.rps.domain;

/**
 * Created by guido on 23.06.16.
 */
public abstract class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }
}
