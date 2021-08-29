package io.github.architers.cache.lock;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class CacheLockServiceImpl implements CacheLockService {
    private final Logger logger = LoggerFactory.getLogger(CacheLockServiceImpl.class);

    @Override
    public void test1(String lockKey) {
        logger.info("执行test1");
    }
}
