package com.yjiewei.service;

import org.springframework.stereotype.Service;

/**
 * @author yjiewei
 * @date 2021/12/6
 */
@Service("PersonService") // 2.配置完扫描包之后，该注解就能将该类注入到容器中了
public class PersonService {

    public void update() {
        System.out.println("模拟更新操作");
    }
}
