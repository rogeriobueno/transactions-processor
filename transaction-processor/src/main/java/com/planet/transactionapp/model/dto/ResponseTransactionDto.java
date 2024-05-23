package com.planet.transactionapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "Transaction")
@JsonPropertyOrder({"id", "amount", "currency", "timestamp", "recipientName", "recipientEmail", "recipientPhone", "creditNumber", "creditName"})
@Schema(name = "TransactionResponse",
        description = "Transaction information")
public class ResponseTransactionDto extends RepresentationModel<ResponseTransactionDto> {
    @JsonProperty("id")
    @Schema(description = "Unique identifier of the transaction", example = "123")
    private Long id;
    @JsonProperty("amount")
    @Schema(description = "Amount of the transaction", example = "100.00")
    private BigDecimal amount;
    @JsonProperty("currency")
    @Schema(description = "Currency of the transaction", example = "USD")
    private String currency;
    @JsonProperty("timestamp")
    @Schema(description = "Timestamp of the transaction", example = "2022-01-01T00:00:00Z")
    private ZonedDateTime timestamp;

    @JsonProperty("recipientName")
    @Schema(description = "Name of the recipient", example = "John Doe")
    private String recipientName;
    @JsonProperty("recipientEmail")
    @Schema(description = "Email of the recipient", example = "john.doe@example.com")
    private String recipientEmail;
    @JsonProperty("recipientPhone")
    @Schema(description = "Phone number of the recipient", example = "+1234567890")
    private String recipientPhone;

    @JsonProperty("creditCardNumber")
    @Schema(description = "Credit card number", example = "4111111111111111")
    private String creditNumber;
    @JsonProperty("creditCardName")
    @Schema(description = "Name on the credit card", example = "John Doe")
    private String creditName;

}
