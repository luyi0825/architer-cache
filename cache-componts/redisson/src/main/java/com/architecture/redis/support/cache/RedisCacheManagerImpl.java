package com.architecture.redis.support.cache;

import com.architecture.context.cache.Cache;
import com.architecture.context.cache.CacheManager;
import org.redisson.api.RedissonClient;

import java.util.concurrent.ConcurrentHashMap;

/**
 * redis缓存管理实现
 *
 * @author luyi
 */
public class RedisCacheManagerImpl implements CacheManager {

    private final RedissonClient client;

    private final RedisValueService valueOperations;

    public RedisCacheManagerImpl(RedissonClient client, RedisValueService valueOperations) {
        this.client = client;
        this.valueOperations = valueOperations;
    }

    ConcurrentHashMap<String, Cache> caches = new ConcurrentHashMap<>(32);

    @Override
    public Cache getSimpleCache(String cacheName) {
        //org.springframework.cache.CacheManager
        Cache cache = caches.get(cacheName);
        if (cache == null) {
            cache = new RedisValueCache(cacheName, valueOperations);
        }
        caches.putIfAbsent(cacheName, cache);
        return cache;
    }

    @Override
    public Cache getMapCache(String cacheName) {
        Cache cache = caches.get(cacheName);
        if (cache == null) {
            return new RedisMapCache(cacheName, client);
        }
        return cache;
    }

}
