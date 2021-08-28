//package com.architecture.context.cache.aspectj;
//
//
//import com.architecture.context.cache.CacheAnnotationsParser;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//
///**
// * @author luyi
// * @date 2020-12-20
// * @TODO 判断多个缓存注解会怎么样
// */
//@Aspect
//@Component
//public class CacheAspectj {
//
//
//    private final CacheAnnotationsParser cacheAnnotationsParser;
//
//    public CacheAspectj(CacheAnnotationsParser cacheAnnotationsParser) {
//        //  this.cacheProcess = cacheProcess;
//        this.cacheAnnotationsParser = cacheAnnotationsParser;
//    }
//
//    /**
//     * caching切点
//     */
//    @Pointcut("@annotation(com.architecture.context.cache.annotation.Cacheable)")
//    public void cacheablePointcut() {
//    }
//
//    /**
//     * 放置putCache切点
//     */
//    @Pointcut("@annotation(com.architecture.context.cache.annotation.PutCache)")
//    public void putCachePointcut() {
//    }
//
//    /**
//     * 删除deleteCache切点
//     */
//    @Pointcut("@annotation(com.architecture.context.cache.annotation.DeleteCache)")
//    public void deleteCachePointcut() {
//    }
//
//    /**
//     * 多个缓存操作的节点
//     */
//    @Pointcut("@annotation(com.architecture.context.cache.annotation.Caching)")
//    public void cachingPointcut() {
//    }
//
//
//    @Around("cacheablePointcut()")
//    public Object cacheable(ProceedingJoinPoint jp) {
//        return handler(jp);
//    }
//
//    @Around("putCachePointcut()")
//    public Object putCaching(ProceedingJoinPoint jp) {
//        return handler(jp);
//    }
//
//    @Around("deleteCachePointcut()")
//    public Object deleteCache(ProceedingJoinPoint jp) {
//        return handler(jp);
//    }
//
//    @Around("cachingPointcut()")
//    public Object caching(ProceedingJoinPoint jp) {
//        return handler(jp);
//    }
//
//
//    public Object handler(ProceedingJoinPoint proceedingJoinPoint) {
//        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        Object target = proceedingJoinPoint.getTarget();
//        return null;
//        //return cacheProcess.process(target, method, proceedingJoinPoint.getArgs(), cacheAnnotationsParser.parse(method));
//    }
//
//
//}
