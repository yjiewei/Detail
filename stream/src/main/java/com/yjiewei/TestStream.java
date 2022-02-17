/*
 * @author yjiewei
 * @date 2022/2/13 23:04
 */
package com.yjiewei;

import com.yjiewei.entity.Author;
import com.yjiewei.entity.Book;
import org.junit.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("ALL")
public class TestStream {
    public static void main(String[] args) {

        List<Author> authors = getAuthors();

        // 1.年龄小于18岁的作家名字去重
        authors.stream().filter(new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.getAge() < 18;
            }
        }).map(new Function<Author, String>() {
            @Override
            public String apply(Author author) {
                return author.getName();
            }
        }).distinct().forEach(new Consumer<String>() {
            @Override
            public void accept(String authorName) {
                System.out.println(authorName);
            }
        });

        // 2.简化写法
        authors.stream() // 返回值是stream对象，也就是集合转流
                .filter(author -> author.getAge() < 18) // 筛选条件
                .map(Author::getName) // 只要名字
                .distinct() // 去重，这里不能放在前面，不然比较的是author元素不是名字
                .forEach(System.out::println); // 遍历输出


        // 3.数组转stream，前面是list集合
        System.out.println("========");
        Integer[] arr = {1,2,3,4};
        Stream<Integer> arr1 = Stream.of(arr); // 利用Stream的静态方法
        arr1.forEach(System.out::println);
        Stream<Integer> arr2 = Arrays.stream(arr);

        // 4.map转stream 得先转换成单列集合才行
        HashMap<String, Integer> map = new HashMap<>();
        map.put("波吉", 15);
        map.put("卡克", 14);
        map.put("波斯", 13);
        map.entrySet().forEach(new Consumer<Map.Entry<String, Integer>>() {
            @Override
            public void accept(Map.Entry<String, Integer> stringIntegerEntry) {
                System.out.println(stringIntegerEntry.getKey());
                System.out.println(stringIntegerEntry.getValue());
            }
        });

        // 5.其他练习，包括debug lambda表达式
        System.out.println("======练习5======");
        test();
    }

    // 初始化一些数据
    private static List<Author> getAuthors() {
        Author author1 = new Author(1L, "杨杰炜", "my introduction 1", 18, null);
        Author author2 = new Author(2L, "yjw", "my introduction 2", 15, null);
        Author author3 = new Author(3L, "yjw", "my introduction 3", 14, null);
        Author author4 = new Author(4L, "wdt", "my introduction 4", 19, null);
        Author author5 = new Author(5L, "wtf", "my introduction 5", 12, null);

        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();
        List<Book> books3 = new ArrayList<>();

        // 上面是作者和书
        books1.add(new Book(1L, "类别", "书名1", 45D, "这是简介哦"));
        books1.add(new Book(2L, "高效", "书名2", 84D, "这是简介哦"));
        books1.add(new Book(3L, "喜剧", "书名3", 83D, "这是简介哦"));

        books2.add(new Book(5L, "天啊", "书名4", 65D, "这是简介哦"));
        books2.add(new Book(6L, "高效", "书名5", 89D, "这是简介哦"));

        books3.add(new Book(7L, "久啊", "书名6", 45D, "这是简介哦"));
        books3.add(new Book(8L, "高效", "书名7", 44D, "这是简介哦"));
        books3.add(new Book(9L, "喜剧", "书名8", 81D, "这是简介哦"));

        author1.setBookList(books1);
        author2.setBookList(books2);
        author3.setBookList(books3);
        author4.setBookList(books3);
        author5.setBookList(books2);

        return new ArrayList<>(Arrays.asList(author1, author2, author3, author4, author5));
    }

    static class Product{
        int id;
        String name;
        Double price;
        public Product(int id, String name, Double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }

    private static void test() {
        List<Product> productsList = new ArrayList<>();
        //Adding Products
        productsList.add(new Product(1, "HP Laptop", 25000d));
        productsList.add(new Product(2, "Dell Laptop", 30000d));
        productsList.add(new Product(3, "Lenovo Laptop", 28000d));
        productsList.add(new Product(4, "Sony Laptop", 28000d));
        productsList.add(new Product(5, "Apple Laptop", 90000d));
        // This is more compact approach for filtering data
        Double totalPrice = productsList.stream()
                .map(product->product.price)
                // 第一个参数是累加类型 第二是累加表达式 累加到sum
                .reduce(0.0D, new BinaryOperator<Double>() {
                    @Override
                    public Double apply(Double total, Double price) {
                        return total + price;
                    }
                });
        System.out.println(totalPrice);
        // More precise code
        Double totalPrice2 = productsList.stream()
                .map(product->product.price)
                // 简单理解就是上一个map方法之后只拿到price值，这里全加起来
                .reduce(0.0D, Double::sum);
        System.out.println(totalPrice2);

        System.out.println("======");
        // 过滤，获取数据，收集成list
        List<Double> productPriceList2 =productsList.stream()
                .filter(p -> p.price > 30000)
                .map(p->p.price)
                .collect(Collectors.toList());
        System.out.println(productPriceList2);

        // 常规操作
        long count = productsList.stream().filter(p -> p.price < 30000)
                .map(p -> p.name)
                .distinct()
                .count();
        System.out.println(count);

        Set<Double> productPriceListSet = productsList.stream().filter(p -> p.price > 30000)
                .map(p -> p.price)
                .distinct().collect(Collectors.toSet());
        System.out.println(productPriceListSet);

        productsList.stream().filter(p -> p.price < 30000)
                .map(p -> p.name)
                .distinct().forEach(System.out::println);
    }
}
