package com.planet.transactionapp;

import com.planet.transactionapp.services.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
public class TransactionAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionAppApplication.class, args);
    }

    @Bean
    CommandLineRunner init(FileStorageService fileStorageService) {
        return (args) -> {
            fileStorageService.initStorageDirectory();
        };
    }


}
