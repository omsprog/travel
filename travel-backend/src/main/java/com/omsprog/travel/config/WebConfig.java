package com.omsprog.travel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Value(value = "${api.base.url}")
    private String baseUrl;

    @Bean(name = "dog_facts")
    public WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow CORS on all endpoints
                        .allowedOrigins("http://localhost:4200") // Specify the allowed origins (e.g., Angular on port 4200)
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true) // If your requests require credentials (e.g., cookies)
                        .maxAge(3600); // Cache the response for 1 hour
            }
        };
    }
}