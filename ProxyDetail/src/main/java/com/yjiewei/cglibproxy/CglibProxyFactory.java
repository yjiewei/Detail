package com.yjiewei.cglibproxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author yjiewei
 * @date 2021/8/3
 */
public class CglibProxyFactory {
    public static Object getProxy(Class<?> clazz){
        // 1.创建动态代理增强类
        Enhancer enhancer = new Enhancer();

        // 2.设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());

        // 3.设置被代理类
        enhancer.setSuperclass(clazz);

        // 4.设置方法拦截器
        enhancer.setCallback(new DebugMethodInterceptor());

        // 5.创建代理类
        return enhancer.create();
    }
}
