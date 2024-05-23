package com.planet.transactionapp.services.impl;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import com.planet.transactionapp.config.ConfigStorageProperties;
import com.planet.transactionapp.exceptions.FileStorageException;
import com.planet.transactionapp.exceptions.FileStorageIOException;
import com.planet.transactionapp.exceptions.ResourceNotFoundException;
import com.planet.transactionapp.model.type.StatusFile;
import com.planet.transactionapp.services.FileImportStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileStorageServiceImplTest {
    @Mock
    private XMLProcessorServiceService xmlProcessorService;
    @Mock
    private FileImportStatusService fileImportStatusService;
    @Mock
    private ConfigStorageProperties configurations;
    private FileStorageServiceImpl fileStorageService;
    private Path todoPath;
    private Path donePath;

    @BeforeEach
    public void setUp() throws IOException {
        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
        todoPath = fileSystem.getPath("storage/todo");
        donePath = fileSystem.getPath("storage/done");
        Files.createDirectories(todoPath);
        Files.createDirectories(donePath);
        when(configurations.getFilePathTodo()).thenReturn(todoPath.toString());
        when(configurations.getFilePathDone()).thenReturn(donePath.toString());
        fileStorageService = new FileStorageServiceImpl(xmlProcessorService, fileImportStatusService,
                configurations, fileSystem);
    }

    @Test
    public void initStorageDirectory_createsDirectories() {
        fileStorageService.initStorageDirectory();
        assertTrue(Files.exists(todoPath));
        assertTrue(Files.exists(donePath));
    }

    @Test
    public void statusDoneFile_whenFileExistsInDone_returnsTrue() throws IOException {
        String fileName = "transactions.xml";
        Path doneFilePath = donePath.resolve(fileName);
        Files.createFile(doneFilePath);
        assertEquals(fileStorageService.statusDoneFile(fileName).getStatus(), StatusFile.DONE);
    }

    @Test
    public void loadTodo_whenFileExists_returnsCorrectPath() throws IOException {
        String fileName = "transactions.txt";
        Path todoFilePath = todoPath.resolve(fileName);
        Files.createFile(todoFilePath);
        Path returnedPath = fileStorageService.loadTodo(fileName);
        assertEquals(todoFilePath, returnedPath);
    }

    @Test
    public void statusDoneFile_whenFileExistsInTodo_returnsFalse() throws IOException {
        String fileName = "transactions.txt";
        Path file = todoPath.resolve(fileName);
        Files.createFile(file);
        assertNotEquals(fileStorageService.statusDoneFile(fileName).getStatus(), StatusFile.DONE);
    }

    @Test
    public void statusDoneFile_whenFileDoesNotExist_throwsResourceNotFoundException() {
        String fileName = "test.txt";
        assertThrows(ResourceNotFoundException.class, () -> fileStorageService.statusDoneFile(fileName));
    }

    @Test
    public void store_whenFileIsNotEmpty_storesFile() {
        MultipartFile file = new MockMultipartFile("file", "test.xml", "text/xml", "test data".getBytes());
        String storedFileName = fileStorageService.store(file);
        assertEquals("test.xml", storedFileName);
        assertTrue(Files.exists(todoPath.resolve(storedFileName)));
    }

    @Test
    public void store_whenFileIsEmpty_throwsFileStorageException() {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "".getBytes());
        assertThrows(FileStorageException.class, () -> fileStorageService.store(file));
    }

    @Test
    public void doneFile_whenFileExists_movesFile() throws IOException {
        String fileName = "example.txt";
        Path todoFilePath = todoPath.resolve(fileName);
        Files.createFile(todoFilePath);
        fileStorageService.doneFile(fileName);
        assertFalse(Files.exists(todoFilePath));
        assertTrue(Files.exists(donePath.resolve(fileName)));
    }

    @Test
    public void doneFile_whenFileDoesNotExist_throwsFileStorageIOException() {
        String fileName = "test.txt";
        assertThrows(FileStorageIOException.class, () -> fileStorageService.doneFile(fileName));
    }

}