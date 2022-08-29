package io.github.architers.cache.put;

import io.github.architers.cache.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * 测试putCache注解
 */
@SpringBootTest
public class PutTest {
    @Autowired
    private PutService putService;

    /**
     * 测试一个注解
     */
    @Test
    public void testOnePut() {
        UserInfo userInfo = UserInfo.getRandomUserInfo();
        putService.onePut(userInfo);
    }

    /**
     * 测试2个注解
     */
    @Test
    public void testTwoPut() {
        UserInfo userInfo = UserInfo.getRandomUserInfo();
        putService.twoPut(userInfo);
    }

    /**
     * 测试返回值作为缓存
     */
    @Test
    public void testReturnValue() {
        UserInfo userInfo = UserInfo.getRandomUserInfo();
        userInfo = putService.returnValue(userInfo);
        System.out.println(userInfo);
    }

    /**
     * 测试过期时间
     */
    @Test
    public void testExpireTime() {
        putService.expireTime(UserInfo.getRandomUserInfo());
    }

    /**
     * 测试随机时间
     */
    @Test
    public void testRandomTime() {
        for (int i = 0; i < 1000; i++) {
            putService.randomTime(UserInfo.getRandomUserInfo());
        }
    }

    /**
     * 测试condition
     */
    @Test
    public void testCondition() {
        //不满足
        putService.condition(UserInfo.getRandomUserInfo());
        //满足
        putService.condition(UserInfo.getRandomUserInfo().setUsername("666" + UUID.randomUUID()));
    }

    @Test
    public void testUnless() {
        //不排除
        putService.unless(UserInfo.getRandomUserInfo());
        //排除
        putService.unless(UserInfo.getRandomUserInfo().setUsername("666" + UUID.randomUUID()));
    }

}
