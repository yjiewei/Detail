package com.yjiewei.cglibproxy;

/**
 * 1.需要被代理的类，需要被增强的方法
 * @author yjiewei
 * @date 2021/8/3
 */
public class AliSmsService {
    public String send(String msg){
        System.out.println("发送消息：" + msg);
        return msg;
    }
}
