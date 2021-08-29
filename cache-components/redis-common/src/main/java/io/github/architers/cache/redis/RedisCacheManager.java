package io.github.architers.cache.redis;


import io.github.architers.cache.Cache;
import io.github.architers.cache.CacheManager;
import io.github.architers.cache.redis.RedisMapCache;
import io.github.architers.cache.redis.RedisMapValueService;
import io.github.architers.cache.redis.RedisSimpleCache;
import io.github.architers.cache.redis.RedisSimpleValueService;


import java.util.concurrent.ConcurrentHashMap;

/**
 * redis缓存管理实现-基于redisson
 * <li>@see caches:将Cache实现类缓存起来。首次并发访问同一个CacheName情况下，这个CacheName可能会创建多个Cache实例
 * ，但是并无大碍。 </li>
 *
 * @author luyi
 */
public class RedisCacheManager implements CacheManager {

    private final RedisSimpleValueService simpleValueService;
    private final RedisMapValueService mapValueService;

    public RedisCacheManager(RedisSimpleValueService simpleValueService,
                             RedisMapValueService mapValueService) {
        this.simpleValueService = simpleValueService;
        this.mapValueService = mapValueService;
    }

    ConcurrentHashMap<String, Cache> caches = new ConcurrentHashMap<>(32);

    @Override
    public Cache getSimpleCache(String cacheName) {
        Cache cache = caches.get(cacheName);
        if (cache == null) {
            cache = new RedisSimpleCache(cacheName, simpleValueService);
            caches.putIfAbsent(cacheName, cache);
        }
        caches.putIfAbsent(cacheName, cache);
        return cache;
    }

    @Override
    public Cache getMapCache(String cacheName) {
        Cache cache = caches.get(cacheName);
        if (cache == null) {
            cache = new RedisMapCache(cacheName, mapValueService);
            caches.putIfAbsent(cacheName, cache);
        }
        return cache;
    }

}
