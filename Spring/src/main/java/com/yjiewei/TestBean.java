package com.yjiewei;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yjiewei
 * @date 2021/11/27
 */
public class TestBean {
    public static void main(String[] args) {
        // 1.加载配置文件信息
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        // 2.获取bean对象
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person.toString());
    }


    @Test
    public void test() {
        // 1.加载配置文件信息 这个文件需要放在resources下
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        // 2.获取bean对象
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person.toString());
    }
}
