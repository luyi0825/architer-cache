package io.github.architers.context.lock;

/**
 * 锁的类型
 *
 * @author luyi
 */
public enum LockType {
    /**
     * 没有锁
     */
    NONE,
    /**
     * 读
     */
    READ,
    /**
     * 写
     */
    WRITE,
    /**
     * 重入公平锁
     */
    REENTRANT_FAIR,
    /**
     * 重入非公平锁
     */
    REENTRANT_UNFAIR;


}
