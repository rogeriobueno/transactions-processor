package com.planet.transactionapp.model.constants;

public interface TransactionXMLConstants {

    String TRANSACTION_COLLECTION = "transactions";
    String TRANSACTION = "Transaction";
    String TRANSACTION_NAMESPACE_URI = "http://www.example.com/transactions";
    String TRANSACTION_AMOUNT = "amount";
    String TRANSACTION_TIMESTAMP = "timestamp";
    String TRANSACTION_CURRENCY = "currency";

    String CREDIT_CARD = "creditCard";
    String CARD_NUMBER = "number";
    String CARD_NAME = "name";
    String CARD_EXPIRE_DATE = "expiryDate";
    String CARD_CVV = "cvv";

    String RECIPIENT = "recipient";
    String RECIPIENT_NAME = "name";
    String RECIPIENT_EMAIL = "email";
    String RECIPIENT_PHONE = "phone";
}
