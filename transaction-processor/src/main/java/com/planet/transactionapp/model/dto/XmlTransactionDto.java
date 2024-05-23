package com.planet.transactionapp.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("Transaction")
public class XmlTransactionDto {

    private Long id;
    private BigDecimal amount;
    private String currency;
    private ZonedDateTime timestamp;
    private String recipientName;
    private String recipientEmail;
    private String recipientPhone;
    private String creditNumber;
    private String creditName;
    private String creditExpiryDate;
    private String creditCvv;

}
