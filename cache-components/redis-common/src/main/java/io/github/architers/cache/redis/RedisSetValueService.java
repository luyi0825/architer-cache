package io.github.architers.cache.redis;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * set缓存值操作
 *
 * @author luyi
 * @version 1.0.0
 */
@Component
public class RedisSetValueService {

    private final RedisTemplate<String, Object> redisTemplate;


    public RedisSetValueService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public void remove(String key, Object value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    public void add(String key, Collection<Object> values) {
        redisTemplate.opsForSet().add(key, values);
    }

    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        redisTemplate.opsForSet().add(key, value);
        redisTemplate.expire(key, expire, timeUnit);
    }

    public boolean delete(String key) {
        Boolean bool = redisTemplate.delete(key);
        if (bool != null) {
            return bool;
        }
        return true;
    }

    public long delete(Collection<String> keys) {
        Long count = redisTemplate.delete(keys);
        if (count != null) {
            return count;
        }
        return 0;
    }
}
