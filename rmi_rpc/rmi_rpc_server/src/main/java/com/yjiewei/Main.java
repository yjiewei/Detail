/*
 * @author yjiewei
 * @date 2021/8/3 23:39
 */
package com.yjiewei;

import com.yjiewei.api.FirstInterfacce;
import com.yjiewei.impl.FirstRMIImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

// 主方法，创建一个服务实现对象，提供服务，并注册到Registry上
public class Main {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        System.out.println("服务器启动中....");
        // 创建服务对象并注册
        FirstInterfacce firstInterfacce = new FirstRMIImpl();
        LocateRegistry.createRegistry(9999);
        // 绑定到注册中心，格式是rmi://ip:port/path  ，使用rebind也是可以的
        Naming.bind("rmi://localhost:9999/first", firstInterfacce);
        System.out.println("服务器启动完毕...");

        // 通常走到这里应该代码结束了，但是这里是一直阻塞的，
        // 原因就是registry在创建的时候，会自动启动一个子线程，并升级为守护线程（服务线程，精灵线程）
    }
}
