package io.github.architers.cache.redis;


import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * set缓存类
 *
 * @author luyi
 * @version 1.0.0
 */
public class RedisSetCache extends BaseRedisCache {

    private final RedisSetValueService setValueService;


    public RedisSetCache(String cacheName, RedisSetValueService setValueService) {
        super(cacheName);
        this.setValueService = setValueService;
    }


    @Override
    public void set(Object key, Object value) {
        setValueService.set(getCacheKey(key), value);
    }

    @Override
    public void multiSet(Object value, long expire, TimeUnit timeUnit) {

    }

    @Override
    public void set(Object key, Object value, long expire, TimeUnit timeUnit) {
        setValueService.set(getCacheKey(key), value, expire, timeUnit);
    }

    @Override
    public boolean setIfAbsent(Object key, Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public List<Object> multiGet(Set<Object> keys) {
        keys = keys.stream().map(this::getCacheKey).collect(Collectors.toSet());
        return null;
    }

    @Override
    public <T> T get(Object key, Class<T> clazz) {
        return null;
    }

    @Override
    public boolean delete(Object key) {
        return setValueService.delete(getCacheKey(key));
    }

    @Override
    public long multiDelete(Collection<Object> keys) {
        keys = keys.stream().map(this::getCacheKey).collect(Collectors.toList());
        return setValueService.delete(keys);
    }

    @Override
    public Map<Object, Object> getAll() {
        throw new RuntimeException("不支持的操作");
    }

    @Override
    public void clearAll() {

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
