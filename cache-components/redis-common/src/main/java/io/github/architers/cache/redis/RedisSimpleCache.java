package io.github.architers.cache.redis;


import io.github.architers.cache.CacheConstants;
import io.github.architers.cache.batch.BatchValueParser;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis工具类--处理简单类型
 * <p>
 * 注意，过期的时间单位都是秒
 *
 * @author luyi
 * @date 2020-12-24
 */
public class RedisSimpleCache extends BaseRedisCache {

    BatchValueParser batchValueParser = new BatchValueParser();

    private final RedisSimpleValueService valueService;


    public RedisSimpleCache(String cacheName, RedisSimpleValueService redisSimpleValueService) {
        super(cacheName);
        this.valueService = redisSimpleValueService;
    }


    @Override
    public void set(Object key, Object value) {
        //判断是不是批量操作
        if (CacheConstants.BATCH_CACHE_KEY.equals(key)) {
            valueService.set(batchValueParser.parse2MapValue(value));
        }
        valueService.set(getCacheKey(key), value);
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
    public long multiDelete(Collection<Object> keys) {
        keys = keys.stream().map(this::getCacheKey).collect(Collectors.toList());
        return valueService.multiDelete(Collections.singleton(keys));
    }

    @Override
    public Map<Object, Object> getAll() {
        throw new RuntimeException("不支持的操作");
    }

    @Override
    public String getCacheName() {
        return cacheName;
    }


    @Override
    public void set(Map<Object, Object> map) {
        valueService.set(map);
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
