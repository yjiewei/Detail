## 函数式编程 stream流
### 1.概述
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


### 2.lambda表达式
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

#### 3.3 常用方法说明 com.yjiewei.TestStream
- map:相当于对数据进行一个操作，可以自定义返回值等
- distinct:可以去除流中的相同元素，注意（*该方法依赖的Object的equals方法来判断是否是相同对象，所以要重写equals方法，否则只有对象地址一样时才会被认为是重复*）
- sorted:可以对流中的元素进行排序，传入空参时使用的是实体类的比较方法
- limit:设置流的最大长度，超出部分将被抛弃
- skip:跳过流中的前n个元素，返回剩下的元素
- **flatMap**:map能把一个对象转换成另外一个对象来作为流中的元素，而flatMap可以把一个对象转换成多个对象作为流中的元素
- 中间操作（filter,map,distinct,sorted,limit,skip,flatMap）
- 终结操作（forEach,collect,count,max,min,reduce归并,查找与匹配）
- forEach:遍历所有元素
- count:计算元素数量
- min&max:返回的是option对象，这里和sorted一样，得指定比较规则
- collect:把当前流转换成一个集合（list, set, map）
    - Collectors.toList()
    - Collectors.toSet()
    - Collectors.toMap(key, value)
- anyMatch:可以用来判断是否有任意符合匹配条件的元素，结果为boolean类型
- allMatch:可以用来判断是否都匹配条件，结果也是boolean类型，都符合则为true
- noneMatch:是否都不符合，都不符合则为true
- findAny:获取流中的任意一个元素，该方法无法保证获取的是流中的第一个元素，只是匹配到
- findFirst:获取流中的第一个元素
- reduce:对流中的数据按照你制定的计算方式计算出一个结果，并返回一个Optional描述归约值（如果有）
    ```java
    T result = identity;
    for(T element : this stream) {
        result = accumulator.apply(result, element); // 执行具体数据操作
    }
    return result;
    // 还有一种三个方法的重载方法，后面还需要补充
    ```

#### 3.4 参考资料
- http://www.srcmini.com/20592.html

#### 3.5 注意事项
- 惰性求值，如果没有终结操作是不会执行的
- 流是一次性的，经过终结操作之后就不能再被使用
- 不会影响元数据


### 4.Optional
#### 4.1 概述
很多情况下代码容易出现空指针异常，尤其对象的属性是另外一个对象的时候，
判断十分麻烦，代码也会很臃肿，这种情况下Java 8 引入了optional来避免空指针异常，
并且很多函数式编程也会用到API也都用到
#### 4.2 使用
1. 创建对象
- optional就像是包装类，可以把我们的具体数据封装Optional对象内部，
  然后我们去使用它内部封装好的方法操作封装进去的数据就可以很好的避免空指针异常
- 一般我们使用Optional.ofNullable来把数据封装成一个optional对象，无论传入的参数是否为null都不会出现问题
`Author author = getAuthor();  Optional<Author> author = Optional.ofNullable(author);`
- 如果你确定一个对象不是空的话就可以用Optional.of这个静态方法来把数据封装成Optional对象
`Optional.of(author);`这里一定不能是null值传入，可以试试会出现空指针
- 如果返回的是null，这时可以使用Optional.empty()来进行封装

2. 安全消费值
- 当我们获取到一个Optional对象的时候，可以用ifPresent方法来去消费其中的值，
这个方法会先去判断是否为空，不为空才会去执行消费代码，优雅避免空指针
`OptionalObject.ifPresent()`

3. 获取值
- Optional.get() 这种方法不推荐，当Optional的get方法为空时会出现异常

3.1 安全获取值
- orElseGet:获取数据并且设置数据为空时的默认值，如果数据不为空就获取该数据，为空则获取默认值
- orElseThrow

4. 过滤
- 我们可以使用filter方法对数据进行过滤，如果原来是有数据的，但是不符合判断，也会变成一个无数据的Optional对象
- Optional.filter()

5. 判断
- Optional.isPresent() 判断数据是否存在，空则返回false，否则true，这种方式不是最好的，推荐使用Optional.ifPresent()
- Optional.ifPresent()，上面isPresent不能体现Optional的优点
- 使用的时候可以先判断，相当于先判空，再去get，这样就不会空指针了

6. 数据转换
- Optional还提供map可以对数据进行转换，并且转换得到的数据还是Optional包装好的，保证安全使用

### 5.函数式接口
