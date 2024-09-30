package com.omsprog.travel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value(value = "${api.base.url}")
    private String baseUrl;

    @Bean(name = "dog_facts")
    public WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }
}