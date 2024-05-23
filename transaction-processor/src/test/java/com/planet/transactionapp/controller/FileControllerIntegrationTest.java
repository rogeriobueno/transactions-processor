package com.planet.transactionapp.controller;

import com.planet.transactionapp.contants.MockValues;
import com.planet.transactionapp.exceptions.FileStorageException;
import com.planet.transactionapp.exceptions.FileStorageIOException;
import com.planet.transactionapp.model.dto.ResponseFileDto;
import com.planet.transactionapp.model.type.StatusFile;
import com.planet.transactionapp.services.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileStorageService fileStorageService;

    private MockMultipartFile file;

    @BeforeEach
    public void setup() {
        file = new MockMultipartFile("file", "transactions.xml", MediaType.TEXT_XML_VALUE,
                MockValues.FOUR_TRANSACTIONS_XML.getBytes());
    }

    @Test
    public void testTransactionFileUpload() throws Exception {
        when(fileStorageService.store(any())).thenReturn("transactions.xml");

        mockMvc.perform(multipart("/api/v1/transactions/file/upload").file(file))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.fileName").value("transactions.xml"));
    }

    @Test
    public void testTransactionFileStatus() throws Exception {
        when(fileStorageService.statusDoneFile(any())).thenReturn(
                new ResponseFileDto("transactions.xml",
                        "http://localhost/api/v1/transactions/file/status/transactions.xml", StatusFile.DONE)
        );

        mockMvc.perform(get("/api/v1/transactions/file/status/transactions.xml"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.status").value(StatusFile.DONE.name()));
    }


    @Test
    public void testTransactionFileUpload_StorageThrowsFileStorageException() throws Exception {
        when(fileStorageService.store(any())).thenThrow(new FileStorageException("Error storing file"));

        mockMvc.perform(multipart("/api/v1/transactions/file/upload").file(file))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testTransactionFileUpload_StorageThrowsFileStorageIOException() throws Exception {
        when(fileStorageService.store(any())).thenThrow(new FileStorageIOException("Error storing file"));

        mockMvc.perform(multipart("/api/v1/transactions/file/upload").file(file))
               .andExpect(status().isInternalServerError());
    }

    @Test
    public void testTransactionFileUpload_InvalidFile() throws Exception {
        MockMultipartFile invalidFile = new MockMultipartFile("file", "invalid.txt", MediaType.TEXT_PLAIN_VALUE,
                "invalid content".getBytes());

        mockMvc.perform(multipart("/api/v1/transactions/file/upload").file(invalidFile))
               .andExpect(status().isBadRequest());
    }

}