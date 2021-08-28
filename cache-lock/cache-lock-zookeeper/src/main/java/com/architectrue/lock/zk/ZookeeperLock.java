package com.architectrue.lock.zk;

import lombok.SneakyThrows;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.springframework.lang.Nullable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * ZK分布式锁
 *
 * @author luyi
 */
public class ZookeeperLock implements Lock {

    private final InterProcessLock interProcessMutex;

    public ZookeeperLock(InterProcessLock interProcessMutex) {
        if (interProcessMutex == null) {
            throw new IllegalArgumentException("InterProcessLock is null");
        }
        this.interProcessMutex = interProcessMutex;
    }

    @SneakyThrows
    @Override
    public void lock() {
        interProcessMutex.acquire();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new InterruptedException("ZookeeperLock not implement");
    }

    @SneakyThrows
    @Override
    public boolean tryLock() {
        interProcessMutex.acquire();
        return true;
    }

    @SneakyThrows
    @Override
    public boolean tryLock(long time, @Nullable TimeUnit unit) {
        return interProcessMutex.acquire(time, unit);
    }

    @SneakyThrows
    @Override
    public void unlock() {
        interProcessMutex.release();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
