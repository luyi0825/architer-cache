package io.github.architers.cache.lock;

/**
 * 获取锁失败，
 *
 * @author luyi
 */
public class DefaultLockFailServiceImpl implements LockFailService {
    @Override
    public void throwFailException(Locked locked) {
        throw new RuntimeException("获取锁失败");
    }
}
