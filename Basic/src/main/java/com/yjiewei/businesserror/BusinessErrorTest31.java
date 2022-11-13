/*
 * @author yangjiewei
 * @date 2022/11/12 21:56
 */
package com.yjiewei.businesserror;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

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

    /**
     * 集合没有加泛型，强转类型出错
     *    Unchecked call to 'map(Function<? super T, ? extends R>)' as a member of raw type 'java.util.stream.Stream'
     *    java.lang.ClassCastException: class java.lang.Integer cannot be cast to class java.lang.Double
     *    new Point2D.Double((double) i % 3, (double) i / 3) 这里已经报错了
     */
    @Test
    public void test2(){
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        double average = calc(ints);
        //如何用一行代码来实现，比较一下可读性
        double result = ints.stream()
                .map(i -> new Point2D.Double((double) i % 3, (double) i / 3))
                .filter(j -> j.getY() > 1)
                .mapToDouble(value -> value.distance(0, 0))// 转换成double
                .average()// optional 空值也可以
                .orElse(0);
        System.out.println("用传统方法结果是：" + average + "，使用stream配合lambda表达式的方法结果是：" + result);
    }


    /**
     * 这个方法是未使用stream配合lambda表达式的写法
     * 该方法逻辑是：
     *    把整数列表转换为 Point2D 列表；
     *    遍历 Point2D 列表过滤出 Y 轴 >1 的对象；
     *    计算 Point2D 点到原点的距离；
     *    累加所有计算出的距离，并计算距离的平均值。
     * @param ints
     * @return
     */
    private static double calc(List<Integer> ints) {
        //临时中间集合
        List<Point2D> point2DList = new ArrayList<>();
        for (Integer i : ints) {
            point2DList.add(new Point2D.Double((double) i % 3, (double) i / 3));
        }
        //临时变量，纯粹是为了获得最后结果需要的中间变量
        double total = 0;
        int count = 0;

        for (Point2D point2D : point2DList) {
            //过滤
            if (point2D.getY() > 1) {
                //算距离
                double distance = point2D.distance(0, 0);
                total += distance;
                count++;
            }
        }
        //注意count可能为0的可能
        return count >0 ? total / count : 0;
    }

    /**
     * optional可以处理空值
     */
    @Test(expected = IllegalArgumentException.class)
    public void test3() {
        //通过get方法获取Optional中的实际值
        assertThat(Optional.of(1).get(), is(1));
        //通过ofNullable来初始化一个null，通过orElse方法实现Optional中无数据的时候返回一个默认值
        assertThat(Optional.ofNullable(null).orElse("A"), is("A"));
        //OptionalDouble是基本类型double的Optional对象，isPresent判断有无数据
        assertFalse(OptionalDouble.empty().isPresent());
        //通过map方法可以对Optional对象进行级联转换，不会出现空指针，转换后还是一个Optional
        assertThat(Optional.of(1).map(Math::incrementExact).get(), is(2));
        //通过filter实现Optional中数据的过滤，得到一个Optional，然后级联使用orElse提供默认值
        assertThat(Optional.of(1).filter(integer -> integer % 2 == 0).orElse(null), is(nullValue()));
        //通过orElseThrow实现无数据时抛出异常
        Optional.empty().orElseThrow(IllegalArgumentException::new);
    }

    /**
     * 并行处理，多线程 parallel()
     * 8核处理器，每秒输出8个数据，没有顺序
     */
    @Test
    public void test4() {
        IntStream.rangeClosed(1,100).parallel().forEach(i->{
            System.out.println(LocalDateTime.now() + " : " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { }
        });
    }

    /**
     * 用于测试其他多线程执行的情况
     * 单个任务单线程执行需要 10 毫秒（任务代码如下），也就是每秒吞吐量是 100 个操作
     * @param atomicInteger
     */
    private void increment(AtomicInteger atomicInteger) {
        atomicInteger.incrementAndGet();
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
