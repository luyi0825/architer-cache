package io.github.architers.proxy;

/**
 * 方法返回值功能函数：控制返回值和多次调用
 *
 * @author luyi
 */
public interface MethodReturnValueFunction {
    /**
     * 处理
     *
     * @return 调用方法后返回的值
     * @throws Throwable 调用方法抛出的异常
     */
    default Object proceed() throws Throwable {
        return null;
    }

    /**
     * 设置返回值
     *
     * @param value 返回值
     */
    void setValue(Object value);
}
