package io.github.architers.cache.expression;

import io.github.architers.cache.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author luyi
 * 缓存表达式测试
 */
@SpringBootTest
public class CacheExpressionTest {
    @Autowired
    private CacheExpressionService expressionService;

    /**
     * 测试#root
     */
    @Test
    public void testRoot() {
        expressionService.root(UserInfo.getRandomUserInfo());
    }

}
