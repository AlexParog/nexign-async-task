package com.nexign.asynctask.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;

public class SwaggerConfiguration {
    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Nexign Async Task API")
                        .version("v1")
                        .description("API для асинхронного управления задачами")
                ).addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("localhost server"));
    }
}
