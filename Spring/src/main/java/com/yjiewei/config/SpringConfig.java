package com.yjiewei.config;

import com.yjiewei.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 作为配置类，替代xml配置文件
 * @author yjiewei
 * @date 2021/12/12
 */
@Configuration
@ComponentScan(basePackages = {"com.yjiewei.service"}) // 这里我用com.yjiewei的时候出错了，不知道什么原因导致
public class SpringConfig {

    @Bean
    public User user(){
        return new User();
    }
}
