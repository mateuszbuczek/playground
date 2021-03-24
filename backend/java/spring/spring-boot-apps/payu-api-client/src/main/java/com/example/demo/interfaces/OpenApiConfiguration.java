package com.example.demo.interfaces;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

  @Bean
  OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info().description("REST API DOC").title("payu-client-api").version("1.0"));
  }
}
