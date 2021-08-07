/*
 * @author yjiewei
 * @date 2021/8/3 23:51
 */
package com.yjiewei;

import com.yjiewei.api.FirstInterfacce;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

// 客户端主方法
public class ClientMain {
    public static void main(String[] args) {
        // 代理对象的创建
        FirstInterfacce firstInterfacce = null;
        // 使用lookup函数找服务，通过名字去找服务，并自动创建代理对象
        // 类型是Object ，对象一定是Proxy的子类型，且一定实现了服务接口
        try {
            firstInterfacce = (FirstInterfacce) Naming.lookup("rmi://localhost:9999/first"); // 自动拼接了请求头请求体什么的
            System.out.println("对象的类型是："+ firstInterfacce.getClass().getName());
            String first = firstInterfacce.first("今天就先到这了...");// 面向代理对象
            System.out.println(first);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
