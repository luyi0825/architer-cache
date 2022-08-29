package io.github.architers.expression;


import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;

/**
 * 缓存解析的context
 *
 * @author luyi
 */
public class ExpressionEvaluationContext extends MethodBasedEvaluationContext {

    public ExpressionEvaluationContext(Object rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
    }



}
