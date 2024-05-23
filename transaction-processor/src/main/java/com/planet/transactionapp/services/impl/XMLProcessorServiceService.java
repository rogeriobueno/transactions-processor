package com.planet.transactionapp.services.impl;

import com.planet.transactionapp.model.dto.XmlTransactionDto;
import com.planet.transactionapp.services.FileImportStatusService;
import com.planet.transactionapp.services.FileProcessorService;
import com.planet.transactionapp.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static com.planet.transactionapp.model.constants.TransactionXMLConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class XMLProcessorServiceService implements FileProcessorService {

    private final TransactionService transactionService;
    private final FileImportStatusService fileImportStatusService;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size:500}")
    private int batchSize;


    public void saveAllTransactionsFromXML(InputStream inputStream, String fileName) {
        log.info("Starting XML parser for {}", fileName);
        long startTime = System.currentTimeMillis();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        AtomicInteger counter = new AtomicInteger();
        XmlTransactionDto xmlTransactionDto = new XmlTransactionDto();
        List<XmlTransactionDto> batchTransactions = new CopyOnWriteArrayList<>();
        StringBuilder stringBuilder = new StringBuilder("Import/Process XML summary:\n");

        String elementName = null;
        try {
            reader = factory.createXMLStreamReader(inputStream);

            while (reader.hasNext()) {
                try {
                    int event = reader.next();

                    switch (event) {
                        case XMLStreamConstants.START_ELEMENT:
                            elementName = reader.getLocalName();

                            switch (elementName) {
                                case TRANSACTION_COLLECTION -> {
                                }
                                case TRANSACTION -> {
                                    xmlTransactionDto = new XmlTransactionDto();
                                    readAmountTransaction(reader, xmlTransactionDto);
                                    readCurrencyTransaction(reader, xmlTransactionDto);
                                    readTimestampTransaction(reader, xmlTransactionDto);
                                }
                                case CREDIT_CARD -> readCreditCard(reader, xmlTransactionDto);
                                case RECIPIENT -> readRecipient(reader, xmlTransactionDto);
                                default -> {
                                    stringBuilder.append("Unrecognized XML element: ").append(elementName).append("\n");
                                    log.warn("Warn in file {}, uUnrecognized XML element: {}", fileName, elementName);
                                }
                            }
                            break;
                        case XMLStreamConstants.END_ELEMENT:
                            if (reader.getLocalName().equals(TRANSACTION) && xmlTransactionDto != null) {
                                counter.incrementAndGet();
                                batchTransactions.add(xmlTransactionDto);
                                if (batchTransactions.size() % batchSize == 0) {
                                    transactionService.saveAll(batchTransactions);
                                    batchTransactions.clear();
                                }
                            }
                            break;
                    }
                } catch (RuntimeException e) {
                    stringBuilder.append("Error processing transaction, Element: ")
                                 .append(elementName).append(" Cause:").append(e.getMessage()).append("\n");
                    log.error("Error in file {} processing transaction, element {} cause {}", fileName, elementName,
                            e.getMessage(), e);
                    xmlTransactionDto = null;
                }
            }
            //To persist the rest of transactions.
            if (!batchTransactions.isEmpty()) {
                transactionService.saveAll(batchTransactions);
            }
            log.info("Persisted {} batchTransactions with success.", counter.get());
        } catch (XMLStreamException e) {
            stringBuilder.append("\nError reading element ").append(elementName).append(" Cause:")
                         .append(e.getMessage());
            log.error("Error in file {}, reading element {}, cause {}", fileName, elementName, e.getMessage(), e);
        }
        long duration = (System.currentTimeMillis() - startTime) / 1_000;  // convert to milliseconds
        stringBuilder.append("\n\nTotal time processing ").append(duration).append(" seconds");
        fileImportStatusService.persistFileStatus(fileName, stringBuilder.toString());
        log.info("Completion of the import process {}", fileName);
    }

    private void readTimestampTransaction(XMLStreamReader reader, XmlTransactionDto xmlTransactionDto) {
        String createdAt = reader.getAttributeValue(TRANSACTION_NAMESPACE_URI,
                TRANSACTION_TIMESTAMP);
        if (createdAt != null && !createdAt.isBlank()) {
            xmlTransactionDto.setTimestamp(ZonedDateTime.parse(createdAt));
        } else {
            throw new RuntimeException("Timestamp must be provided");
        }
    }

    private void readCurrencyTransaction(XMLStreamReader reader, XmlTransactionDto xmlTransactionDto) {
        String currency = reader.getAttributeValue(TRANSACTION_NAMESPACE_URI,
                TRANSACTION_CURRENCY);
        if (currency != null && !currency.isBlank()) {
            xmlTransactionDto.setCurrency(currency);
        } else {
            throw new RuntimeException("Currency must be provided");
        }
    }

    private void readAmountTransaction(XMLStreamReader reader, XmlTransactionDto xmlTransactionDto) {
        String amount = reader.getAttributeValue("", TRANSACTION_AMOUNT);
        if (amount != null && !amount.isBlank() && Double.parseDouble(amount) > 0) {
            xmlTransactionDto.setAmount(new BigDecimal(amount));
        } else {
            throw new RuntimeException("Amount must be greater than 0");
        }
    }

    private void readRecipient(XMLStreamReader reader, XmlTransactionDto dto) throws XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    String elementName = reader.getLocalName();
                    switch (elementName) {
                        case RECIPIENT_NAME -> {
                            String recipientName = reader.getElementText();
                            if (recipientName != null && !recipientName.isBlank()) {
                                dto.setRecipientName(recipientName);
                            } else {
                                throw new RuntimeException("Recipient name must be provided");
                            }
                        }
                        case RECIPIENT_EMAIL -> dto.setRecipientEmail(reader.getElementText());
                        case RECIPIENT_PHONE -> dto.setRecipientPhone(reader.getElementText());
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (reader.getLocalName().equals(RECIPIENT)) {
                        return;
                    }
                    break;
            }
        }
    }

    private void readCreditCard(XMLStreamReader reader, XmlTransactionDto dto) throws XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    String elementName = reader.getLocalName();
                    switch (elementName) {
                        case CARD_NUMBER -> {
                            String cardNumber = reader.getElementText();
                            if (cardNumber != null && !cardNumber.isBlank()) {
                                dto.setCreditNumber(cardNumber.replaceAll("-", ""));
                            } else {
                                throw new RuntimeException("Card number must be provided");
                            }
                        }
                        case CARD_NAME -> {
                            String cardName = reader.getElementText();
                            if (cardName != null && !cardName.isBlank()) {
                                dto.setCreditName(cardName);
                            } else {
                                throw new RuntimeException("Card name must be provided");
                            }
                        }
                        case CARD_EXPIRE_DATE -> {
                            String cardExpireDate = reader.getElementText();
                            if (cardExpireDate != null && !cardExpireDate.isBlank()) {
                                dto.setCreditExpiryDate(cardExpireDate);
                            } else {
                                throw new RuntimeException("Card expire date must be provided");
                            }
                        }
                        case CARD_CVV -> dto.setCreditCvv(reader.getElementText());
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (reader.getLocalName().equals(CREDIT_CARD)) {
                        return;
                    }
                    break;
            }
        }
    }
}
