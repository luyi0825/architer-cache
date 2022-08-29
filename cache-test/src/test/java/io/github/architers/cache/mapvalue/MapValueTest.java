package io.github.architers.cache.mapvalue;


import io.github.architers.cache.UserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author luyi
 * 测试缓存的是map值缓存注解
 */
@SpringBootTest
public class MapValueTest {

    @Autowired
    private MapValueService mapValueService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String uuid = "test";

    @Test
    public void testFind() {
        UserInfo userInfo = mapValueService.findByUserName(uuid);
        Assertions.assertNotNull(userInfo);
    }

    @Test
    public void testDelete() {
        mapValueService.deleteByUserName(uuid);
        UserInfo userInfo = (UserInfo) redisTemplate.opsForHash().get(MapValueService.cacheName, uuid);
        Assertions.assertNull(userInfo);
    }
}
