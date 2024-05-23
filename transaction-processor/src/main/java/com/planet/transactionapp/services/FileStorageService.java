package com.planet.transactionapp.services;

import com.planet.transactionapp.model.dto.ResponseFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface FileStorageService {

    void startProcessTodoFiles();

    List<Path> loadAllTodo(int limit);

    void initStorageDirectory();

    String store(MultipartFile file);

    void doneFile(String filename);

    Path loadTodo(String filename);

    ResponseFileDto statusDoneFile(String fileName);

}
