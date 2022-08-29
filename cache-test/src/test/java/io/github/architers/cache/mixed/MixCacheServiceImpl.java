package io.github.architers.cache.mixed;

import io.github.architers.cache.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author luyi
 * 混合注解操作
 */
@Service
public class MixCacheServiceImpl implements MixCacheService {
    private final Logger logger = LoggerFactory.getLogger(MixCacheServiceImpl.class);

    @Override
    public UserInfo findByUserName(String userName) {
        logger.info("findByUserName：{}", userName);
        return UserInfo.getRandomUserInfo().setUsername(userName);
    }
}
