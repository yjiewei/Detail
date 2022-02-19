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

### 3. stream流
#### 3.1 概述
- stream使用的是函数式编程模式，可以被用来对集合或数组进行链状流式的操作
- 有别于其他输入输出流，这里是针对集合操作数据的流哦
- 创建流实战：com.yjiewei.TestStream

#### 3.2 功能
- 流不存储元素。它只是通过计算操作的流水线从数据结构, 数组或I/O通道等源中传递元素。
- 流本质上是功能性的。对流执行的操作不会修改其源。例如, 对从集合中获取的流进行过滤会产生一个新的不带过滤元素的流, 而不是从源集合中删除元素。
- Stream是惰性的, 仅在需要时才评估代码。
- 在流的生存期内, 流的元素只能访问一次。像Iterator一样, 必须生成新的流以重新访问源中的相同元素。

#### 3.3 常用方法说明
- reduce:使用关联的累加函数对此流的元素进行归约, 并返回一个Optional描述归约值（如果有）
- map:相当于对数据进行一个操作，可以自定义返回值等
- distinct:可以去除流中的相同元素，注意（*该方法依赖的Object的equals方法来判断是否是相同对象，所以要重写equals方法，否则只有对象地址一样时才会被认为是重复*）
- sorted:可以对流中的元素进行排序
- limit:设置流的最大长度，超出部分将被抛弃
- skip:跳过流中的前n个元素，返回剩下的元素
- **flatMap**:map能把一个对象转换成另外一个对象来作为流中的元素，而flatMap可以把一个对象转换成多个对象作为流中的元素
- 中间操作（filter,map,distinct,sorted,limit,skip,flatMap）
- 终结操作（forEach,collect,）

#### 3.4 参考资料
- http://www.srcmini.com/20592.html
