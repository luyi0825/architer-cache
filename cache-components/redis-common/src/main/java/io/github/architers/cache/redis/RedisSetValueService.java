package io.github.architers.redis;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * set缓存值操作
 *
 * @author luyi
 * @version 1.0.0
 */
@Component
public class RedisSetValueService {

    private final RedisTemplate<Object, Object> redisTemplate;


    public RedisSetValueService(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(Object key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public void remove(Object key, Object value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    public void add(Object key, Collection<Object> values) {
        redisTemplate.opsForSet().add(key, values);
    }

    public void set(Object key, Object value, long expire, TimeUnit timeUnit) {
        redisTemplate.opsForSet().add(key, value);
        redisTemplate.expire(key, expire, timeUnit);
    }

    public boolean delete(Object key) {
        Boolean bool = redisTemplate.delete(key);
        if (bool != null) {
            return bool;
        }
        return true;
    }

    public long delete(Collection<Object> keys) {
        Long count = redisTemplate.delete(keys);
        if (count != null) {
            return count;
        }
        return 0;
    }
}
