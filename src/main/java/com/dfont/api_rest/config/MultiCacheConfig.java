package com.dfont.api_rest.config;

import com.dfont.api_rest.cache.MultiLevelCacheManager;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultiCacheConfig {

    @Bean
    public CacheManager cacheManager(
            CacheManager caffeineCacheManager,
            CacheManager redisCacheManager) {

        return new MultiLevelCacheManager(
                caffeineCacheManager,
                redisCacheManager
        );
    }
}
