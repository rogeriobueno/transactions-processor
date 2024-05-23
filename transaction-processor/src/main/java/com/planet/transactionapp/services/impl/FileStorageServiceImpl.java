package com.planet.transactionapp.services.impl;

import com.planet.transactionapp.config.ConfigStorageProperties;
import com.planet.transactionapp.exceptions.FileStorageException;
import com.planet.transactionapp.exceptions.FileStorageIOException;
import com.planet.transactionapp.exceptions.ResourceNotFoundException;
import com.planet.transactionapp.model.dto.ResponseFileDto;
import com.planet.transactionapp.model.type.StatusFile;
import com.planet.transactionapp.services.FileImportStatusService;
import com.planet.transactionapp.services.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {


    private final XMLProcessorServiceService xmlProcessorService;
    private final FileImportStatusService fileImportStatusService;
    private final Path todoRootLocation;
    private final Path doneRootLocation;
    private final int maxFilesExecution;

    public FileStorageServiceImpl(XMLProcessorServiceService xmlProcessorService, FileImportStatusService fileImportStatusService,
                                  ConfigStorageProperties configurations, FileSystem fileSystem) {
        this.xmlProcessorService = xmlProcessorService;
        this.fileImportStatusService = fileImportStatusService;
        if (configurations.getFilePathTodo().isBlank() || configurations.getFilePathDone().isBlank()) {
            throw new FileStorageException("Files location not be empty");
        }
        this.todoRootLocation = fileSystem.getPath(configurations.getFilePathTodo());
        this.doneRootLocation = fileSystem.getPath(configurations.getFilePathDone());
        this.maxFilesExecution = configurations.getMaxFilesExecution();
    }

    @Override
    public ResponseFileDto statusDoneFile(String fileName) {
        try {
            if (Files.walk(todoRootLocation, 1)
                     .filter(path -> !Files.isDirectory(path))
                     .anyMatch(f -> f.getFileName().toString().equalsIgnoreCase(fileName))) {
                return new ResponseFileDto(fileName, "", StatusFile.IN_PROGRESS);
            } else if (Files.walk(doneRootLocation, 1)
                            .filter(path -> !Files.isDirectory(path))
                            .anyMatch(f -> f.getFileName().toString().equalsIgnoreCase(fileName))) {
                return new ResponseFileDto(fileName, "", StatusFile.DONE);
            } else {
                throw new ResourceNotFoundException("File not found!");
            }
        } catch (IOException e) {
            throw new FileStorageIOException("Error on load the status file.", e);
        }
    }

    @Override
    @Scheduled(initialDelay = 20_000, fixedDelay = 5000)
    public void startProcessTodoFiles() {
        loadAllTodo(maxFilesExecution)
                .parallelStream()
                .forEach(path -> {
                    try (InputStream inputStream = Files.newInputStream(path.toAbsolutePath())) {
                        xmlProcessorService.saveAllTransactionsFromXML(inputStream, path.toFile().getName());
                        doneFile(path.toFile().getName());
                    } catch (Exception e) {
                        throw new FileStorageIOException("Error on process todo files", e);
                    }
                });
    }

    @Override
    public List<Path> loadAllTodo(int limit) {
        try {
            if (limit <= 0) {
                throw new FileStorageException("Limit must be greater than 0");
            }
            return Files.walk(this.todoRootLocation, 1)
                        .filter(path -> !path.equals(this.todoRootLocation))
                        .limit(limit)
                        .toList();
        } catch (IOException e) {
            throw new FileStorageIOException("Failed to read stored files", e);
        }
    }

    @Override
    public void initStorageDirectory() {
        try {
            Files.createDirectories(todoRootLocation);
            Files.createDirectories(doneRootLocation);
            log.info("Initialized storages in {} and {}", todoRootLocation.toAbsolutePath(),
                    doneRootLocation.toAbsolutePath());
        } catch (IOException e) {
            throw new FileStorageIOException("Could not initialize storage", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new FileStorageException("Failed to store empty file.");
            }
            String fileName = file.getOriginalFilename();
            if (fileImportStatusService.isAlreadyImported(fileName) || fileExistsInTodo(fileName)) {
                throw new FileStorageException("File already imported: " + fileName);
            }
            Path destinationFile = this.todoRootLocation
                    .resolve(fileName)
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.todoRootLocation.toAbsolutePath())) {
                // This is a security check :)
                throw new FileStorageException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            log.info("File stored in {}", destinationFile.toAbsolutePath());
            return fileName;
        } catch (IOException e) {
            throw new FileStorageIOException("Failed to store file.", e);
        }
    }

    @Override
    public void doneFile(String filename) {
        Path destinationFile = this.doneRootLocation.resolve(filename).normalize().toAbsolutePath();
        try {
            Files.move(todoRootLocation.resolve(filename), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Failed to move file {} to {}", filename, destinationFile.toAbsolutePath());
            throw new FileStorageIOException("Failed to move file.", e);
        }
    }

    @Override
    public Path loadTodo(String filename) {
        return todoRootLocation.resolve(filename);
    }

    private boolean fileExistsInTodo(String fileName) {
        Path filePath = this.todoRootLocation.resolve(fileName).normalize().toAbsolutePath();
        return Files.exists(filePath);
    }

}
