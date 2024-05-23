package com.planet.transactionapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStorageIOException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public FileStorageIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStorageIOException(String message) {
        super(message);
    }
}
