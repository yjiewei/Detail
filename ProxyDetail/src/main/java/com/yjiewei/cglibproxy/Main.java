package com.yjiewei.cglibproxy;

/**
 * @author yjiewei
 * @date 2021/8/3
 */
public class Main {
    public static void main(String[] args) {
        AliSmsService proxy = (AliSmsService) CglibProxyFactory.getProxy(AliSmsService.class); // 实现类的代理
        proxy.send("CGLIB 动态代理");
    }
}
