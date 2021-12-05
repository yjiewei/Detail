package com.yjiewei.factoryBean;

import com.yjiewei.Person;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author yjiewei
 * @date 2021/12/5
 */
// 这里的泛型以及getObject()决定返回的是实际对象
public class MyBean implements FactoryBean<Person> {
    @Override
    public Person getObject() throws Exception {
        Person person = new Person();
        person.setAge(22);
        person.setName("you are not young anymore");
        return person;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
