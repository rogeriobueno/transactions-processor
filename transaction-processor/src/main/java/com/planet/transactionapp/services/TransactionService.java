package com.planet.transactionapp.services;

import com.planet.transactionapp.model.dto.ResponseCountryAmountDto;
import com.planet.transactionapp.model.dto.ResponseTransactionDto;
import com.planet.transactionapp.model.dto.XmlTransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionService {
    void saveAll(List<XmlTransactionDto> transactions);

    @Transactional(readOnly = true)
    List<ResponseCountryAmountDto> findAmountByRecipient(String recipientName);

    @Transactional(readOnly = true)
    Page<ResponseTransactionDto> findAllByCreditNumberPageable(String creditCard, Pageable pageable);

    @Transactional(readOnly = true)
    ResponseTransactionDto findById(Long id);

    @Transactional(readOnly = true)
    List<ResponseCountryAmountDto> findSumAmountTransactionGroupByCurrency();
}
