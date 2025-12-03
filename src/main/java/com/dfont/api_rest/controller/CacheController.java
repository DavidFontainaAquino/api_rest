package com.dfont.api_rest.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dfont.api_rest.service.CacheService;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private final CacheService cacheService;

    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    // Obtener un valor por id
    @GetMapping("/{cacheName}/{id}")
    public ResponseEntity<?> getById(
            @PathVariable String cacheName,
            @PathVariable String id) {

        Object value = cacheService.getFromCache(cacheName, id);
        return ResponseEntity.ok(value != null ? value : "No existe en cache");
    }

    // Obtener todos los valores de una cache
    @GetMapping("/{cacheName}")
    public ResponseEntity<Map<Object, Object>> getAll(@PathVariable String cacheName) {
        return ResponseEntity.ok(cacheService.getAllFromCache(cacheName));
    }

    // Vaciar una cache
    @DeleteMapping("/{cacheName}")
    public ResponseEntity<String> clear(@PathVariable String cacheName) {
        cacheService.clearCache(cacheName);
        return ResponseEntity.ok("Cache '" + cacheName + "' limpiada");
    }

    // Vaciar todas las caches (Redis + Caffeine)
    @DeleteMapping("/all")
    public ResponseEntity<String> clearAll() {
        cacheService.clearAllCaches();
        return ResponseEntity.ok("Todas las caches limpiadas");
    }
}
