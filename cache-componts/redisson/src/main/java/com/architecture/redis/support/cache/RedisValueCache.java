package com.architecture.redis.support.cache;


import com.architecture.context.exception.ServiceException;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis工具类--处理字符串类型
 * <p>
 * 注意，过期的时间单位都是秒
 *
 * @author luyi
 * @date 2020-12-24
 */
public class RedisValueCache extends RedisCache {


    private final RedisValueService valueService;


    public RedisValueCache(String cacheName, RedisValueService redisValueService) {
        super(cacheName);
        this.valueService = redisValueService;
    }


    @Override
    public void set(String key, Object value) {
        valueService.set(getCacheKey(key), value);
    }


    @Override
    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        valueService.set(getCacheKey(key), value, expire, timeUnit);
    }

    @Override
    public boolean setIfAbsent(String key, Object value) {
        return valueService.setIfAbsent(getCacheKey(key), value);
    }

    @Override
    public Object get(String key) {
        return valueService.get(getCacheKey(key));
    }

    @Override
    public List<Object> multiGet(Set<String> keys) {
        keys = keys.stream().map(this::getCacheKey).collect(Collectors.toSet());
        return valueService.multiGet(keys);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return valueService.get(getCacheKey(key), clazz);
    }

    @Override
    public boolean delete(String key) {
        return valueService.delete(getCacheKey(key));
    }

    @Override
    public long multiDelete(Collection<String> keys) {
        keys = keys.stream().map(this::getCacheKey).collect(Collectors.toList());
        return valueService.multiDelete(keys);
    }

    @Override
    public Map<Object, Object> getAll() {
        throw new ServiceException("不支持的操作");
    }

    @Override
    public String getCacheName() {
        return cacheName;
    }


    @Override
    public void set(Map<String, Object> map) {
        valueService.set(map);
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
