package com.planet.transactionapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigOpenAPI {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RESTful API for Transaction Processor")
                        .version("v1")
                        .description("API for import, process and summarize transactions.")
                        .termsOfService("https://")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("http://apache.org/licenses/LICENSE-2.0.html")
                        )
                );
    }

}
