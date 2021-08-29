package io.github.architers.cache.expression;

import io.github.architers.cache.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class CacheExpressionServiceImpl implements CacheExpressionService {
    /**
     * 私有字段
     */
    private final String privateField = "666";
    /**
     * 公共字段
     */
    public final String publicField = "777";

    /**
     * 私有方法
     */
    private double privateMethod() {
        return Math.random() * 10000000000L;
    }

    /**
     * 共有方法
     */
    public double publicMethod() {
        return Math.random() * 10000000000L;
    }

    @Override
    public void root(UserInfo userInfo) {
        System.out.println("root");
    }

}
