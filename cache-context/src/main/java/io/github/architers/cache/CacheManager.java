package io.github.architers;


/**
 * @author luyi
 * @version 1.0.0
 * 缓存管理接口类，用于获取不同缓存的实例对象
 */
public interface CacheManager {
    /**
     * 得到简单缓存（key-value的形式）
     *
     * @param cacheName 缓存名称，不能为空
     * @return 简单缓存对象实例
     */
    Cache getSimpleCache(String cacheName);

    /**
     * 得到map形式的缓存
     *
     * @param cacheName 缓存名称，不能为空
     * @return map缓存对象实例
     */
    Cache getMapCache(String cacheName);

}
