package com.planet.transactionapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

@Configuration
public class ConfigFileSystem {
    @Bean
    public FileSystem defaultFileSystem() {
        return FileSystems.getDefault();
    }
}
