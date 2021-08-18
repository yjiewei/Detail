/*
 * @author yjiewei
 * @date 2021/8/17 21:17
 */
package com.yjiewei.test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Jwt {

    /**
     * 获取JWT令牌
     */
    @Test
    public void getToken() {
        Map<String, Object> map = new HashMap<>();

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 2000);

        /**
         * header可以不写有默认值
         * payload 通常用来存放用户信息
         * signature 是前两个合起来的签名值
         */
        String token = JWT.create().withHeader(map) //header
                .withClaim("userId", 21)//payload
                .withClaim("username", "yjiewei")//payload
                .withExpiresAt(instance.getTime())//指定令牌的过期时间
                .sign(Algorithm.HMAC256("!RHO4$%*^fi$R")); //签名，密钥自己记住
        System.out.println(token);
    }

    /**
     * 令牌验证:根据令牌和签名解析数据
     * 常见异常：
     *   SignatureVerificationException 签名不一致异常
     *   TokenExpiredException          令牌过期异常
     *   AlgorithmMismatchException     算法不匹配异常
     *   InvalidClaimException          失效的payload异常
     */
    @Test
    public void tokenVerify() {
        // token值传入做验证
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MjkyMDg0NjgsInVzZXJJZCI6MjEsInVzZXJuYW1lIjoieWppZXdlaSJ9.e4auZWkykZ2Hu8Q20toaks-4e62gerPlDEPHvhunCnQ";
        /**
         * 用户Id：21
         * 用户名：yjiewei
         * 过期时间：Tue Aug 17 21:54:28 CST 2021
         */
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("!RHO4$%*^fi$R")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token); // 验证并获取解码后的token
        System.out.println("用户Id：" + decodedJWT.getClaim("userId").asInt());
        System.out.println("用户名：" + decodedJWT.getClaim("username").asString());
        System.out.println("过期时间：" + decodedJWT.getExpiresAt());
    }
}
