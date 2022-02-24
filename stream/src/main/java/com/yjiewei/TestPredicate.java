/*
 * @author yjiewei
 * @date 2022/2/22 22:58
 */
package com.yjiewei;

import com.yjiewei.entity.Author;
import com.yjiewei.entity.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class TestPredicate {
    public static void main(String[] args) {

        testPredicateAnd();

        testPredicateOr();

        testMethodReference();

        testMethodReference2();

        testParallel();
    }

    /**
     * 测试并行流
     * 还有一个方法就是 getAuthors.parallelStream()
     */
    private static void testParallel() {
        Optional<Integer> reduce = getAuthors().stream().parallel() // 这里就用并行流来提高计算效率，只求最终结果一致
                .map(Author::getAge)
                .peek(new Consumer<Integer>() { // peek就检查当前操作
                    @Override
                    public void accept(Integer integer) {
                        System.out.println(integer + Thread.currentThread().getName());
                    }
                })
                .reduce(new BinaryOperator<Integer>() {
                    @Override
                    public Integer apply(Integer res, Integer value) {
                        return res + value;
                    }
                });
        System.out.println(reduce.get());
    }

    interface UseString{
        String use(String str, int start, int length);
    }

    public static String subAuthorName(String str, UseString useString) {
        int start = 0;
        int length = 1;
        return useString.use(str, start, length);
    }

    /**
     * 这个例子就厉害了 好好学学
     */
    private static void testMethodReference2() {
        subAuthorName("杨杰炜", String::substring); // 6.2.3 简洁
        // 复杂版的写法是
        subAuthorName("杨杰炜", new UseString() {
            @Override
            public String use(String str, int start, int length) {
                return str.substring(start, length); // 第一个对象把后面两个按照顺序传入了，所以可以直接简化
            }
        });
    }

    private static void testMethodReference() {

        getAuthors().stream()
                .map(author -> author.getAge())
                .map(new Function<Integer, String>() { // 这个方法就能用 6.2.1 的写法 .map(String::valueOf)
                    // 这个就是重写方法
                    @Override
                    public String apply(Integer integer) { // 注意这个方法参数
                        return String.valueOf(integer); // 方法体中只有一个方法，参数传进去这个静态方法了
                    }
                });

        getAuthors().stream()
                .map(author -> author.getAge())
                .forEach(new Consumer<Integer>() { // 6.2.2 简化成 System.out::println
                    @Override
                    public void accept(Integer integer) {
                        System.out.println(integer); // 某个对象的成员方法，并传入了参数
                    }
                });
    }

    /**
     * IntPredicate 就是参数为 int
     * or相当于是使用||来拼接两个判断条件
     * 打印作家中年龄大于18或者姓名长度小于2的作家
     */
    private static void testPredicateOr() {
        getAuthors().stream()
                .filter(((Predicate<Author>) author -> author.getAge() > 18).or(new Predicate<Author>() {
                            @Override
                            public boolean test(Author author) {
                                return author.getName().length() < 2;
                            }
                        })
                ).forEach(author -> System.out.println(author.getName()));
    }

    /**
     * 测试and连接多个条件 相当于&&
     * 打印作家中年龄大于18且名字长度超过1的作家名字
     */
    private static void testPredicateAnd() {
        getAuthors().stream()
                .filter(new Predicate<Author>() {
                    @Override
                    public boolean test(Author author) {
                        return author.getAge() > 18;
                    }
                }.and(new Predicate<Author>() {
                            @Override
                            public boolean test(Author author) {
                                return author.getName().length() > 1;
                            }
                        })
                ).forEach(author -> System.out.println(author.getName()));
    }

    // 初始化一些数据
    private static List<Author> getAuthors() {
        Author author1 = new Author(1L, "杨", "my introduction 1", 18, null);
        Author author2 = new Author(2L, "yjw", "my introduction 2", 19, null);
        Author author3 = new Author(3L, "yjw", "my introduction 3", 14, null);
        Author author4 = new Author(4L, "w", "my introduction 4", 29, null);
        Author author5 = new Author(5L, "wtf", "my introduction 5", 12, null);

        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();
        List<Book> books3 = new ArrayList<>();

        // 上面是作者和书
        books1.add(new Book(1L, "类别,分类啊", "书名1", 45D, "这是简介哦"));
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
}
