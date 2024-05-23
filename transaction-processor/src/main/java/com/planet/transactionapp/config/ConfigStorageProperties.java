package com.planet.transactionapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "storage-files")
public class ConfigStorageProperties {
    public int maxFilesExecution;
    private String filePathTodo;
    private String filePathDone;
}
