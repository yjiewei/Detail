/*
 * @author yjiewei
 * @date 2021/8/3 23:30
 */
package com.yjiewei.impl;

import com.yjiewei.api.FirstInterfacce;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// 实现远程服务接口，所有的远程服务实现，必须是Remote接口直接或间接实现类
// 如果不会创建基于RMI的服务标准实现，可以继承UnicastRemoteObject类型
public class FirstRMIImpl extends UnicastRemoteObject implements FirstInterfacce, Remote {

    public FirstRMIImpl() throws RemoteException { // 子类必须抛出与父类相同的异常
    }

    @Override
    public String first(String name) throws RemoteException {
        System.out.println("客户端的请求参数是：" + name);
        return "你好，" + name;
    }
}
