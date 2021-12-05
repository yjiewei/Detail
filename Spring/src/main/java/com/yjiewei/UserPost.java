package com.yjiewei;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 配置文件中配置后会把配置文件中的所有bean都配置后置处理器
 * @author yjiewei
 * @date 2021/12/5
 */
public class UserPost implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("第三步之前：在初始化之前执行的方法");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("第三步之前：在初始化之后执行的方法");
        return bean;
    }
}
