package io.github.architers.redisson;

import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;


/**
 * @author luyi
 * redisson的属性配置
 */
@ConfigurationProperties(prefix = "customize.redisson")
public class RedissonProperties implements Serializable {
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
