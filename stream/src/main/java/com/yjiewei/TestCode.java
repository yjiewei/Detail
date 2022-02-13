/*
 * @author yjiewei
 * @date 2022/2/13 15:29
 */
package com.yjiewei;

import com.yjiewei.entity.Author;
import com.yjiewei.entity.Book;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class TestCode {

    /**
     * 查询未成年作家评分在70分以上的书籍，由于流的影响所以作家和书籍可能会重复出现，所以要去重
     * 我不是很理解这里为什么说会重复出现呢？
     */
    @Test
    public void test1() {
        List<Book> bookList = new ArrayList<>();
        List<Author> authorList = new ArrayList<>();
        Set<Book> uniqueBookValues = new HashSet<>();
        Set<Author> uniqueAuthorValues = new HashSet<>();
        for (Author author : authorList) {
            // 这里如果重复就不会添加成功
            if (uniqueAuthorValues.add(author)) {
                if (author.getAge() < 18) {
                    List<Book> books = author.getBookList();
                    for (Book book : books) {
                        if (book.getScore() > 70D) {
                            // 如果之前有这本书就不会再次添加
                            if (uniqueBookValues.add(book)) {
                                bookList.add(book);
                            }
                        }
                    }
                }
            }
        }
        System.out.println(bookList);
        // authorList.add(new Author());
        // 函数式写法
        List<Book> collect = authorList.stream()
                .distinct()
                .filter(author -> author.getAge() < 18)
                .map(author -> author.getBookList())
                .flatMap(Collection::stream)
                .filter(book -> book.getScore() > 70)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test2() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我好想你");
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        // 函数式接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我好想你");
            }
        }).start();

        new Thread(() ->{
            System.out.println("我好想你*2");
        }).start();
    }
}
