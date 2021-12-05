package com.yjiewei;

/**
 * 测试bean的生命周期
 * @author yjiewei
 * @date 2021/12/5
 */
public class User {
    public User() {
        System.out.println("第一步：执行无参数构造bean实例");
    }
    private String username;

    public void setUsername(String username) {
        System.out.println("第二步：调用set方法设置属性值");
        this.username = username;
    }

    public void initMethod() {
        System.out.println("第三步：执行初始化方法");
    }

    // 第四步：获取对象之后使用


    public void destroy() {
        System.out.println("第五步：执行销毁方法");
    }
}
