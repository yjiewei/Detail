package com.yjiewei.proxy.impl;

/**
 * 2.创建接口实现类，实现方法
 * @author yjiewei
 * @date 2021/12/12
 */
public class UserDaoImpl implements UserDao {

    @Override
    public int add(int a, int b) {
        // 如何不修改这段逻辑实现方法增强呢
        System.out.println("执行被增强的方法");
        return a+b;
    }

    @Override
    public String update(String id) {
        return id;
    }
}
