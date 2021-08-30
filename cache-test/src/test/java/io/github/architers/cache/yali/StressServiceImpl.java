package io.github.architers.cache.yali;

import io.github.architers.cache.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class StressServiceImpl implements StressService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public UserInfo findByNameDirect(String name) {
        return (UserInfo) redisTemplate.opsForValue().get(name);
    }

    @Override
    public UserInfo findByNameAnnotation(String name) {
       // Assertions.fail();
        return UserInfo.getRandomUserInfo().setUsername(name);
    }
}
