package com.planet.transactionapp.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "ExceptionResponse",
        description = "Schema to hold error response information")
@JsonPropertyOrder(value = {"timestamp", "message", "details"})
public record ResponseExceptionDTO(
        @Schema(description = "Date and Time when the error occurred")
        LocalDateTime timestamp,
        @Schema(description = "Message representing the error")
        String message,
        @Schema(description = "Detail representing the error")
        String details

) {
}
