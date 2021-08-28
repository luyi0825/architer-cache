package com.architectrue.lock.zk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Zk分布式锁配置测试类
 */
@SpringBootApplication
public class ZkLockConfigureTest {
    public static void main(String[] args) {
        SpringApplication.run(ZkLockConfigureTest.class, args);
    }
}
