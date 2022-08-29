package io.github.architers.cache.put;

import io.github.architers.cache.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PutServiceImpl implements PutService {
    private Logger logger = LoggerFactory.getLogger(PutServiceImpl.class);

    @Override
    public void onePut(UserInfo userInfo) {
        System.out.println("保存到数据库");
    }

    @Override
    public void twoPut(UserInfo userInfo) {
        System.out.println("保存到数据库");
    }

    @Override
    public UserInfo returnValue(UserInfo userInfo) {
        return userInfo.setPassword("test");
    }

    @Override
    public void expireTime(UserInfo userInfo) {
        logger.info("expireTime保存到数据库");
    }

    @Override
    public void randomTime(UserInfo userInfo) {
        logger.info("randomTime保存到数据库");
    }

    @Override
    public UserInfo condition(UserInfo userInfo) {
        logger.info("condition保存到数据库:{}", userInfo.getUsername());
        return userInfo;
    }

    @Override
    public UserInfo unless(UserInfo userInfo) {
        logger.info("unless保存到数据库:{}", userInfo.getUsername());
        return userInfo;
    }
}
