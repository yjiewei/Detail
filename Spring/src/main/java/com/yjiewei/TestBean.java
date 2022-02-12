package com.yjiewei;


import com.yjiewei.aop.anno.AnnoUser;
import com.yjiewei.aop.xml.Book;
import com.yjiewei.aop.xml.ConfigAOP;
import com.yjiewei.service.PersonService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
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
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext();// 要传入配置类
        User user = applicationContext.getBean("user", User.class);
        System.out.println(user);

        PersonService personService = (PersonService) applicationContext.getBean("PersonService");
        personService.update();
    }

    /**
     * 测试用aspect注解方式实现AOP 可行
     */
    @Test
    public void test3() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        AnnoUser user = context.getBean(AnnoUser.class);
        user.add();
    }

    /**
     * 测试用aspect配置文件的方式实现AOP 可行
     */
    @Test
    public void test4() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
        Book book = context.getBean(Book.class);
        book.buy();
    }

    /**
     * 测试完全使用aspect注解的方式实现AOP 可行
     */
    @Test
    public void test5() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigAOP.class);
        AnnoUser user = context.getBean(AnnoUser.class);
        user.add();
    }
}
