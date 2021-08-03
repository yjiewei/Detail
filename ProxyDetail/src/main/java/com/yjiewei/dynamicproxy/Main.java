package com.yjiewei.dynamicproxy;

import java.lang.reflect.Proxy;

/**
 * @author yjiewei
 * @date 2021/8/3
 */
public class Main {
    public static void main(String[] args) {
        SmsService smsService = (SmsService) JdkProxyFactory.getProxy(new SmsServiceImpl());
        smsService.send("发送消息：动态代理，代理的是实现类...");

        System.out.println("====================================");

        SmsServiceImpl service = new SmsServiceImpl();
        SmsService sms = (SmsService) Proxy.newProxyInstance(service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                new DebugInvocationHandler(service));
        sms.send("不使用工厂来获取代理对象");
    }
}
