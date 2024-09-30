package com.omsprog.travel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource(value = "classpath:config/dog_api.properties"),
    @PropertySource(value = "classpath:config/redis.properties"),
})
public class PropertiesConfig {

}