package com.planet.transactionapp.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "AMOUNT", nullable = false, columnDefinition = "Decimal(35,13) default 0.0")
    private BigDecimal amount;
    @Column(name = "CURRENCY", nullable = false, length = 3)
    private String currency;
    @Column(name = "CREATED_AT", nullable = false, length = 20)
    private ZonedDateTime createdAt;

    @Column(name = "RECIPIENT_NAME", nullable = false, length = 20)
    private String recipientName;
    @Column(name = "RECIPIENT_EMAIL", length = 100)
    private String recipientEmail;
    @Column(name = "RECIPIENT_PHONE", length = 20)
    private String recipientPhone;
    @Column(name = "CC_NUMBER", nullable = false, length = 150)
    private String creditNumber;
    @Column(name = "CC_NAME", nullable = false, length = 200)
    private String creditName;
    @Column(name = "CC_EXPIRE_DATE", nullable = false, length = 100)
    private String creditExpiryDate;
    @Column(name = "CC_CVV", length = 50)
    private String creditCvv;

}
