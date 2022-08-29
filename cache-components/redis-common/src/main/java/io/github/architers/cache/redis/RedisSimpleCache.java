package io.github.architers.cache.redis;


import io.github.architers.cache.CacheConstants;
import io.github.architers.cache.batch.BatchValueFactory;
import io.github.architers.cache.batch.CacheField;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis工具类--处理简单类型
 *
 * @author luyi
 * @date 2020-12-24
 */
public class RedisSimpleCache extends BaseRedisCache {

    BatchValueFactory batchValueParser = new BatchValueFactory();

    private final RedisSimpleValueService valueService;


    public RedisSimpleCache(String cacheName, RedisSimpleValueService redisSimpleValueService) {
        super(cacheName);
        this.valueService = redisSimpleValueService;
    }


    @Override
    public void set(Object key, Object value) {
        valueService.set(getCacheKey(key), value);
    }

    @Override
    public void multiSet(Object values, long expire, TimeUnit timeUnit) {
        Map<Object, Object> cacheMap = batchValueParser.parseValue2Map(cacheName, RedisConstants.SPLIT, values);
        valueService.multiSet(cacheMap, expire, timeUnit);
    }

    @Override
    public void set(Object key, Object value, long expire, TimeUnit timeUnit) {
        valueService.set(getCacheKey(key), value, expire, timeUnit);
    }

    @Override
    public boolean setIfAbsent(Object key, Object value) {
        return valueService.setIfAbsent(getCacheKey(key), value);
    }

    @Override
    public Object get(Object key) {
        return valueService.get(getCacheKey(key));
    }

    @Override
    public List<Object> multiGet(Set<Object> keys) {
        keys = keys.stream().map(this::getCacheKey).collect(Collectors.toSet());
        return valueService.multiGet(Collections.singleton(keys));
    }

    @Override
    public <T> T get(Object key, Class<T> clazz) {
        return valueService.get(getCacheKey(key), clazz);
    }

    @Override
    public boolean delete(Object key) {
        return valueService.delete(getCacheKey(key));
    }

    @Override
    public long multiDelete(Object keys) {
        Collection<Object> cacheKeys = batchValueParser.parseCacheKeys(cacheName, RedisConstants.SPLIT, keys);
        if (CollectionUtils.isEmpty(cacheKeys)) {
            return 0;
        }
        return valueService.multiDelete(cacheKeys);
    }

    @Override
    public Map<Object, Object> getAll() {
        throw new RuntimeException("不支持的操作");
    }

    @Override
    public void clearAll() {
        throw new RuntimeException("暂时不支持");
    }

    @Override
    public String getCacheName() {
        return cacheName;
    }


    @Override
    public boolean setIfAbsent(Object key, Object value, long expire, TimeUnit timeUnit) {
        return false;
    }

    @Override
    public boolean setIfPresent(Object key, Object value) {
        return false;
    }

    @Override
    public boolean setIfPresent(Object key, Object value, long expire, TimeUnit timeUnit) {
        return false;
    }

    @Override
    public Object getAndSet(Object key, Object value) {
        return null;
    }


}
