package io.github.architers.cache.batch;

import io.github.architers.cache.CacheConstants;
import io.github.architers.cache.UserInfo;
import io.github.architers.cache.annotation.PutCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class BatchSimpleService {
    private final Logger logger = LoggerFactory.getLogger(BatchSimpleService.class);

    @PutCache(cacheName = "'BatchSimpleService_batchPutMap'", key = CacheConstants.BATCH_CACHE_KEY, cacheValue = "#userMap")
    public void batchPutMap(Map<String, UserInfo> userMap) {
        logger.info("批量插入");
    }
}
