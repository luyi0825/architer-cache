package io.github.architers.cache.mixed;

import io.github.architers.cache.UserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * 测试混合注解
 */
@SpringBootTest
public class MixCacheTest {
    @Autowired
    private MixCacheService mixCacheService;

    @Test
    public void findByUserName() {
        String uuid = UUID.randomUUID().toString();
        for (int i = 0; i < 100; i++) {
            UserInfo info = mixCacheService.findByUserName(uuid);
            Assertions.assertNotNull(info);
        }
    }


}
