package de.gzockoll.rps.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by guido on 22.06.16.
 */
@ControllerAdvice
class GenericExceptionHandler {
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException() {

    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalStateException.class)
    public void handleIllegalStateException() {

    }

}
