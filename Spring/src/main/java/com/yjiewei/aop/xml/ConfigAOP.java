package com.yjiewei.aop.xml;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author yjiewei
 * @date 2022/2/12
 */
@Configuration
@ComponentScan(basePackages = {"com.yjiewei.aop"})
@EnableAspectJAutoProxy(proxyTargetClass = true) // 生成代理对象，注意这个默认是false
public class ConfigAOP {
}
