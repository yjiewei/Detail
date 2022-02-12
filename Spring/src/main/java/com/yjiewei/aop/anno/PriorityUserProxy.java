package com.yjiewei.aop.anno;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author yjiewei
 * @date 2022/2/12
 */
@Component
@Aspect
@Order(3)
public class PriorityUserProxy {

    @Before(value = "execution(* com.yjiewei.aop.anno.AnnoUser.add(..))")
    public void before2() {
        System.out.println("我要比另外一个增强类先输出...");
    }
}
