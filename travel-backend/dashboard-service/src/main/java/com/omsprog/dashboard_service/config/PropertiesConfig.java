package com.omsprog.dashboard_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource(value = "classpath:config/redis.properties"),
})
public class PropertiesConfig {

}