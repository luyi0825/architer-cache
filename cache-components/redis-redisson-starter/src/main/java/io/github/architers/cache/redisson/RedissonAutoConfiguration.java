
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redisson自动配置类
 *
 * @author luyi
 */
@Configuration
@ComponentScan("io.github.architers.cache.redisson")
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {
    @Autowired
    private RedissonProperties redissonProperties;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public RedissonClient redissonClient() {
        Config config = null;
        if (redissonProperties != null && redissonProperties.getConfig() != null) {
            config = redissonProperties.getConfig();
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
    public RedisTemplate<Object, Object> redisTemplate(RedissonConnectionFactory redisConnectionFactory) {
        RedisSerializer<Object> serializer = getRedisSerializer();
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean("stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(RedissonConnectionFactory redisConnectionFactory) {
        RedisSerializer<Object> serializer = getRedisSerializer();
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 得到redis的序列化器
     */
    private RedisSerializer<Object> getRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public RedisSimpleValueService redisValueService(RedisTemplate<Object, Object> redisTemplate) {
        return new RedisSimpleValueService(redisTemplate);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public RedisMapValueService redisMapValueService(RedisTemplate<Object, Object> redisTemplate) {
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
