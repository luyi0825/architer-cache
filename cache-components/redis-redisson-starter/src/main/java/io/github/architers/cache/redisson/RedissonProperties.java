package io.github.architers.redisson;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;


/**
 * @author luyi
 * redisson的属性配置
 */
@ConfigurationProperties(prefix = "customize.redis.redisson")
public class RedissonProperties implements Serializable {
    /**
     * 配置的文件
     */
    private String file;
    /**
     * 配置
     */
    private RedissonConfig config;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public RedissonConfig getConfig() {
        return config;
    }

    public void setConfig(RedissonConfig config) {
        this.config = config;
    }
}
