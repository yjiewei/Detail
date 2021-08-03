package com.yjiewei.staticproxy;

/**
 * @author yjiewei
 * @date 2021/8/3
 */
public class SmsServiceImpl implements SmsService{
    @Override
    public String send(String msg) {
        System.out.println("发送消息：" + msg);
        return msg;
    }
}
