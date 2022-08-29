//package io.github.architers.cache.redisson.stresstest;
//
//
//import io.github.architers.cache.redis.JsonUtils;
//import io.github.architers.cache.redis.RedisConstants;
//import org.junit.jupiter.api.Test;
//import org.redisson.api.RList;
//import org.redisson.api.RedissonClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import java.util.UUID;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * @author luyi
// * redisList压力测试
// */
//@SpringBootTest
//public class RedisListStressTest {
//
//    private final String className = this.getClass().getName();
//    private final Logger logger = LoggerFactory.getLogger(RedisListStressTest.class);
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//    @Autowired
//    private RedissonClient redissonClient;
//
//    @Test
//    public void testOpsForListList() {
//        //一百万
//        int num = 1000000;
//        TestModel model = TestModel.build().setCount(num).setRemark("redisTemplate.opsForList单线程测试");
//        model.setStartTime(System.currentTimeMillis());
//        String key = className + RedisConstants.SPLIT + UUID.randomUUID().toString();
//        for (int i = 1; i < num; i++) {
//            redisTemplate.opsForList().rightPush(key, i);
//        }
//        model.setEndTime(System.currentTimeMillis());
//        logger.info("{}", JsonUtils.toJsonString(model));
//    }
//
//
//    @Test
//    public void testList() {
//        //一百万
//        int num = 1000000;
//        TestModel model = TestModel.build().setCount(num).setRemark("redissonClient.getList单线程测试");
//        model.setStartTime(System.currentTimeMillis());
//        String key = className + RedisConstants.SPLIT + UUID.randomUUID().toString();
//        for (int i = 1; i < num; i++) {
//            RList<Object> rList = redissonClient.getList(key);
//            rList.add(i);
//        }
//        model.setEndTime(System.currentTimeMillis());
//        logger.info("{}", JsonUtils.toJsonString(model));
//    }
//
//    @Test
//    public void testGatherList() throws InterruptedException {
//        //一百万
//        int num = 1000000;
//        int threadNum = 10;
//        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
//        TestModel model = TestModel.build().setCount(num).setRemark("redissonClient.testGatherList多线程测试");
//        model.setStartTime(System.currentTimeMillis());
//        String key = className + "_testGatherList" + RedisConstants.SPLIT + UUID.randomUUID().toString();
//        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
//
//        for (int i = 0; i < threadNum; i++) {
//            executorService.submit(() -> {
//                for (int j = 1; j < num / 10; j++) {
//                    RList<Object> rList = redissonClient.getList(key);
//                    rList.add(UUID.randomUUID());
//                }
//                countDownLatch.countDown();
//            });
//        }
//
//        countDownLatch.await();
//        model.setEndTime(System.currentTimeMillis());
//        logger.info("{}", JsonUtils.toJsonString(model));
//    }
//
//    @Test
//    public void testGatherTemplateList() throws InterruptedException {
//        //一百万
//        int num = 1000000;
//        int threadNum = 10;
//        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
//        TestModel model = TestModel.build().setCount(num).setRemark("template.testGatherTemplateList多线程测试");
//        model.setStartTime(System.currentTimeMillis());
//        String key = className + "_testGatherTemplateList" + RedisConstants.SPLIT + UUID.randomUUID().toString();
//        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
//        for (int i = 0; i < threadNum; i++) {
//            executorService.submit(() -> {
//                for (int j = 1; j < num / 10; j++) {
//                    redisTemplate.opsForList().rightPush(key, UUID.randomUUID());
//                }
//                countDownLatch.countDown();
//            });
//        }
//        countDownLatch.await();
//        model.setEndTime(System.currentTimeMillis());
//        logger.info("{}", JsonUtils.toJsonString(model));
//    }
//
//
//}
