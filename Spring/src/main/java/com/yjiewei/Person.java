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

    /**
     * 空参构造器，不然xml中需要设置属性
     */
    public Person() {
    }

    public String getName() {
        return name;
    }

    /**
     * 通过set设置属性值
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
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
