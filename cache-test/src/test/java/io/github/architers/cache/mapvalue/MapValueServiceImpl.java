package io.github.architers.cache.mapvalue;

import io.github.architers.cache.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class MapValueServiceImpl implements MapValueService {
    @Override
    public UserInfo findByUserName(String userName) {
        return UserInfo.getRandomUserInfo().setUsername(userName);
    }

    @Override
    public void deleteByUserName(String userName) {
        System.out.println("删除缓存");
    }
}
