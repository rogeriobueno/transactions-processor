package com.planet.transactionapp.repository;

import com.planet.transactionapp.model.entities.FileImportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileImportStatusRepository extends JpaRepository<FileImportStatus, Long> {
    boolean existsByFileName(String fileName);
}
