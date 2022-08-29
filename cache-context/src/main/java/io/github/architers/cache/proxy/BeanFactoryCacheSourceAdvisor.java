package io.github.architers.proxy;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.lang.Nullable;


/**
 * 缓存的 Advisor(通知）
 *
 * @author luyi
 */
public class BeanFactoryCacheSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    @Nullable
    private CacheOperationSource cacheOperationSource;

    private CacheOperationSourcePointcut pointcut = new CacheOperationSourcePointcut() {
        @Override
        public CacheOperationSource getCacheOperationSource() {
            return cacheOperationSource;
        }
    };

    public BeanFactoryCacheSourceAdvisor() {

    }

    public BeanFactoryCacheSourceAdvisor(CacheOperationSource cacheOperationSource, CacheOperationSourcePointcut pointcut) {
        this.cacheOperationSource = cacheOperationSource;
        this.pointcut = pointcut;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    /**
     * 设置切点类过滤器
     *
     * @param classFilter 类过滤器
     */
    public void setClassFilter(ClassFilter classFilter) {
        this.pointcut.setClassFilter(classFilter);
    }

    public void setCacheOperationSource(@Nullable CacheOperationSource cacheOperationSource) {
        this.cacheOperationSource = cacheOperationSource;
    }

    public void setPointcut(CacheOperationSourcePointcut pointcut) {
        this.pointcut = pointcut;
    }
}




