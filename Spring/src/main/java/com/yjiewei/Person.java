package com.yjiewei;

/**
 * java bean 通过配置文件的方式注入到spring容器中，交给spring去控制
 * @author yjiewei
 * @date 2021/11/27
 */
public class Person {
    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
