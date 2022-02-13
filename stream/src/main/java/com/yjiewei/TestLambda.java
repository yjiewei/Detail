/*
 * @author yjiewei
 * @date 2022/2/13 16:59
 */
package com.yjiewei;

import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;

public class TestLambda {
    public static void main(String[] args) {
        // 练习1
        // 可以自动转换成lambda表达式
        int i = calculateNum(new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return left * right;
            }
        });
        System.out.println(i);

        // lambda写法
        int i1 = calculateNum((left, right) -> left * right);
        System.out.println(i1);

        System.out.println("==偶数==");
        // 练习2
        printNum(new IntPredicate() {
            @Override
            public boolean test(int value) {
                return value%2==0; // 偶数为true
            }
        });
        System.out.println("==奇数==");
        // lambda写法
        printNum((value) -> value%2!=0);

        // 练习3 alt+回车可以替换哦
        Integer result = typeConvert(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.valueOf(s);
            }
        });
        System.out.println(result);

        Integer integer = typeConvert(s -> Integer.valueOf(s));
        System.out.println(integer);
    }

    // 参数是一个函数式接口
    public static int calculateNum(IntBinaryOperator operator) {
        int a = 10;
        int b = 20;
        return operator.applyAsInt(a, b);
    }

    // 练习2
    public static void printNum(IntPredicate predicate) {
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        for (int i : arr) {
            if (predicate.test(i)) {
                System.out.println(i);
            }
        }
    }

    // 练习3 <R> R 是方法泛型
    public static <R> R typeConvert(Function<String, R> function) {
        String str = "12345";
        return function.apply(str);
    }
}
