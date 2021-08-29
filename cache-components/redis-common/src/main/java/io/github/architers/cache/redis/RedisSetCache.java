package io.github.architers.cache.redis;


import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * map缓存类
 *
 * @author luyi
 * @version 1.0.0
 */
public class RedisSetCache extends RedisCache {

    private final RedisSetValueService setValueService;


    public RedisSetCache(String cacheName, RedisSetValueService setValueService) {
        super(cacheName);
        this.setValueService = setValueService;
    }


    @Override
    public void set(String key, Object value) {
        setValueService.set(getCacheKey(key), value);
    }

    @Override
    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        setValueService.set(getCacheKey(key), value, expire, timeUnit);
    }

    @Override
    public boolean setIfAbsent(String key, Object value) {
        return false;
    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public List<Object> multiGet(Set<String> keys) {
        keys = keys.stream().map(this::getCacheKey).collect(Collectors.toSet());
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public boolean delete(String key) {
        return setValueService.delete(getCacheKey(key));
    }

    @Override
    public long multiDelete(Collection<String> keys) {
        keys=keys.stream().map(this::getCacheKey).collect(Collectors.toList());
        return setValueService.delete(keys);
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
    public void set(Map<String, Object> map) {

    }


    @Override
    public boolean setIfAbsent(String key, Object value, long expire, TimeUnit timeUnit) {
        return false;
    }

    @Override
    public boolean setIfPresent(String key, Object value) {
        return false;
    }

    @Override
    public boolean setIfPresent(String key, Object value, long expire, TimeUnit timeUnit) {
        return false;
    }

    @Override
    public Object getAndSet(String key, Object value) {
        return null;
    }


}
