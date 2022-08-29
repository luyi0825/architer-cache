package io.github.architers.cache.cacheable;

import io.github.architers.cache.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service("CacheableUserInfoService")
public class CacheableServiceImpl implements CacheableService {
    private final Logger logger = LoggerFactory.getLogger(CacheableService.class);

    @Override
    public UserInfo oneCacheable(String userName) {
        logger.info("{}查询数据库", "oneCacheable");
        return new UserInfo().setUsername(userName);
    }

    @Override
    public UserInfo twoCacheable(String userName) {
        logger.info("{}查询数据库", "twoCacheable");
        return new UserInfo().setUsername(userName);
    }

    @Override
    public UserInfo expireTime_1(String userName) {
        logger.info("{}查询数据库", "expireTime_1");
        return new UserInfo().setUsername(userName);
    }


    @Override
    public UserInfo expireTime_2(String userName) {
        logger.info("{}查询数据库", "expireTime_2");
        return new UserInfo().setUsername(userName);
    }

    @Override
    public UserInfo expireTime_3(String userName) {
        logger.info("{}查询数据库", "expireTime_3");
        return new UserInfo().setUsername(userName);
    }

    @Override
    public UserInfo randomTime(String userName) {
        logger.info("{}查询数据库:userName是{}", "randomTime", userName);
        return new UserInfo().setUsername(userName);
    }

    @Override
    public UserInfo condition(String userName) {
        logger.info("{}查询数据库:userName是{}", "condition", userName);
        return new UserInfo().setUsername(userName);
    }

    @Override
    public UserInfo unless(String userName) {
        logger.info("{}查询数据库:userName是{}", "unless", userName);
        return new UserInfo().setUsername(userName);
    }

    private final AtomicInteger toGatherCount = new AtomicInteger(0);

    @Override
    public UserInfo toGather(String userName) {
        logger.info("{}查询数据库:userName是{},查询了{}次", "toGather", userName, toGatherCount.incrementAndGet());
        return new UserInfo().setUsername(userName);
    }

    private final AtomicInteger toLockGatherCount = new AtomicInteger(0);

    @Override
    public UserInfo testLockToGather(String userName) {
        logger.info("{}查询数据库:userName是{},查询了{}次", "toGather", userName, toLockGatherCount.incrementAndGet());
        return new UserInfo().setUsername(userName);
    }

}
