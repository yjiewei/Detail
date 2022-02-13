/*
 * @author yjiewei
 * @date 2022/2/13 15:34
 */
package com.yjiewei.entity;

import java.util.List;

public class Author {
    private Integer age;
    private List<Book> bookList;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (age != null ? !age.equals(author.age) : author.age != null) return false;
        return bookList != null ? bookList.equals(author.bookList) : author.bookList == null;
    }

    @Override
    public int hashCode() {
        int result = age != null ? age.hashCode() : 0;
        result = 31 * result + (bookList != null ? bookList.hashCode() : 0);
        return result;
    }
}
