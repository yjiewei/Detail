package com.yjiewei;

import com.yjiewei.config.SpringConfig;
import com.yjiewei.service.PersonService;
import com.yjiewei.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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

    // 测试第11个部分，0.导包 1.配置扫描目录 2.使用注解
    @Test
    public void test1() {
        // 1.加载配置文件信息 这个文件需要放在resources下
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application2.xml");
        // 2.获取bean对象
        PersonService personService = (PersonService) applicationContext.getBean("PersonService");
        personService.update();
    }

    /**
     * 测试通过配置类来实现注解开发取代xml配置的方式
     */
    @Test
    public void test2() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        User user = applicationContext.getBean("user", User.class);
        System.out.println(user);

        PersonService personService = (PersonService) applicationContext.getBean("PersonService");
        personService.update();
    }
}
