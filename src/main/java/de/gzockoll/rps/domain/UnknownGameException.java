package de.gzockoll.rps.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by guido on 23.06.16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnknownGameException extends IllegalArgumentException {
    public UnknownGameException(String message) {
        super(message);
    }
}
