package com.yjiewei.cglibproxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 自定义的方法拦截器
 * @author yjiewei
 * @date 2021/8/3
 */
public class DebugMethodInterceptor implements MethodInterceptor {
    /**
     * 类似于动态代理中的invoke方法
     * @param o 被代理的对象（需要被增强）
     * @param method 被拦截的方法（需要增强的方法）
     * @param objects 方法入参
     * @param methodProxy 用于调用原始方法
     * @return 调用原始方法返回的对象
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 增强方法
        System.out.println( method + "调用前处理...");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("调用方法后处理...");
        return result;
    }
}
