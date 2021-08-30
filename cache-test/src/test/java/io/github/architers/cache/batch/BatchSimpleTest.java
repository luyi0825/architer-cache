package io.github.architers.cache.batch;

import io.github.architers.cache.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author luyi
 * 简单值批量操作测试
 */
@SpringBootTest
public class BatchSimpleTest {

    @Autowired
    private BatchSimpleService batchSimpleService;

    /**
     * 测试批量put类型为Map的值
     */
    @Test
    public void batchPutMap() {
        Map<String, UserInfo> userMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername("test" + i).setPassword(UUID.randomUUID().toString());
            userMap.put(userInfo.getUsername(), userInfo);
        }
        batchSimpleService.batchPutMap(userMap);
    }


}
