package com.dfont.api_rest.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final CacheManager redisCacheManager;
    private final CacheManager caffeineCacheManager;

    public CacheService(
            @Qualifier("redisCacheManager") CacheManager redisCacheManager,
            @Qualifier("caffeineCacheManager") CacheManager caffeineCacheManager) {
        this.redisCacheManager = redisCacheManager;
        this.caffeineCacheManager = caffeineCacheManager;
    }

    // Obtener un elemento por ID desde la cache seleccionada
    public Object getFromCache(String cacheName, Object id) {
        Cache cache = resolveCache(cacheName);
        if (cache == null) return null;

        return cache.get(id, Object.class);
    }

    // Obtener todos los elementos de una cache
    public Map<Object, Object> getAllFromCache(String cacheName) {
        Cache cache = resolveCache(cacheName);
        if (cache == null) return Map.of();

        Object nativeCache = cache.getNativeCache();

        // Caffeine usa ConcurrentMap internamente
        if (nativeCache instanceof ConcurrentMap<?, ?> map) {
            return new HashMap<>(map);
        }

        // Redis no permite obtener todo sin un scan, así que devolvemos vacío
        return Map.of();
    }

    // Limpiar una cache
    public void clearCache(String cacheName) {
        Cache cache = resolveCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }

    // Limpiar todas las caches
    public void clearAllCaches() {
        redisCacheManager.getCache("usersCacheRedis").clear();
        caffeineCacheManager.getCache("usersCacheCaffeine").clear();
    }

    // Resolver cache en el admin correcto
    private Cache resolveCache(String cacheName) {
        // Primero Caffeine (cache en RAM, más rápida)
        Cache cache = caffeineCacheManager.getCache(cacheName);
        if (cache != null) {
            return cache;
        }

        // Luego Redis (cache distribuida, más lenta)
        return redisCacheManager.getCache(cacheName);
    }

}
