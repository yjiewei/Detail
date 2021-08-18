/*
 * @author yjiewei
 * @date 2021/8/17 22:03
 */
package com.yjiewei.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.yjiewei.entity.User;
import com.yjiewei.service.UserService;
import com.yjiewei.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/user/login")
    public Map<String, Object> login(User user){
        log.info(user.getName());
        log.info(user.getPassword());
        Map<String, Object> map = new HashMap<>();
        try{
            User login = userService.login(user);
            Map<String, String> payload = new HashMap<>();
            payload.put("id", String.valueOf(login.getId()));
            payload.put("name", login.getName());
            payload.put("password", login.getPassword());

            // 生成token
            String token = JWTUtil.getToken(payload);

            map.put("status", true);
            map.put("msg", "登录成功");
            map.put("token", token); // 响应token，存储在客户端
        }catch (Exception e) {
            log.info("登录失败...");
            map.put("status", false);
            map.put("msg", "登录失败");
        }
        return map;
    }

    @PostMapping("/user/test")
    public Map<String, Object> test(HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtil.verify(token);
        String id = verify.getClaim("id").asString(); // 我前面存的时候转字符串了
        String name = verify.getClaim("name").asString();
        log.info("用户id：{}", id);
        log.info("用户名: {}", name);

        //TODO:业务逻辑
        Map<String, Object> map = new HashMap<>();
        map.put("status", true);
        map.put("msg", "请求成功");
        return map;
    }

}
