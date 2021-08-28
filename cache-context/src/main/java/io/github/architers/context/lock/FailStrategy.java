package io.github.architers.context.lock;

/**
 * @author luyi
 * 锁失败的策略
 */
public enum FailStrategy {
    /**
     * 抛出异常：默认值
     */
    EXCEPTION,
    /**
     * 继续处理业务流程
     */
    CONTINUE,
    /**
     * 放弃任务，也就是不调用方法，返回空
     */
    ABANDON,
    /**
     * 回调
     */
    CAll_BACK

}
