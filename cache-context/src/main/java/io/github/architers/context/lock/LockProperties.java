package io.github.architers.context.lock;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 默认的缓存属性配置
 *
 * @author luyi
 */

@ConfigurationProperties(prefix = "customize.lock")
public class LockProperties {
    private boolean enabled = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
