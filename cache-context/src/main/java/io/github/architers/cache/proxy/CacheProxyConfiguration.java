package io.github.architers.cache.proxy;


import io.github.architers.cache.CacheAnnotationsParser;
import io.github.architers.cache.operation.CacheOperationHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import java.util.List;

/**
 * @author luyi
 * 缓存代理配置类
 **/
@Configuration
public class CacheProxyConfiguration {


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheAnnotationsParser cacheAnnotationsParser() {
        return new CacheAnnotationsParser();
    }


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AnnotationCacheOperationSource annotationCacheOperationSource() {
        return new AnnotationCacheOperationSource(cacheAnnotationsParser());
    }


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheInterceptor cacheInterceptor(List<CacheOperationHandler> cacheOperationHandlers, CacheAnnotationsParser cacheAnnotationsParser) {
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        cacheInterceptor.setCacheOperationHandlers(cacheOperationHandlers);
        cacheInterceptor.setCacheAnnotationsParser(cacheAnnotationsParser);
        return cacheInterceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BeanFactoryCacheSourceAdvisor beanFactoryCacheSourceAdvisor(AnnotationCacheOperationSource annotationCacheOperationSource, CacheInterceptor cacheInterceptor) {
        BeanFactoryCacheSourceAdvisor advisor = new BeanFactoryCacheSourceAdvisor();
        advisor.setCacheOperationSource(annotationCacheOperationSource);
        advisor.setAdvice(cacheInterceptor);
        return advisor;
    }
}
