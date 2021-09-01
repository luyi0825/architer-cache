package io.github.architers.cache.redis;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * redis-map值类型相关逻辑操作
 */
public class RedisMapValueService {

    public final RedisTemplate<Object, Object> redisTemplate;
    private final HashOperations<Object, Object, Object> hashOperations;


    public RedisMapValueService(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    /**
     * @param key     缓存的key
     * @param hashKey redis中map数据中的hashKey
     * @param value   redis中map数据中的值
     */
    public void set(String key, Object hashKey, Object value) {
        hashOperations.put(key, hashKey, value);
    }

    /**
     * @param key        缓存的key
     * @param hashKey    redis中map数据中的hashKey
     * @param value      redis中map数据中的值
     * @param expireTime 过期时间
     * @param timeUnit   过期的时间单位
     */
    public void set(Object key, Object hashKey, Object value, long expireTime, TimeUnit timeUnit) {
        hashOperations.put(key, hashKey, value);
        if (expireTime > 0) {
            redisTemplate.expire(key, expireTime, timeUnit);
        }
    }

    /**
     * @param key     缓存的key
     * @param hashKey redis中map数据中的hashKey
     * @param value   redis中map数据中的值
     * @return true表示设置成功，false表示值已经存在
     */
    public boolean setIfAbsent(Object key, Object hashKey, Object value) {
        return hashOperations.putIfAbsent(key, hashKey, value);
    }

    public Object get(Object key, Object hashKey) {
        return hashOperations.get(key, hashKey);
    }

    public <T> T get(Object key, Object hashKey, Class<T> clazz) {
        Object object = hashOperations.get(key, hashKey);
        if (object instanceof String) {
            return JsonUtils.readValue((String) object, clazz);
        }
        return (T) object;
    }

    public List<Object> multiGet(Object key, Set<Object> hashKeys) {
        return hashOperations.multiGet(key, hashKeys);
    }

    /**
     * @param key
     * @param hashKey
     * @return
     */
    public Long delete(Object key, Object... hashKey) {
        return hashOperations.delete(key, hashKey);
    }

    public void delete(Object key) {
        redisTemplate.delete(key);
    }

    public void multiSet(String key, Map<Object, Object> map, long expire, TimeUnit timeUnit) {
        hashOperations.putAll(key, map);
        if (expire > 0) {
            redisTemplate.expire(key, expire, timeUnit);
        }
    }


    public boolean setIfAbsent(Object key, Object hashKey, Object value, long expire, TimeUnit timeUnit) {
        redisTemplate.expire(key, expire, timeUnit);
        return hashOperations.putIfAbsent(key, hashKey, value);
    }


}
