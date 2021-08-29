
package io.github.architers.cache.redisson;


import io.github.architers.cache.lock.LockService;
import io.github.architers.cache.redis.RedisMapValueService;
import io.github.architers.cache.redis.RedisSimpleValueService;
import io.github.architers.cache.redis.RedisCacheManager;
import io.github.architers.cache.redisson.support.RedisLockServiceImpl;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redisson配置类
 *
 * @author luyi
 */
@Configuration
@ComponentScan("io.github.architers.cache.redisson")
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonConfiguration {

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public RedissonClient redissonClient(RedissonProperties redisProperties) {
        Config config;
        if (redisProperties != null && redisProperties.getConfig() != null) {
            config = redisProperties.getConfig();
        } else {
            config = new Config();
            config.setCodec(new JsonJacksonCodec());
            config.useSingleServer().setAddress("redis://localhost:6379");
        }
        return Redisson.create(config);
    }


    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }


    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedissonConnectionFactory redisConnectionFactory) {
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public RedisSimpleValueService redisValueService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisSimpleValueService(redisTemplate);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public RedisMapValueService redisMapValueService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisMapValueService(redisTemplate);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public RedisCacheManager redisCacheManager(RedisSimpleValueService redisSimpleValueService,
                                               RedisMapValueService redisMapValueService) {
        return new RedisCacheManager(redisSimpleValueService, redisMapValueService);
    }


    @Bean("redisLock")
    public LockService lockService(RedissonClient redissonClient) {
        return new RedisLockServiceImpl(redissonClient);
    }

}
