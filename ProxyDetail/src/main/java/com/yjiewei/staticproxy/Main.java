package com.yjiewei.staticproxy;

/**
 * @author yjiewei
 * @date 2021/8/3
 */
public class Main {
    public static void main(String[] args) {
        SmsService smsService = new SmsServiceImpl();
        SmsProxy smsProxy = new SmsProxy(smsService);
        smsProxy.send("静态代理，需要代理去实现接口...");
    }
}
