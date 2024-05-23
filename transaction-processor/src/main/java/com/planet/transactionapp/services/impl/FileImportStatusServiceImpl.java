package com.planet.transactionapp.services.impl;

import com.planet.transactionapp.model.entities.FileImportStatus;
import com.planet.transactionapp.model.type.StatusFile;
import com.planet.transactionapp.repository.FileImportStatusRepository;
import com.planet.transactionapp.services.FileImportStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileImportStatusServiceImpl implements FileImportStatusService {

    private final FileImportStatusRepository fileImportStatusRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void persistFileStatusWithError(String fileName, String description) {
        fileImportStatusRepository.save(
                FileImportStatus.builder()
                                .fileName(fileName)
                                .importDate(LocalDateTime.now())
                                .description(description)
                                .status(StatusFile.ERROR).build());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void persistFileStatus(String fileName, String description) {
        fileImportStatusRepository.save(
                FileImportStatus.builder()
                                .fileName(fileName)
                                .importDate(LocalDateTime.now())
                                .description(description)
                                .status(StatusFile.DONE).build());

    }

    @Override
    @Transactional(readOnly = true)
    public boolean isAlreadyImported(String fileName) {
        return fileImportStatusRepository.existsByFileName(fileName);
    }


}
