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
 * https://github.com/JosephZhu1983/java-common-mistakes
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
        log.info("1.使用数据结构的时候，要考虑平衡时间和空间，收益和成本都是要考虑的");
        log.info("2.不要过分迷信教科书中的大O时间复杂度，还得考虑使用场景");
        log.info("2.1 LinkedList链表的随机访问时间复杂度是O(n)，插入操作其实也是O(n)，并不是我们想象中的O(1)，因为插入需要查找前一个节点，这里不能只考虑插入操作的耗时。");
        oom();
    }


    /*
    * Arrays.asList 得到的是 Arrays 的内部类 ArrayList，List.subList 得到的是 ArrayList 的内部类 SubList，不能把这两个内部类转换为 ArrayList 使用。
    * Arrays.asList 直接使用了原始数组，可以认为是共享“存储”，而且不支持增删元素；List.subList 直接引用了原始的 List，也可以认为是共享“存储”，而且对原始 List 直接进行结构性修改会导致 SubList 出现异常。
    * 对 Arrays.asList 和 List.subList 容易忽略的是，新的 List 持有了原始数据的引用，可能会导致原始数据也无法 GC 的问题，最终导致 OOM。
    */

    /*
      问题1：调用类型是 Integer 的 ArrayList 的 remove 方法删除元素，传入一个 Integer 包装类的数字和传入一个 int 基本类型的数字，结果一样吗？
            int类型是index，也就是索引，是按照元素位置删除的；Integer是删除某个元素，内部是通过遍历数组然后对比，找到指定的元素，然后删除；两个都需要进行数组拷贝，是通过System.arraycopy进行的。
      问题2：循环遍历 List，调用 remove 方法删除元素，往往会遇到 ConcurrentModificationException 异常，原因是什么，修复方式又是什么呢？
            以foreach为例说，遍历删除实质是变化为迭代器实现，不管是迭代器里面的remove()还是next()方法,都会checkForComodification();
            而这个方法是判断modCount和expectedModCount是否相等，这个modCount是这个list集合修改的次数，每一次add或者remove都会增加这个变量，
            然后迭代器每次去next或者去remove的时候检查checkForComodification();
            发现expectedModCount(这个迭代器修改的次数)和modCount(这个集合实际修改的次数)不相等，
            就会抛出ConcurrentModificationException，迭代器里面没有add方法，用迭代器时，可以删除原来集合的元素，
            但是！一定要用迭代器的remove方法而不是集合自身的remove方法，否则抛异常。
     */
}
