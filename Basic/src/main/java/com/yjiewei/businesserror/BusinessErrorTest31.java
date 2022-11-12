/*
 * @author yangjiewei
 * @date 2022/11/12 21:56
 */
package com.yjiewei.businesserror;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;

/**
 * 31 | 加餐1：带你吃透课程中Java 8的那些重要知识点（一）
 */
@Slf4j
public class BusinessErrorTest31 {

    @Test
    public void test1() {
        //Predicate接口是输入一个参数，返回布尔值。我们通过and方法组合两个Predicate条件，判断是否值大于0并且是偶数
        Predicate<Integer> positiveNumber = i -> i > 0;
        Predicate<Integer> evenNumber = i -> i % 2 == 0;
        Assert.isTrue(positiveNumber.and(evenNumber).test(2));

        //Consumer接口是消费一个数据。我们通过andThen方法组合调用两个Consumer，输出两行abcdefg
        Consumer<String> print = System.out::println;
        print.accept("abcde");
        print.andThen(print).accept("abcde");

        //Function接口是输入一个数据，计算后输出一个数据。我们先把字符串转换为大写，然后通过andThen组合另一个Function实现字符串拼接
        Function<String, String> upCase = String::toUpperCase;
        Function<String, String> concat = s -> s.concat(s);
        String yangjiewei = upCase.andThen(concat).apply("yangjiewei");
        System.out.println(yangjiewei);

        //Supplier是提供一个数据的接口。这里我们实现获取一个随机数
        Supplier<Integer> random = () -> ThreadLocalRandom.current().nextInt();
        System.out.println(random.get());

        //BinaryOperator是输入两个同类型参数，输出一个同类型参数的接口。
        // 这里我们通过方法引用获得一个整数加法操作，通过Lambda表达式定义一个减法操作，然后依次调用
        BinaryOperator<Integer> add = Integer::sum;
        BinaryOperator<Integer> sub = (a,b) -> a-b;
        Integer result = sub.apply(add.apply(1, 2), 3);
        System.out.println(result);

    }

}
