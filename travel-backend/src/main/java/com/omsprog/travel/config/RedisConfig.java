package com.omsprog.travel.config;

import com.omsprog.travel.util.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

@Configuration
@EnableCaching
@Slf4j
@EnableScheduling
public class RedisConfig {
    @Value(value = "${cache.redis.address}")
    private String serverAddress;
    @Value(value = "${cache.redis.password}")
    private String serverPassword;

    @Bean
    public RedissonClient redissonClient() {
        var config = new Config();
        config.useSingleServer()
                .setAddress(serverAddress)
                .setPassword(serverPassword);

        return Redisson.create(config);
    }

    @Bean
    @Autowired
    public CacheManager cacheManager(RedissonClient redissonClient) {
        var configs = Map.of(
                CacheConstants.FLIGHT_CACHE_NAME, new CacheConfig(),
                CacheConstants.HOTEL_CACHE_NAME, new CacheConfig()
        );
        return new RedissonSpringCacheManager(redissonClient, configs);
    }

//    @Scheduled(cron = "0 * * * * * ")
//    @Async
//    @CacheEvict(cacheNames = {
//            CacheConstants.HOTEL_CACHE_NAME,
//            CacheConstants.FLIGHT_CACHE_NAME
//    }, allEntries = true)
//    public void deleteCache() {
//        log.info("Cleaning cache ...");
//    }
}