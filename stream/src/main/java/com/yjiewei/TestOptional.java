/*
 * @author yjiewei
 * @date 2022/2/20 20:54
 */
package com.yjiewei;

import com.yjiewei.entity.Author;
import com.yjiewei.entity.Book;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TestOptional {
    public static void main(String[] args) {

        testOfNullable();

        testFilter();

        testMap();
    }

    private static void testMap() {
        Optional<Author> authorOptional = getAuthorOptional();
        Optional<List<Book>> books = authorOptional.map(author -> author.getBookList());
        books.ifPresent(new Consumer<List<Book>>() {
            @Override
            public void accept(List<Book> books) {
                // 这里没有数据但是也不会出现异常
                books.forEach(book -> System.out.println(book.getName()));
            }
        });
    }

    private static void testFilter() {

        Optional<Author> authorOptional = getAuthorOptional();
        authorOptional.filter(new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.getAge() > 18;
            }
        }).ifPresent(author -> System.out.println(author.getName()));
    }

    /**
     * Optional.ofNullable()
     * Optional.ifPresent()
     * Optional.orElseGet()
     * Optional.get()
     * Optional.orElseThrow()
     */
    private static void testOfNullable() {
        Optional<Author> authorOptional = getAuthorOptional();
        authorOptional.ifPresent(author -> System.out.println(author.getName()));
        // authorOptional.get();// 这种方式不推荐使用 防止出现异常
        // Optional.orElseGet(); // 如果get获取到的值为空，就会获取默认值，这里的是 new Author()
        Author author = authorOptional.orElseGet(Author::new);
        System.out.println(author.toString());
        System.out.println(author.getName()); // 虽然这里的author是null，但是也不会出现空指针的情况

        authorOptional.orElseThrow(() -> new RuntimeException("当前获取到的数据是NULL"));
    }

    public static Optional<Author> getAuthorOptional(){
        Author author = new Author(1L, "杨杰炜", "my introduction 1", 20, null);
        return Optional.ofNullable(author);
    }
}
