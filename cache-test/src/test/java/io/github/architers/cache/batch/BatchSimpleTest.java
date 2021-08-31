package io.github.architers.cache.batch;

import io.github.architers.cache.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

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

    /**
     * 批量放置集合值
     */
    @Test
    public void batchPutCollection() {
        List<UserInfo> userInfos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername("test" + i).setPassword(UUID.randomUUID().toString());
            userInfos.add(userInfo);
        }
        batchSimpleService.batchPutCollection(userInfos);
    }

    @Test
    public void batchDeleteCollection() {
        List<Object> userInfos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUsername("test" + i).setPassword(UUID.randomUUID().toString());
                userInfos.add(userInfo);
            } else {
                userInfos.add("test" + i);
            }
        }
        batchSimpleService.batchDeleteCollection(userInfos);
    }


}
