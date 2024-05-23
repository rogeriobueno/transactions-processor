package com.planet.transactionapp.controller;

import com.planet.transactionapp.controller.spec.FileControllerSpec;
import com.planet.transactionapp.controller.validator.ValidXmlFile;
import com.planet.transactionapp.model.dto.ResponseFileDto;
import com.planet.transactionapp.model.type.StatusFile;
import com.planet.transactionapp.services.FileStorageService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transactions/file")
public class FileController implements FileControllerSpec {

    private final FileStorageService fileStorageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> transactionFileUpload(@NotNull @ValidXmlFile @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.store(file);
        ResponseFileDto responseDto = new ResponseFileDto(fileName, getUriStringFile(fileName), StatusFile.IN_PROGRESS);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/status/{fileName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> transactionFileStatus(@NotEmpty @PathVariable String fileName) {
        ResponseFileDto responseFileDto = fileStorageService.statusDoneFile(fileName);
        responseFileDto.setFileUri(getUriStringFile(fileName));
        return ResponseEntity.ok(responseFileDto);
    }

    private String getUriStringFile(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                                          .path("/api/v1/transactions/file/status/")
                                          .path(fileName)
                                          .toUriString();
    }

}
