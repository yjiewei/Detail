## 函数式编程 stream流
### 1. 概述
#### 1.1 学习目的
- 代码可读性
- 避免过分嵌套
- 看懂别人写的代码
- 大数据量下集合处理效率
- 底层使用多线程处理并线程安全可以保障？
```java
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
```

#### 1.2 函数式编程思想
- 面向对象思想主要是关注对象能完成什么事情，函数式编程思想就像函数式，主要是针对数据操作；
- 代码简洁容易理解，方便于并发编程，不需要过分关注线程安全问题
### 2. lambda表达式
#### 2.1 概述
- lambda是JDK8中的一个语法糖，可
以对某些匿名内部类的写法进行优化，让函数式编程只关注数据而不是对象。
- 基本格式：(参数列表)->{代码}
#### 2.2 实战
- com.yjiewei.TestLambda