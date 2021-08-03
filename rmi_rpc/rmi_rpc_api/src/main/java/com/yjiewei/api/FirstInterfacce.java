/*
 * @author yjiewei
 * @date 2021/8/3 23:25
 */
package com.yjiewei.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

// 定义一个远程服务接口，这是RMI强制要求，必须是Remote接口的实现
public interface FirstInterfacce extends Remote { // 标记接口

    // 强制抛出 RemoteException 异常
    String first(String name) throws RemoteException;

}
