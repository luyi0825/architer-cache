package io.github.architers.cache;


import io.github.architers.cache.expression.ExpressionParser;
import io.github.architers.cache.lock.DefaultLockFailServiceImpl;
import io.github.architers.cache.lock.LockExecute;
import io.github.architers.cache.lock.LockFailService;
import io.github.architers.cache.operation.CacheableOperationHandler;
import io.github.architers.cache.operation.DeleteCacheOperationHandler;
import io.github.architers.cache.operation.PutCacheOperationHandler;
import io.github.architers.cache.lock.LockFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * 缓存配置，存放各种缓存的公共配置
 */
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfiguration {


    @Bean
    public ExpressionParser keyExpressionParser() {
        return new ExpressionParser();
    }

    @Bean
    public LockFactory lockFactory(ExpressionParser expressionParser) {
        LockFactory lockFactory = new LockFactory();
        lockFactory.setExpressionParser(expressionParser);
        return lockFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public LockFailService lockFailService() {
        return new DefaultLockFailServiceImpl();
    }

    @Bean
    public LockExecute lockExecute(LockFactory lockFactory, LockFailService lockFailService) {
        return new LockExecute(lockFactory, lockFailService);
    }

    @Bean
    public CacheableOperationHandler cacheableOperationHandler(ExpressionParser expressionParser) {
        CacheableOperationHandler cacheableOperationHandler = new CacheableOperationHandler();
        cacheableOperationHandler.setExpressionParser(expressionParser);
        return cacheableOperationHandler;
    }

    @Bean
    public DeleteCacheOperationHandler deleteCacheOperationHandler() {
        return new DeleteCacheOperationHandler();
    }

    @Bean
    public PutCacheOperationHandler putCacheOperationHandler() {
        return new PutCacheOperationHandler();
    }


}
