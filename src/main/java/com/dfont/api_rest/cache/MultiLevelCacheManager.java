package com.dfont.api_rest.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;

public class MultiLevelCacheManager implements CacheManager {

    private final CacheManager caffeineManager;
    private final CacheManager redisManager;

    public MultiLevelCacheManager(CacheManager caffeineManager, CacheManager redisManager) {
        this.caffeineManager = caffeineManager;
        this.redisManager = redisManager;
    }

    @Override
    public Cache getCache(String name) {
        Cache caffeineCache = caffeineManager.getCache(name);
        Cache redisCache = redisManager.getCache(name);
        return new MultiLevelCache(name, caffeineCache, redisCache);
    }

    @Override
    public Collection<String> getCacheNames() {
        // Las definidas en Caffeine son suficientes
        return caffeineManager.getCacheNames();
    }
}

