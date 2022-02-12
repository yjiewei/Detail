package com.yjiewei.aop.anno;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 增强类
 * @author yjiewei
 * @date 2022/2/12
 */
@Component
@Aspect // 生成代理对象
@Order(1) // 优先级问题
public class UserProxy {

    /**
     * 前置通知
     */
    @Before(value = "execution(* com.yjiewei.aop.anno.AnnoUser.add(..))")
    public void before() {
        System.out.println("执行被增强方法之前...");
    }

    /**
     * Advice 与切入点表达式相关联，并在切入点匹配的方法执行之前、之后或周围运行。
     * 切入点表达式可以是对命名切入点的简单引用，也可以是就地声明的切入点表达式。
     * 除了前置通知外，还有其他比如 @AfterReturning @AfterThrowing @After @Around 环绕通知中间要执行被增强方法，这里试一下
     */
    @Around(value = "pointExpressionExtract()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕通知前....");
        // 执行被增强的方法
        proceedingJoinPoint.proceed();
        System.out.println("环绕通知后....");
        return null;
    }

    /**
     * 这里切入点表达式都是一样的，可以做一个抽取成方法
     */
    @Pointcut(value = "execution(* com.yjiewei.aop.anno.AnnoUser.add(..))")
    public void pointExpressionExtract() {
    }

    /**
     * 有多个增强类的时候，可以设置优先级，在增强类上添加注解@Order(数字类型值) 数字越小优先级越高
     */

}
