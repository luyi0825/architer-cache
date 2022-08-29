package io.github.architers.lock;

/**
 * @author luyi
 * 获取锁失败的流程
 */
public interface LockFailService {
    /**
     * 抛出失败的一样
     * @param locked
     */
    void throwFailException(Locked locked);

}
