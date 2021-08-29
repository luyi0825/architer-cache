package io.github.architers.cache.delete;

import io.github.architers.cache.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeleteServiceImpl implements DeleteService {
    private final Logger logger = LoggerFactory.getLogger(DeleteServiceImpl.class);

    @Override
    public void oneDelete(UserInfo userInfo) {
        logger.info("oneDelete:{}", userInfo.getUsername());
    }
}
