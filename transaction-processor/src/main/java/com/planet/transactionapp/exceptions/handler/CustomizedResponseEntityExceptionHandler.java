package com.planet.transactionapp.exceptions.handler;


import com.planet.transactionapp.exceptions.BadRequestException;
import com.planet.transactionapp.exceptions.FileStorageException;
import com.planet.transactionapp.exceptions.ResourceNotFoundException;
import com.planet.transactionapp.model.dto.ResponseExceptionDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Slf4j
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler {


    @ExceptionHandler({Exception.class, FileUploadIOException.class})
    public final ResponseEntity<ResponseExceptionDTO> handleAllExceptions(Exception ex, WebRequest request) {
        ResponseExceptionDTO responseExceptionDTO =
                new ResponseExceptionDTO(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(responseExceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        String errorMessage = ex.getConstraintViolations().stream()
                                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                                .collect(Collectors.joining(", "));
        ResponseExceptionDTO responseExceptionDTO =
                new ResponseExceptionDTO(
                        LocalDateTime.now(),
                        errorMessage,
                        request.getDescription(false));
        return new ResponseEntity<>(responseExceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadRequestException.class, FileStorageException.class})
    public final ResponseEntity<ResponseExceptionDTO> handleBadRequestExceptions(Exception ex, WebRequest request) {
        ResponseExceptionDTO responseExceptionDTO =
                new ResponseExceptionDTO(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(responseExceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ResponseExceptionDTO> handleNotResourceFoundException(Exception ex, WebRequest request) {
        ResponseExceptionDTO responseExceptionDTO =
                new ResponseExceptionDTO(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(responseExceptionDTO, HttpStatus.NOT_FOUND);
    }

}
