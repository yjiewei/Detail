package com.yjiewei.dynamicproxy;

import java.lang.reflect.Proxy;

/**
 * 获取代理对象的工厂
 * 要代理其他对象，你得告诉工厂他的信息啊
 * @author yjiewei
 * @date 2021/8/3
 */
public class JdkProxyFactory {
    public static Object getProxy(Object target){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),  // 目标类的类加载
                target.getClass().getInterfaces(),   // 代理要实现的接口，可以多个
                new DebugInvocationHandler(target)   // 代理对象对应的自定义
        );
    }
}
