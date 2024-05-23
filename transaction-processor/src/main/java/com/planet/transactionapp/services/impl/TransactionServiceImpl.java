package com.planet.transactionapp.services.impl;

import com.planet.transactionapp.exceptions.ResourceNotFoundException;
import com.planet.transactionapp.model.dto.ResponseCountryAmountDto;
import com.planet.transactionapp.model.dto.ResponseTransactionDto;
import com.planet.transactionapp.model.dto.XmlTransactionDto;
import com.planet.transactionapp.model.entities.Transaction;
import com.planet.transactionapp.model.mappers.ResponseTransactionMapper;
import com.planet.transactionapp.model.mappers.XmlTransactionMapper;
import com.planet.transactionapp.repository.TransactionRepository;
import com.planet.transactionapp.services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final ResponseTransactionMapper responseTransactionMapper;
    private final XmlTransactionMapper xmlTransactionMapper;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseTransactionDto findById(Long id) {
        return transactionRepository.findById(id)
                                    .map(responseTransactionMapper::toRecord)
                                    .orElseThrow(
                                            () -> new ResourceNotFoundException("Transaction not found with id " + id));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAll(List<XmlTransactionDto> transactions) {
        transactionRepository.saveAll(xmlTransactionMapper.fromRecord(transactions));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseCountryAmountDto> findAmountByRecipient(final String recipientName) {
        return transactionRepository.findSumAmountGroupByCurrencyByRecipientName(
                recipientName);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseTransactionDto> findAllByCreditNumberPageable(final String creditCardNumber, final Pageable pageable) {
        Page<Transaction> transactionPage = transactionRepository.findAllByCreditNumber(creditCardNumber, pageable);
        return transactionPage.map(responseTransactionMapper::toRecord);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ResponseCountryAmountDto> findSumAmountTransactionGroupByCurrency() {
        return transactionRepository.findSumAmountGroupByCurrency();
    }

}
