package io.github.architers.cache.batch;

import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Set;

/**
 * @author luyi
 * 批量值解析支持
 */
public interface BatchSupport {
    /**
     * 解析成Map值
     *
     * @param cacheName 缓存名称
     * @param value     值类型为map或者collection(如果为集合，对应的key比如标明CacheKey和CacheValue注解）
     * @return 缓存值解析，key为缓存key,value为缓存值
     */
    Map<?, ?> parse2MapValue(String cacheName, Object value);

    /**
     * 解析缓存key
     *
     * @param cacheName 缓存名称
     * @param value     需要解析的对象
     * @return key的集合
     */
    Set<?> parseCacheKey(@NonNull String cacheName, @NonNull Object value);
}
