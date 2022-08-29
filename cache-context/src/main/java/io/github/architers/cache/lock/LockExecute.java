package io.github.architers.lock;


import io.github.architers.expression.ExpressionMetadata;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * @version 1.0.0
 * 锁的执行器
 * <li>获取锁后执行后边的逻辑</li>
 * <li>没有获取到锁就按照失败策略执行流程</li>
 */
public class LockExecute implements ApplicationContextAware {

    private final LockFactory lockFactory;

    private final LockFailService lockFailService;

    private ApplicationContext applicationContext;

    public LockExecute(LockFactory lockFactory, LockFailService lockFailService) {
        this.lockFactory = lockFactory;
        this.lockFailService = lockFailService;
    }

    public Object execute(Locked locked, ExpressionMetadata expressionMetadata, LockExecuteFunction function) throws Throwable {
        if (locked != null) {
            //从锁的工厂中获取锁
            Lock lock = lockFactory.get(locked, expressionMetadata);
            if (LockService.FAIL_LOCK.equals(lock)) {
                return this.handlerFail(locked, function,expressionMetadata);
            }
            try {
                return function.execute();
            } finally {
                if (lock != null) {
                    lock.unlock();
                }
            }
        }
        return function.execute();


    }

    /**
     * 做失败后的事情
     *
     * @param locked 锁注解信息
     * @return 执行失败返回的参数
     */
    private Object handlerFail(Locked locked, LockExecuteFunction lockExecuteFunction, ExpressionMetadata expressionMetadata) throws Throwable {
        switch (locked.failStrategy()) {
            //抛出异常
            case EXCEPTION:
                lockFailService.throwFailException(locked);
                break;
            //继续执行
            case CONTINUE:
                return lockExecuteFunction.execute();
            case ABANDON:
                return null;
            case CAll_BACK:
                return this.doCallBack(locked, expressionMetadata);
            default:
                break;
        }
        return null;

    }

    /**
     * 执行回调
     *
     * @param locked 锁信息
     */
    private Object doCallBack(Locked locked, ExpressionMetadata expressionMetadata) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String callBack = locked.callBack();
        if (!StringUtils.hasText(callBack)) {
            throw new IllegalArgumentException("callBack is null");
        }
        //方法名称
        String methodName;
        //回调的对象实例
        Object callBackTarget;
        if (callBack.contains("#")) {
            callBackTarget = applicationContext.getBean(callBack.split("#")[0]);
            methodName = callBack.split("#")[1];
        } else {
            callBackTarget = expressionMetadata.getTarget();
            methodName = callBack;
        }
        Method callMethod = callBackTarget.getClass().getDeclaredMethod(methodName, expressionMetadata.getTargetMethod().getParameterTypes());
        //反射回调
        return callMethod.invoke(callBackTarget, expressionMetadata.getArgs());
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
