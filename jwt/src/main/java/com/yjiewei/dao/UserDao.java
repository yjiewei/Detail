/*
 * @author yjiewei
 * @date 2021/8/17 21:52
 */
package com.yjiewei.dao;

import com.yjiewei.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User login(User user); // 直接根据用户名密码进行登录
}
