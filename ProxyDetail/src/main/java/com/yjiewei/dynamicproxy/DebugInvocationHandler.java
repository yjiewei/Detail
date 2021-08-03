package com.yjiewei.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK动态代理类
 * @author yjiewei
 * @date 2021/8/3
 */
public class DebugInvocationHandler implements InvocationHandler {

    private final Object object;

    public DebugInvocationHandler(Object object) {
        this.object = object;
    }

    @Override // 实际上会调用这个去增强方法
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 增强方法
        System.out.println("调用方法前处理...");
        Object result = method.invoke(object, args);
        System.out.println("调用方法后处理...");
        return result;
    }
}
