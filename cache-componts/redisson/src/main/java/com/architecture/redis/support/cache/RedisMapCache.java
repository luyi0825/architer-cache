package com.architecture.redis.support.cache;

import com.architecture.utils.JsonUtils;
import org.redisson.api.RedissonClient;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 */
public class RedisMapCache extends RedisCache {

    private final RedissonClient client;

    public RedisMapCache(String cacheName, RedissonClient client) {
        super(cacheName);
        this.client = client;
    }


    @Override
    public void set(String key, Object value) {
        client.getMap(cacheName).put(key, value);
    }

    @Override
    public void set(Map<String, Object> map) {
        client.getMap(cacheName).putAll(map);
    }

    @Override
    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        client.getMap(cacheName).put(key, value);
        client.getKeys().expire(getCacheKey(key), expire, timeUnit);
    }

    @Override
    public boolean setIfAbsent(String key, Object value) {
        return !(client.getMap(cacheName).putIfAbsent(key, value) == value);
    }

    @Override
    public boolean setIfAbsent(String key, Object value, long expire, TimeUnit timeUnit) {
        if (this.setIfAbsent(key, value)) {
            return client.getKeys().expire(getCacheKey(key), expire, timeUnit);
        }
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
        return client.getMap(cacheName);
    }

    @Override
    public Object get(String key) {
        return client.getMap(cacheName).get(key);
    }

    @Override
    public Collection<Object> multiGet(Set<String> keys) {
        return client.getMap(cacheName).getAll(Collections.singleton(keys)).values();
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        Object value = client.getMap(cacheName).get(key);
        if (value instanceof String) {
            return JsonUtils.readValue((String) value, clazz);
        }
        return (T) value;
    }

    @Override
    public boolean delete(String key) {
        return client.getMap(cacheName).remove(key) != null;
    }

    @Override
    public long multiDelete(Collection<String> keys) {
        return 0;
    }

    @Override
    public Map<Object, Object> getAll() {
        return client.getMap(cacheName).readAllMap();
    }
}
