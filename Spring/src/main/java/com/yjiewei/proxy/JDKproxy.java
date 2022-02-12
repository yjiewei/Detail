package com.yjiewei.proxy;

import com.yjiewei.proxy.impl.UserDao;
import com.yjiewei.proxy.impl.UserDaoImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 3.创建proxy接口代理对象
 * @author yjiewei
 * @date 2021/12/12
 */
public class JDKproxy {
    public static void main(String[] args) {
        Class[] interfaces = {UserDao.class};
        UserDaoImpl userDao = new UserDaoImpl();
        // 强转
        // 第一个参数：当前接口代理类形象的类加载器；
        // 第二个参数：接口类；
        // 第三个参数：invocationHandler创建代理对象并实现方法增强
        UserDao dao = (UserDao) Proxy.newProxyInstance(JDKproxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
        int result = dao.add(1, 2);
        System.out.println("执行结果：" + result);
    }
}

// 创建代理对象代码
class UserDaoProxy implements InvocationHandler{

    // 1.把创建的是谁的代理对象，把谁传递过来
    // 2.有参数构造传递
    private Object obj;
    public UserDaoProxy(Object object) {
        this.obj = object;
    }

    // 增强的逻辑
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 方法之前
        System.out.println("方法增强前...");

        // 被增强方法
        Object res = method.invoke(obj, args);

        // 方法之后
        System.out.println("方法增强后...");

        return res;
    }
}
