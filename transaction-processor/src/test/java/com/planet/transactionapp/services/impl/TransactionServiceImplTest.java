package com.planet.transactionapp.services.impl;

import com.planet.transactionapp.model.dto.XmlTransactionDto;
import com.planet.transactionapp.model.mappers.XmlTransactionMapper;
import com.planet.transactionapp.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private XmlTransactionMapper mapper;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void saveAll_whenSaveListTransactionsDto() {
        XmlTransactionDto xmlTransactionDto = new XmlTransactionDto();
        List<XmlTransactionDto> transactions = Collections.singletonList(xmlTransactionDto);

        transactionService.saveAll(transactions);

        verify(mapper).fromRecord(transactions);
        verify(transactionRepository).saveAll(mapper.fromRecord(transactions));
    }
}