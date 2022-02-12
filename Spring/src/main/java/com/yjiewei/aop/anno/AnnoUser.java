package com.yjiewei.aop.anno;

import org.springframework.stereotype.Component;

/**
 * 被增强类
 * @author yjiewei
 * @date 2022/2/12
 */
@Component
public class AnnoUser {
    public void add(){
        System.out.println("添加....");
    }
}
