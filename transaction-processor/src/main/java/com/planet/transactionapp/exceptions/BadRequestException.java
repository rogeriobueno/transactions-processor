package com.planet.transactionapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 438448514842901239L;

    public BadRequestException(String exception) {
        super(exception);
    }

}
