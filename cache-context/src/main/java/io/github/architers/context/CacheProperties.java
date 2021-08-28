package io.github.architers.context;

import io.github.architers.context.lock.LockEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 默认的缓存属性配置
 *
 * @author luyi
 */
@ConfigurationProperties(prefix = "customize.cache")
public class CacheProperties {
    /**
     * 使用什么锁
     */
    private LockEnum lockEnum = LockEnum.JDK;

    public LockEnum getLock() {
        return lockEnum;
    }

    public void setLock(LockEnum lockEnum) {
        this.lockEnum = lockEnum;
    }

}
