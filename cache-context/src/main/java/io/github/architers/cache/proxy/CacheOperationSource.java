package io.github.architers.proxy;


import io.github.architers.operation.Operation;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author luyi
 * @see org.springframework.cache.interceptor.CacheOperationSource 模仿的这个类
 */
public interface CacheOperationSource {
    /**
     * 类是否满足
     *
     * @param targetClass 目标类
     * @return 如果已知该类在类或方法级别没有缓存操作元数据，则返回false否则返回false 。 否则为tru
     */
    default boolean isCandidateClass(Class<?> targetClass) {
        return true;
    }

    /**
     * 得到缓存操作
     *
     * @param method      方法
     * @param targetClass 目标类
     * @return 解析的注解操作
     */
    @Nullable
    Collection<Operation> getCacheOperations(Method method, @Nullable Class<?> targetClass);
}
