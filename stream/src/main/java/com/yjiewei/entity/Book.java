/*
 * @author yjiewei
 * @date 2022/2/13 15:32
 */
package com.yjiewei.entity;


public class Book {
    private Long id;
    private String category;
    private String name;
    private Double score;
    private String introduction;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Book() {
    }

    public Book(Long id, String category, String name, Double score, String introduction) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.score = score;
        this.introduction = introduction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != null ? !id.equals(book.id) : book.id != null) return false;
        if (category != null ? !category.equals(book.category) : book.category != null) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        if (score != null ? !score.equals(book.score) : book.score != null) return false;
        return introduction != null ? introduction.equals(book.introduction) : book.introduction == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (introduction != null ? introduction.hashCode() : 0);
        return result;
    }
}
