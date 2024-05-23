package com.planet.transactionapp.model.mappers;

import com.planet.transactionapp.model.dto.XmlTransactionDto;
import com.planet.transactionapp.model.entities.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class XmlTransactionMapper {

    public XmlTransactionDto toRecord(Transaction entity) {
        if (entity == null) {
            return null;
        }

        return XmlTransactionDto.builder()
                                .timestamp(entity.getCreatedAt())
                                .id(entity.getId())
                                .amount(entity.getAmount())
                                .currency(entity.getCurrency())
                                .recipientName(entity.getRecipientName())
                                .recipientEmail(entity.getRecipientEmail())
                                .recipientPhone(entity.getRecipientPhone())
                                .creditNumber(entity.getCreditNumber())
                                .creditName(entity.getCreditName())
                                .creditExpiryDate(entity.getCreditExpiryDate())
                                .creditCvv(entity.getCreditCvv()).build();
    }

    public List<XmlTransactionDto> toRecord(Iterable<Transaction> entities) {
        if (entities == null) {
            return null;
        }
        return StreamSupport.stream(entities.spliterator(), false)
                            .map(this::toRecord)
                            .collect(Collectors.toList());
    }

    public Transaction fromRecord(XmlTransactionDto dto) {
        if (dto == null) {
            return null;
        }

        return Transaction.builder()
                          .createdAt(dto.getTimestamp())
                          .amount(dto.getAmount())
                          .currency(dto.getCurrency())
                          .recipientName(dto.getRecipientName())
                          .recipientEmail(dto.getRecipientEmail())
                          .recipientPhone(dto.getRecipientPhone())
                          .creditNumber(dto.getCreditNumber())
                          .creditName(dto.getCreditName())
                          .creditExpiryDate(dto.getCreditExpiryDate())
                          .creditCvv(dto.getCreditCvv()).build();
    }

    public List<Transaction> fromRecord(Iterable<XmlTransactionDto> dtos) {
        if (dtos == null) {
            return null;
        }
        return StreamSupport.stream(dtos.spliterator(), false)
                            .map(this::fromRecord)
                            .collect(Collectors.toList());
    }
}
