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

    public final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOperations;


    public RedisMapValueService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    /**
     * @param key     缓存的key
     * @param hashKey redis中map数据中的hashKey
     * @param value   redis中map数据中的值
     */
    public void set(String key, String hashKey, Object value) {
        hashOperations.put(key, hashKey, value);
    }

    /**
     * @param key        缓存的key
     * @param hashKey    redis中map数据中的hashKey
     * @param value      redis中map数据中的值
     * @param expireTime 过期时间
     * @param timeUnit   过期的时间单位
     */
    public void set(String key, String hashKey, Object value, long expireTime, TimeUnit timeUnit) {
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
    public boolean setIfAbsent(String key, String hashKey, Object value) {
        return hashOperations.putIfAbsent(key, hashKey, value);
    }

    public Object get(String key, String hashKey) {
        return hashOperations.get(key, hashKey);
    }

    public <T> T get(String key, String hashKey, Class<T> clazz) {
        Object object = hashOperations.get(key, hashKey);
        if (object instanceof String) {
            return JsonUtils.readValue((String) object, clazz);
        }
        return (T) object;
    }

    public List<Object> multiGet(String key, Set<String> hashKeys) {
        return hashOperations.multiGet(key, hashKeys);
    }

    /**
     * @param key
     * @param hashKey
     * @return
     */
    public Long delete(String key, Collection<String> hashKey) {
        return hashOperations.delete(key, hashKey);
    }

    public void set(String key, Map<String, Object> map) {
        hashOperations.putAll(key, map);
    }

    public boolean setIfAbsent(String key, String hashKey, Object value, long expire, TimeUnit timeUnit) {
        redisTemplate.expire(key, expire, timeUnit);
        return hashOperations.putIfAbsent(key, hashKey, value);
    }
}
