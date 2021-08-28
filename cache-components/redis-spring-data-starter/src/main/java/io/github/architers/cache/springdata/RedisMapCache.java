package io.github.architers.cache.springdata;

import io.github.architers.cache.redis.RedisCache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisMapCache extends RedisCache {
    public RedisMapCache(String cacheName) {
        super(cacheName);
    }

    @Override
    public void set(String key, Object value) {

    }

    @Override
    public void set(Map<String, Object> map) {

    }

    @Override
    public void set(String key, Object value, long expire, TimeUnit timeUnit) {

    }

    @Override
    public boolean setIfAbsent(String key, Object value) {
        return false;
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

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public Collection<Object> multiGet(Set<String> keys) {
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public boolean delete(String key) {
        return false;
    }

    @Override
    public long multiDelete(Collection<String> keys) {
        return 0;
    }

    @Override
    public Map<Object, Object> getAll() {
        return null;
    }
}
