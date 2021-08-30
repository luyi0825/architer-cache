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
public class RedisMapCache extends BaseRedisCache {

    private final RedisMapValueService mapValueService;


    public RedisMapCache(String cacheName, RedisMapValueService mapValueService) {
        super(cacheName);
        this.mapValueService = mapValueService;
    }


    @Override
    public void set(Object key, Object value) {
        mapValueService.set(cacheName, key, value);
    }

    @Override
    public void set(Object key, Object value, long expire, TimeUnit timeUnit) {
        mapValueService.set(cacheName, key, value, expire, timeUnit);
    }

    @Override
    public boolean setIfAbsent(Object key, Object value) {
        return mapValueService.setIfAbsent(cacheName, key, value);
    }

    @Override
    public Object get(Object key) {
        return mapValueService.get(cacheName, key);
    }

    @Override
    public List<Object> multiGet(Set<Object> keys) {
        keys = keys.stream().map(this::getCacheKey).collect(Collectors.toSet());
        return mapValueService.multiGet(cacheName, Collections.singleton(keys));
    }

    @Override
    public <T> T get(Object key, Class<T> clazz) {
        return mapValueService.get(cacheName, key, clazz);
    }

    @Override
    public boolean delete(Object key) {
        return mapValueService.delete(cacheName, Collections.singleton(key)) > 0;
    }

    @Override
    public long multiDelete(Collection<Object> keys) {
        return mapValueService.delete(cacheName, keys);
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
    public void set(Map<Object, Object> map) {
        mapValueService.set(cacheName,map);
    }


    @Override
    public boolean setIfAbsent(Object key, Object value, long expire, TimeUnit timeUnit) {
        return mapValueService.setIfAbsent(cacheName,key,value,expire,timeUnit);
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
