package com.planet.transactionapp.services.impl;

import com.planet.transactionapp.contants.MockValues;
import com.planet.transactionapp.model.mappers.XmlTransactionMapper;
import com.planet.transactionapp.services.FileImportStatusService;
import com.planet.transactionapp.services.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.planet.transactionapp.contants.MockValues.TRANSACTIONS_XML_FILE_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class XMLProcessorServiceServiceTest {

    @Mock
    private FileImportStatusService fileImportStatusService;
    @Mock
    private TransactionService transactionService;
    private XmlTransactionMapper mapper;

    private XMLProcessorServiceService xmlProcessorServiceService;

    @BeforeEach
    void setUp() {
        xmlProcessorServiceService = new XMLProcessorServiceService(transactionService,
                fileImportStatusService);
    }

    @Test
    void shouldPersistTransactionsSuccessfully_twoTimes() {
        ReflectionTestUtils.setField(xmlProcessorServiceService, "batchSize", 2);
        InputStream inputStream = new ByteArrayInputStream(MockValues.FOUR_TRANSACTIONS_XML.trim().getBytes());
        xmlProcessorServiceService.saveAllTransactionsFromXML(inputStream, TRANSACTIONS_XML_FILE_NAME);
        verify(transactionService, times(2)).saveAll(any());
    }

    @Test
    void shouldPersistTransactionsSuccessfully_twoTimesWithOneInTheEnd() {
        ReflectionTestUtils.setField(xmlProcessorServiceService, "batchSize", 2);
        InputStream inputStream = new ByteArrayInputStream(MockValues.THREE_TRANSACTIONS_XML.trim().getBytes());
        xmlProcessorServiceService.saveAllTransactionsFromXML(inputStream, TRANSACTIONS_XML_FILE_NAME);
        verify(transactionService, times(2)).saveAll(any());
    }

    @Test
    void persistTransactions_withInvalidXML() {
        String invalidXML = "<transactions><Transaction></Transaction></transactions>";
        ReflectionTestUtils.setField(xmlProcessorServiceService, "batchSize", 2);

        InputStream inputStream = new ByteArrayInputStream(invalidXML.getBytes());
        Assertions.assertDoesNotThrow(() -> xmlProcessorServiceService.saveAllTransactionsFromXML(inputStream,
                TRANSACTIONS_XML_FILE_NAME));
        verify(transactionService, never()).saveAll(any());
    }

    @Test
    void persistTransactions_withException() {
        ReflectionTestUtils.setField(xmlProcessorServiceService, "batchSize", 2);
        InputStream inputStream = new ByteArrayInputStream(MockValues.FOUR_TRANSACTIONS_XML.getBytes());
        Mockito.lenient().doThrow(new RuntimeException()).when(transactionService).saveAll(any());
        Assertions.assertThrows(RuntimeException.class,
                () -> xmlProcessorServiceService.saveAllTransactionsFromXML(inputStream, TRANSACTIONS_XML_FILE_NAME));
    }
}