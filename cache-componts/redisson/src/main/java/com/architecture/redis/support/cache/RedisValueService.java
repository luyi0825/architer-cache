package com.architecture.redis.support.cache;

import com.architecture.context.cache.CacheConstants;
import com.architecture.utils.JsonUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * redisTemplate的opsForValue的工具类
 * <li>注意:这个和ValueRedisCache的区别是:本类key会直接作为缓存的key,而ValueRedisCache还会加上cacheName</li>
 */
public class RedisValueService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> valueOperations;

    public RedisValueService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    /**
     * 描述:向redis中放入值:永不过期
     *
     * @param key   缓存的key
     * @param value 缓存的值
     */
    public void set(String key, Object value) {
        valueOperations.set(key, value);
    }

    /**
     * 批量设置，设置的值不会过期
     *
     * @param map 缓存数据，key为缓存key,value为缓存值
     */
    public void set(Map<String, Object> map) {
        valueOperations.multiSet(map);
    }

    /**
     * 得到过期时间
     *
     * @param key      缓存的key
     * @param timeUnit 返回的过期时间的单位
     * @return 过期时间，单位为传递的参数
     */
    public long getExpireTime(String key, TimeUnit timeUnit) {
        Long expireTime = redisTemplate.getExpire(key, timeUnit);
        if (expireTime != null) {
            return expireTime;
        }
        return -2;
    }


    /**
     * 描述:向缓存中存放值，并设置过期时间
     *
     * @param expire   过期时间
     * @param timeUnit 单位
     * @param key      缓存的key
     * @param value    缓存的值
     */
    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        if (expire == CacheConstants.NEVER_EXPIRE) {
            this.set(key, value);
        } else {
            valueOperations.set(key, value, expire, timeUnit);
        }
    }

    /**
     * 描述:如果不存在，就向缓存中设置值(设置的值不过期)
     *
     * @param key   缓存的key
     * @param value 缓存的值
     * @return true为设置成功
     */
    public boolean setIfAbsent(String key, Object value) {
        Boolean bool = valueOperations.setIfAbsent(key, value);
        if (bool != null) {
            return bool;
        }
        return false;
    }

    /**
     * 描述:如果不存在，就向缓存中设置值
     *
     * @param expire   过期的时间
     * @param key      缓存的key
     * @param value    缓存的值
     * @param timeUnit 单位
     * @return 是否设置成功：true成功
     */
    public boolean setIfAbsent(String key, Object value, long expire, TimeUnit timeUnit) {
        Boolean bool = valueOperations.setIfAbsent(key, value, expire, timeUnit);
        if (bool != null) {
            return bool;
        }
        return false;
    }

    /**
     * 描述:如果不存在，就向缓存中设置值
     *
     * @param key   缓存的key
     * @param value 缓存的值
     * @return 是否设置成功：true成功
     */
    public boolean setIfPresent(String key, Object value) {
        Boolean bool = valueOperations.setIfPresent(key, value);
        if (bool != null) {
            return bool;
        }
        return false;
    }

    /**
     * 描述:如果存在，就向缓存中设置值
     *
     * @param expire   过期的时间
     * @param key      缓存的key
     * @param value    缓存的值
     * @param timeUnit 单位
     * @return 是否成功
     */
    public boolean setIfPresent(String key, Object value, long expire, TimeUnit timeUnit) {
        Boolean bool = valueOperations.setIfPresent(key, value, expire, timeUnit);
        if (bool == null) {
            return false;
        }
        return bool;
    }


    //*************************************get**************************************************/

    /**
     * 得到以前的值，并设置新的值
     *
     * @param key   缓存的key
     * @param value 缓存的值
     * @return 原来的缓存值
     */
    public Object getAndSet(String key, Object value) {
        return valueOperations.getAndSet(key, value);
    }

    /**
     * 描述:得到缓存值
     *
     * @param key 缓存的key
     * @return 缓存的值
     */
    public Object get(String key) {
        return valueOperations.get(key);
    }

    /**
     * 批量获取
     */
    public List<Object> multiGet(Collection<String> keys) {
        return valueOperations.multiGet(keys);
    }

    /**
     * 只适合指定类型的值
     *
     * @param key   缓存的key
     * @param clazz 值的类型
     * @return 缓存后T类型的数据
     */
    public <T> T get(String key, Class<T> clazz) {
        Object value = valueOperations.get(key);
        if (value instanceof String) {
            return JsonUtils.readValue((String) value, clazz);
        }
        return (T) value;
    }

    /**
     * 描述:删除缓存
     *
     * @param key 缓存的key
     * @return 是否删除成功
     */
    public boolean delete(String key) {
        Boolean bool = redisTemplate.delete(key);
        if (bool != null) {
            return bool;
        }
        return false;
    }

    /**
     * 批量删除
     *
     * @param keys 需要删除key
     * @return 删除的数量
     */
    public long multiDelete(Collection<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return 0L;
        }
        Long count = redisTemplate.delete(keys);
        if (count == null) {
            return 0;
        }
        return count;
    }

}
