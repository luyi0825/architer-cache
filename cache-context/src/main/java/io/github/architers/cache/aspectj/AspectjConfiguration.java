package io.github.architers.cache.aspectj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * Aspectj 缓存配置的类
 */
@Configuration
public class AspectjConfiguration {

//    @Bean
//    @ConditionalOnMissingBean
//    public CacheAnnotationsParser cacheAnnotationsParser() {
//        return new CacheAnnotationsParser();
//    }


//    @Bean
//    @ConditionalOnMissingBean
//    @Autowired(required = false)
//    public CacheAspectj cacheAspectj(CacheProcess cacheProcess, CacheAnnotationsParser cacheAnnotationsParser) {
//        return new CacheAspectj(cacheProcess, cacheAnnotationsParser);
//    }
}
