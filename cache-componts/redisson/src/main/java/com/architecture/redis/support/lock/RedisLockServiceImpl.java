package com.architecture.redis.support.lock;

import com.architecture.context.cache.lock.LockService;
import com.architecture.redis.RedisConstants;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * redis锁实现
 */
public class RedisLockServiceImpl implements LockService {
    private final RedissonClient redissonClient;

    public RedisLockServiceImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public Lock tryFairLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        Lock lock = redissonClient.getFairLock(lockName);
        if (lock.tryLock(time, timeUnit)) {
            return lock;
        }
        return LockService.FAIL_LOCK;
    }


    @Override
    public String getLockSplit() {
        return RedisConstants.SPLIT;
    }

    @Override
    public Lock tryFairLock(String lockName) {
        Lock lock = redissonClient.getFairLock(lockName);
        if (lock.tryLock()) {
            return lock;
        }
        return LockService.FAIL_LOCK;
    }

    @Override
    public Lock tryUnfairLock(String lockName) {
        Lock lock = redissonClient.getLock(lockName);
        if (lock.tryLock()) {
            return lock;
        }
        return LockService.FAIL_LOCK;
    }

    @Override
    public Lock tryUnfairLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        Lock lock = redissonClient.getLock(lockName);
        if (lock.tryLock(time, timeUnit)) {
            return lock;
        }
        return LockService.FAIL_LOCK;
    }

    @Override
    public Lock tryWriteLock(String lockName) {
        Lock lock = redissonClient.getReadWriteLock(lockName).writeLock();
        if (lock.tryLock()) {
            return lock;
        }
        return LockService.FAIL_LOCK;
    }

    @Override
    public Lock tryWriteLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        Lock lock = redissonClient.getReadWriteLock(lockName).writeLock();
        if (lock.tryLock(time, timeUnit)) {
            return lock;
        }
        return LockService.FAIL_LOCK;
    }

    @Override
    public Lock tryReadLock(String lockName) {
        Lock lock = redissonClient.getReadWriteLock(lockName).readLock();
        if (lock.tryLock()) {
            return lock;
        }
        return LockService.FAIL_LOCK;
    }

    @Override
    public Lock tryReadLock(String lockName, long time, TimeUnit timeUnit) {
        Lock lock = redissonClient.getReadWriteLock(lockName).readLock();
        if (lock.tryLock()) {
            return lock;
        }
        return LockService.FAIL_LOCK;
    }
}
