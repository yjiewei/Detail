/*
 * @author yjiewei
 * @date 2022/2/20 20:54
 */
package com.yjiewei;

import com.yjiewei.entity.Author;
import com.yjiewei.entity.Book;

import java.util.List;
import java.util.Optional;

public class TestOptional {
    public static void main(String[] args) {
        testOfNullable();
    }

    private static void testOfNullable() {
        Optional<Author> authorOptional = getAuthorOptional();
        authorOptional.ifPresent(author -> System.out.println(author.getName()));
    }

    public static Optional<Author> getAuthorOptional(){
        Author author = new Author(1L, "杨杰炜", "my introduction 1", 18, null);
        return Optional.ofNullable(author);
    }
}
