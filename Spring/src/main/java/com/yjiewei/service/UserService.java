package com.yjiewei.service;

import com.yjiewei.dao.UserDao;

/**
 * @author yjiewei
 * @date 2021/12/5
 */
public class UserService {

    // 1.创建UserDao类型属性，生成set方法
    // 2.再通过application.xml去注册bean对象，再userService bean中注入userDao
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add() {
        System.out.println("test service set dao");
        userDao.update();
    }

}
