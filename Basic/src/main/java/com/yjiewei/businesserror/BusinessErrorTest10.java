package com.yjiewei.businesserror;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * List、Set、Map、Queue 可以从广义上统称为集合类数据结构，可以分为 Map 和 Collection 两大类。
 * 把数组转换为 List 集合、对 List 进行切片操作、List 搜索的性能问题等几个方面着手，来聊聊其中最可能遇到的一些坑
 * @author yangjiewei
 * @date 2022/8/31
 */
@Slf4j
public class BusinessErrorTest10 {

    /**
     * Arrays.asList可以把原始的数组转换成list，就可以开展Java8中各种stream操作了。
     * Arrays.asList 把数据转换为 List 的三个坑
     * 1.不能直接使用 Arrays.asList 来转换基本类型数组
     */
    @Test
    public void test1() {
        int[] arr = {1, 2, 3};

        log.info("1.直接用Arrays.asList转换数组为list");
        List list = Arrays.asList(arr);
        // 坑1：这里记录的是 int数组，只有一个元素，元素类型是整数数组
        // 原因：只能是把 int 装箱为 Integer，不可能把 int 数组装箱为 Integer 数组，所以这里转数组的时候，把int[]作为类型了
        log.info("list:{} size:{} class:{}", list, list.size(), list.get(0).getClass());

        // 解决方法：1.用Arrays.stream()方法转换
        log.info("2.用Arrays.stream方法将数组转化为list");
        IntStream intStream = Arrays.stream(arr);
        intStream.forEach((a)-> log.info(String.valueOf(a)));

        // 解决方法：2.不要用int数组，声明Integer数组
        log.info("3.定义Integer数组，而不用int数组");
        Integer[] arr1 = {1, 2, 3};
        List<Integer> integerList = Arrays.asList(arr1);
        log.info("integerList:{} size:{} class:{}", integerList, integerList.size(), integerList.get(0).getClass());
    }


    /**
     * Arrays.asList 把数据转换为 List 的三个坑
     * 2.Arrays.asList 返回的 List 不支持增删操作：返回的类型是Arrays类的内部类，未实现add操作
     * 3.对原始数组的修改会影响到我们获得的那个 List：因为asList方法直接使用了原数组，直接共享
     */
    @Test
    public void test2() {
        String[] arr = {"1", "2", "3"};
        List list = Arrays.asList(arr);
        // 修复坑3：新创建数组
        // List list = new ArrayList(Arrays.asList(arr));
        arr[1] = "4";
        try {
            // UnsupportedOperationException，返回的并不是ArrayList，并没有add方法
            list.add("5");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        log.info("arr:{} list:{}", Arrays.toString(arr), list);
    }


    private static List<List<Integer>> data = new ArrayList<>();

    /**
     * 未重现 oom情况
     * 出现 OOM 的原因是，循环中的 1000 个具有 10 万个元素的 List 始终得不到回收，因为它始终被 subList 方法返回的 List 强引用
     *
     */
    private static void oom() {
        for (int i = 0; i < 1000; i++) {
            List<Integer> rawList = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
            data.add(rawList.subList(0, 1));
        }
    }

    public static void main(String[] args) {
        oom();
    }
}
