package io.github.architers.context.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * 锁的接口类
 */
public interface LockService {

    FailLock FAIL_LOCK = new FailLock();

    /**
     * 得到锁的分割符号
     *
     * @return lockName和key的分隔符号
     */
    String getLockSplit();


    /**
     * 获取公平锁，直到获取到锁
     *
     * @param lockName 锁的名称:在zk中.会被替换成/
     * @return 不为空，说明成功获取到锁
     * @throws Exception 获取锁失败后抛出的异常
     */
    Lock tryFairLock(String lockName) throws Exception;


    /**
     * 获取公平锁：在指定的时间获取不到锁，就会返回空
     *
     * @param lockName 锁的名称:zk中.会被替换成/
     * @param time     超时时间
     * @param timeUnit 单位
     * @return 不为空，说明成功获取到锁
     * @throws Exception 获取锁失败的异常
     */
    Lock tryFairLock(String lockName, long time, TimeUnit timeUnit) throws Exception;

    /**
     * 获取非公平锁，直到获取到
     *
     * @param lockName 锁的名称:zk中.会被替换成/
     * @return 不为空，说明成功获取到锁
     * @throws Exception 获取锁失败后抛出的异常
     */
    Lock tryUnfairLock(String lockName) throws Exception;

    /**
     * 获取非公平锁，在指定的时间获取不到锁，就会返回空
     *
     * @param lockName 锁的名称:zk中.会被替换成/
     * @param time     超时时间
     * @param timeUnit 单位
     * @return 不为空，说明成功获取到锁
     * @throws Exception 获取锁失败的异常
     */
    Lock tryUnfairLock(String lockName, long time, TimeUnit timeUnit) throws Exception;

    /**
     * 得到写锁,直到获取到
     *
     * @param lockName 锁的名称:zk中.会被替换成/
     * @return 不为空，说明成功获取到锁
     * @throws Exception 获取锁失败的异常
     */
    Lock tryWriteLock(String lockName) throws Exception;


    /**
     * 得到写锁
     *
     * @param lockName 锁的名称:zk中.会被替换成/
     * @param time     超时时间
     * @param timeUnit 单位
     * @return 不为空，说明成功获取到锁
     * @throws Exception 获取锁失败的异常
     */
    Lock tryWriteLock(String lockName, long time, TimeUnit timeUnit) throws Exception;

    /**
     * 得到读锁，直到获取到
     *
     * @param lockName 锁的名称:zk中.会被替换成/
     * @return 不为空，说明成功获取到锁
     * @throws Exception 获取锁失败的异常
     */
    Lock tryReadLock(String lockName) throws Exception;

    /**
     * 得到读锁，直到获取到
     *
     * @param time     超时时间
     * @param timeUnit 单位
     * @param lockName 锁的名称:zk中.会被替换成/
     * @return 不为空，说明成功获取到锁
     * @throws Exception 获取锁失败的异常
     */
    Lock tryReadLock(String lockName, long time, TimeUnit timeUnit) throws Exception;


    /**
     * 释放锁
     *
     * @param lock tryLock获取到的锁
     */
    default void releaseLock(Lock lock) {
        if (lock != null) {
            lock.unlock();
        }
    }
}
