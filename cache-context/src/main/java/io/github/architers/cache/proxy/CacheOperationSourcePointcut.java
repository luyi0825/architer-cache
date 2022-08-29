package io.github.architers.proxy;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;


import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 缓存操作切点
 *
 * @author luyi
 * @see org.springframework.cache.interceptor.CacheOperationSourcePointcut 模仿的这个类
 */
public abstract class CacheOperationSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {


    public CacheOperationSourcePointcut() {
        this.setClassFilter(new CacheOperationSourceClassFilter());
    }

    public CacheOperationSourcePointcut(@Nullable ClassFilter classFilter) {
        this.setClassFilter(classFilter);
    }


    /**
     * 是否匹配
     *
     * @param method      方法
     * @param targetClass 目标类
     * @return 是否满足，true表示该类有缓存的注解
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        CacheOperationSource cas = getCacheOperationSource();
        return (cas != null && !CollectionUtils.isEmpty(cas.getCacheOperations(method, targetClass)));
    }


    public abstract CacheOperationSource getCacheOperationSource();

    /**
     * {@link ClassFilter} that delegates to {@link org.springframework.cache.interceptor.CacheOperationSource#isCandidateClass}
     * for filtering classes whose methods are not worth searching to begin with.
     */
    private class CacheOperationSourceClassFilter implements ClassFilter {

        @Override
        public boolean matches(Class<?> clazz) {
            if (CacheManager.class.isAssignableFrom(clazz)) {
                return false;
            }
            CacheOperationSource cas = getCacheOperationSource();
            return (cas == null || cas.isCandidateClass(clazz));
        }
    }

}
