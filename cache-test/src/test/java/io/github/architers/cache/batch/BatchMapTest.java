package io.github.architers.cache.batch;

import io.github.architers.cache.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * @author luyi
 * 测试批量map操作
 */
@SpringBootTest
public class BatchMapTest {

    @Autowired
    private BatchMapService batchMapService;

    @Test
    public void batchPutMap() {
        Map<String, UserInfo> userMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername("test" + i).setPassword(UUID.randomUUID().toString());
            userMap.put(userInfo.getUsername(), userInfo);
        }
        batchMapService.batchPutMap(userMap);
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
        batchMapService.batchPutCollection(userInfos);
    }

    @Test
    public void batchDelete() {
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
        batchMapService.batchDeleteCollection(userInfos);
    }

    /**
     * 删除所有
     */
    @Test
    public void cleanAll() {
        batchMapService.clearAll();
    }

}
