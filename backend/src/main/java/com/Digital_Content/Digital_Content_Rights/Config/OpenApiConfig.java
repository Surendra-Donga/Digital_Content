package com.Digital_Content.Digital_Content_Rights.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Digital Content Rights & Royalty API")
                        .version("1.0")
                        .description("API documentation for Digital Content Rights and Royalty Distribution Management System"));
    }
}
