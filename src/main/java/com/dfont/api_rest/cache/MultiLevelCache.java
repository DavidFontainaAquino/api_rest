package com.dfont.api_rest.cache;

import org.springframework.cache.Cache;

import java.util.concurrent.Callable;

public class MultiLevelCache implements Cache {

    private final Cache caffeineCache;
    private final Cache redisCache;
    private final String name;

    public MultiLevelCache(String name, Cache caffeineCache, Cache redisCache) {
        this.name = name;
        this.caffeineCache = caffeineCache;
        this.redisCache = redisCache;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return caffeineCache.getNativeCache();
    }

    @Override
    public ValueWrapper get(Object key) {
        // Buscar primero en Caffeine
        ValueWrapper value = caffeineCache.get(key);
        if (value != null) {
            return value;
        }

        // Buscar en Redis
        value = redisCache.get(key);
        if (value != null) {
            // Si existe en Redis → guardar en Caffeine
            caffeineCache.put(key, value.get());
            return value;
        }

        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Class<T> type) {
        ValueWrapper wrapper = get(key);
        if (wrapper == null) return null;
        return (T) wrapper.get();
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return caffeineCache.get(key, valueLoader);
    }

    @Override
    public void put(Object key, Object value) {
        caffeineCache.put(key, value);
        redisCache.put(key, value);
    }

    @Override
    public void evict(Object key) {
        caffeineCache.evict(key);
        redisCache.evict(key);
    }

    @Override
    public void clear() {
        caffeineCache.clear();
        redisCache.clear();
    }
}