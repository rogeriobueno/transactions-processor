package com.planet.transactionapp.contants;


import com.planet.transactionapp.model.dto.XmlTransactionDto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MockValues {
    public static final String TRANSACTIONS_XML_FILE_NAME = "file_teste.xml";
    public static String FOUR_TRANSACTIONS_XML = """
                        <transactions xmlns:x="http://www.example.com/transactions">
                            <Transaction amount="558.3507317192206" x:currency="GBP" x:timestamp="2024-05-08T02:23:11.938755749Z">
                                <creditCard>
                                    <number>1234-5678-1234-207</number>
                                    <name>David</name>
                                    <expiryDate>12/24</expiryDate>
                                    <cvv/>
                                </creditCard>
                                <recipient>
                                    <name>Charlie</name>
                                    <email/>
                                    <phone>+1234567890</phone>
                                </recipient>
                            </Transaction>
                            <Transaction amount="748.9139853138893" x:currency="CHF" x:timestamp="2024-05-08T02:26:31.923370014Z">
                                <creditCard>
                                    <number>1234-5678-1234-9736</number>
                                    <name>Grace</name>
                                    <expiryDate>12/24</expiryDate>
                                    <cvv/>
                                </creditCard>
                                <recipient>
                                    <name>Bob</name>
                                    <email/>
                                    <phone>+1234567890</phone>
                                </recipient>
                            </Transaction>
                            <Transaction amount="558.3507317192206" x:currency="GBP" x:timestamp="2024-05-08T02:23:11.938755749Z">
                                <creditCard>
                                    <number>1234-5678-1234-207</number>
                                    <name>David</name>
                                    <expiryDate>12/24</expiryDate>
                                    <cvv/>
                                </creditCard>
                                <recipient>
                                    <name>Charlie</name>
                                    <email/>
                                    <phone>+1234567890</phone>
                                </recipient>
                            </Transaction>
                            <Transaction amount="748.9139853138893" x:currency="CHF" x:timestamp="2024-05-08T02:26:31.923370014Z">
                                <creditCard>
                                    <number>1234-5678-1234-9736</number>
                                    <name>Grace</name>
                                    <expiryDate>12/24</expiryDate>
                                    <cvv/>
                                </creditCard>
                                <recipient>
                                    <name>Bob</name>
                                    <email/>
                                    <phone>+1234567890</phone>
                                </recipient>
                            </Transaction>
                        </transactions>
            """;
    public static String THREE_TRANSACTIONS_XML = """
                        <transactions xmlns:x="http://www.example.com/transactions">
                            <Transaction amount="558.3507317192206" x:currency="GBP" x:timestamp="2024-05-08T02:23:11.938755749Z">
                                <creditCard>
                                    <number>1234-5678-1234-207</number>
                                    <name>David</name>
                                    <expiryDate>12/24</expiryDate>
                                    <cvv/>
                                </creditCard>
                                <recipient>
                                    <name>Charlie</name>
                                    <email/>
                                    <phone>+1234567890</phone>
                                </recipient>
                            </Transaction>
                            <Transaction amount="748.9139853138893" x:currency="CHF" x:timestamp="2024-05-08T02:26:31.923370014Z">
                                <creditCard>
                                    <number>1234-5678-1234-9736</number>
                                    <name>Grace</name>
                                    <expiryDate>12/24</expiryDate>
                                    <cvv/>
                                </creditCard>
                                <recipient>
                                    <name>Bob</name>
                                    <email/>
                                    <phone>+1234567890</phone>
                                </recipient>
                            </Transaction>
                            <Transaction amount="558.3507317192206" x:currency="GBP" x:timestamp="2024-05-08T02:23:11.938755749Z">
                                <creditCard>
                                    <number>1234-5678-1234-207</number>
                                    <name>David</name>
                                    <expiryDate>12/24</expiryDate>
                                    <cvv/>
                                </creditCard>
                                <recipient>
                                    <name>Charlie</name>
                                    <email/>
                                    <phone>+1234567890</phone>
                                </recipient>
                            </Transaction>
                        </transactions>
            """;

    public static List<XmlTransactionDto> makeTransactionDto() {
        List<XmlTransactionDto> dtos = new ArrayList<>();
        IntStream.of(1, 2).forEach(i -> {
            dtos.add(XmlTransactionDto.builder().amount(new BigDecimal("558.3507317192206")).currency("GBP")
                                      .timestamp(ZonedDateTime.parse("2024-05-08T02:23:11.938755749Z"))
                                      .creditNumber("123456781234207").creditName("David").creditExpiryDate("12/24")
                                      .creditCvv("").recipientName("Charlie").recipientEmail("")
                                      .recipientPhone("+1234567890")
                                      .build());

            dtos.add(XmlTransactionDto.builder().amount(new BigDecimal("748.9139853138893")).currency("CHF")
                                      .timestamp(ZonedDateTime.parse("2024-05-08T02:26:31.923370014Z"))
                                      .creditNumber("1234-5678-1234-9736").creditName("Grace").creditExpiryDate("12/24")
                                      .creditCvv("").recipientName("Bob").recipientEmail("")
                                      .recipientPhone("+1234567890")
                                      .build());
        });
        return dtos;
    }
}
