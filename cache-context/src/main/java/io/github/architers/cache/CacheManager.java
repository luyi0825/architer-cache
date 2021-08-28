package io.github.architers.cache;


/**
 * @author luyi
 * 注解缓存操作service接口层
 */
public interface CacheManager {
    /**
     * 得到缓存
     *
     * @param cacheName 缓存名称
     */
    Cache getSimpleCache(String cacheName);

    Cache getMapCache(String cacheName);

}
