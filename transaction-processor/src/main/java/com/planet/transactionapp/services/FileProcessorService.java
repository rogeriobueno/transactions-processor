package com.planet.transactionapp.services;

import java.io.InputStream;

public interface FileProcessorService {
    void saveAllTransactionsFromXML(InputStream inputStream, String fileName);
}
