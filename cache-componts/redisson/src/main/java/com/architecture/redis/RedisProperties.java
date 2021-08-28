package com.architecture.redis;

import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;


/**
 * @author luyi
 * redis的属性配置
 */
@ConfigurationProperties(prefix = "customize.redis")
public class RedisProperties implements Serializable {
    /**
     * 配置
     */
    private Config config;

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
