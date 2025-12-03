package com.dfont.api_rest.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfig {

    private final CacheProperties properties;

    public CacheConfig(CacheProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Caffeine<Object, Object> caffeineBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(properties.getExpireAfterWriteMinutes(), TimeUnit.MINUTES)
                .maximumSize(properties.getMaximumSize());
    }

    @Bean
    public CacheManager caffeineCacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager manager = new CaffeineCacheManager("users", "user");
        manager.setCaffeine(caffeine);
        return manager;
    }
}
