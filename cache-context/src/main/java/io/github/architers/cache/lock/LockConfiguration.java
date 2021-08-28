package io.github.architers.cache.lock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 */
@ConditionalOnProperty(prefix = "customize.lock", name = {"enabled"}, havingValue = "true", matchIfMissing = false)
@Configuration
@EnableConfigurationProperties(LockProperties.class)
public class LockConfiguration {
}
