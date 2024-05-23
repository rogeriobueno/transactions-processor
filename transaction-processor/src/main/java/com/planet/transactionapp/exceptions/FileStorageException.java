package com.planet.transactionapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FileStorageException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStorageException(String message) {
        super(message);

    }
}
