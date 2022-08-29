package io.github.architers.yali;


import io.github.architers.TestModel;
import io.github.architers.UserInfo;
import io.github.architers.redis.JsonUtils;
import io.github.architers.redis.RedisConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

/**
 * 测试直接从缓存取和通过注解代码取值
 */
@SpringBootTest
public class StressTest {

    private final Logger logger = LoggerFactory.getLogger(StressTest.class);

    private final int count = 10000;


    @Autowired
    private StressService stressService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public void addData(String key, UserInfo userInfo) {
        redisTemplate.opsForValue().set(key, userInfo);
    }

    @Test
    public void findByDirect() {
        String uuid = UUID.randomUUID().toString();
        String key = "findByName" + RedisConstants.SPLIT + uuid;
        addData(key, UserInfo.getRandomUserInfo().setUsername(uuid));
        TestModel testModel = TestModel.build().setStartTime(System.currentTimeMillis())
                .setCount(count).setRemark("直接调用方法从缓存查询数据");
        for (int i = 1; i <= count; i++) {
            UserInfo userInfo = stressService.findByNameDirect(key);
            Assertions.assertNotNull(userInfo);
        }
        testModel.setEndTime(System.currentTimeMillis());
        logger.info(JsonUtils.toJsonString(testModel));
    }

    @Test
    public void findByAnnotation() {
        String uuid = UUID.randomUUID().toString();
        String key = "findByName" + RedisConstants.SPLIT + uuid;
        // addData(key, UserInfo.getRandomUserInfo().setUsername(uuid));
        TestModel testModel = TestModel.build().setStartTime(System.currentTimeMillis())
                .setCount(count).setRemark("通过注解缓存查询数据");
        for (int i = 1; i <= count; i++) {
            UserInfo userInfo = stressService.findByNameAnnotation(uuid);
            Assertions.assertNotNull(userInfo);
        }
        testModel.setEndTime(System.currentTimeMillis());
        logger.info(JsonUtils.toJsonString(testModel));
    }
}
