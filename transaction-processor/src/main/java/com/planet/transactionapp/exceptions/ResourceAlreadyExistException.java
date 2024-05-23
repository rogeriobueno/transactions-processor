package com.planet.transactionapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1621458548784641191L;

    public ResourceAlreadyExistException(String exception) {
        super(exception);
    }

    public ResourceAlreadyExistException(String exception, Throwable cause) {
        super(exception, cause);
    }


}
