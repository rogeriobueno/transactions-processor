package com.planet.transactionapp.services;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface FileImportStatusService {
    @Transactional(propagation = Propagation.REQUIRED)
    void persistFileStatusWithError(String fileName, String description);

    @Transactional(propagation = Propagation.REQUIRED)
    void persistFileStatus(String fileName, String description);

    @Transactional(readOnly = true)
    boolean isAlreadyImported(String fileName);
}
