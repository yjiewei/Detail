package com.yjiewei.staticproxy;

/**
 * @author yjiewei
 * @date 2021/8/3
 */
public class SmsProxy implements SmsService{

    private final SmsService smsService;

    public SmsProxy(SmsService smsService) {
        this.smsService = smsService;
    }

    @Override
    public String send(String msg) {
        // 增强方法
        System.out.println("调用方法前处理...");
        smsService.send(msg);
        System.out.println("调用方法后处理...");
        return null;
    }
}
