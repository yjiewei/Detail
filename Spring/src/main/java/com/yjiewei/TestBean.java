package com.yjiewei;

import com.yjiewei.service.UserService;
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
/*        // 2.获取bean对象
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person.toString());

        // 测试类中注入外部bean
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.add();

        // 测试工厂bean
        Person myBean = (Person) applicationContext.getBean("myBean");
        System.out.println("实际上获得的是person对象：" + myBean.toString());*/

        // 测试bean的生命周期 获取对象并使用
        User user = (User) applicationContext.getBean("user");
        System.out.println("第四步：获取对象并使用" + user.toString());

        // 测试bean的生命周期 关闭容器并销毁对象
        applicationContext.close();
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
