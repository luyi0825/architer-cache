package io.github.architers.cache.lock;

/**
 * 使用的锁（用什么作为锁）
 *
 * @author luyi
 */
public enum LockEnum {
    /**
     * 默认的锁
     * <li>使用使用该，会使用配置</li>
     */
    DEFAULT,
    /**
     * 没有锁
     */
    NONE,
    /**
     * jdk的本地锁
     */
    JDK,
    /**
     * redis
     */
    REDIS,
    /**
     * zookeeper
     */
    ZK;


}
