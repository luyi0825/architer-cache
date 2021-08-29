package io.github.architers.cache.lock;


import io.github.architers.cache.expression.ExpressionMetadata;

import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * @version 1.0.0
 * 锁的执行器
 * <li>获取锁后执行后边的逻辑</li>
 * <li>没有获取到锁就按照失败策略执行流程</li>
 */
public class LockExecute {

    private final LockFactory lockFactory;

    public LockExecute(LockFactory lockFactory) {
        this.lockFactory = lockFactory;
    }

    public Object execute(Locked locked, ExpressionMetadata expressionMetadata, LockExecuteFunction function) throws Throwable {
        if (locked != null) {
            //从锁的工厂中获取锁
            Lock lock = lockFactory.get(locked, expressionMetadata);
            if (LockService.FAIL_LOCK != lock) {
                try {
                    return function.execute();
                } finally {
                    if (lock != null) {
                        lock.unlock();
                    }
                }
            }
            //没有获取到锁
            throw new RuntimeException("没有获取到锁");
        } else {
            return function.execute();
        }
    }
}
