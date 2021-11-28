## 如何创建一个spring项目？
1. 新建一个空maven工程
2. 引入依赖
3. 创建配置文件 src/application.xml
4. 在配置文件中引入标签beans
   - 在src目录下新建xml configuration file -> spring config会自动生成
   
5. 配置文件需要放在resources目录下，可能是因为我这是maven工程的原因


## IOC
- 反转控制，把对象的创建和对象之间的调用过程交给spring管理
- 降低耦合
- 原理：xml解析、工厂模式、反射

---
IOC过程
```java
// 1.工厂模式
class UserFactory{
    public static UserDao getDao(){
        return new UserDao();
    }
}
```
```xml
<!-- 2.xml配置文件，配置创建的对象 -->
<!--注入到容器中，并设置示例对象值-->
<bean id="person" class="com.yjiewei.Person">
   <constructor-arg index="0" value="杨杰炜"/>
   <constructor-arg index="1" value="23"/>
</bean>
```
```java
class UserFactory{
   public static UserDao getDao(){
      String classValue = "class属性值"; // 通过xml解析
      Class clazz = Class.forName(classValue); // 通过反射创建对象
      return (UserDao)clazz.newInstance();
   }
}
```
spring 提供IOC容器实现的两种方式（两个接口）
- BeanFactory (spring内部使用，不推荐开发使用)
  - 在使用时才会去创建对象，加载配置文件时不会去创建对象
- ApplicationContext
  - 加载配置文件的时候就会把在配置文件对象进行创建
   

ApplicationContext实现类
- ClassPathXmlApplicationContext
- FileSystemXmlApplicationContext

注入属性值的三种方式
```xml
    <!--注入到容器中，并设置示例对象值-->
    <bean id="person" class="com.yjiewei.Person">
        <!-- 1.这里如果没有空参构造器就会让你设置值；所以这里相当于通过xml构造器的方式设置属性-->
        <!--  <constructor-arg index="0" value="杨杰炜"/>-->
        <!--  <constructor-arg index="1" value="23"/>-->

        <!-- 2.通过property完成属性注入-->
        <property name="name" value="yjiewei"/>
        <property name="age" value="23"/>

        <!-- 3.p名称空间注入 其实就是简化property，引入名称空间，使用p标签-->
    </bean>
```

xml注入其他数据类型（比如null，特殊字符）
```xml
    <bean id="person" class="com.yjiewei.Person">
        <!--属性中设置null值-->
        <property name="name">
            <null/>
        </property>
        <!--属性中包含特殊字符 1.把<>进行转义 &lt; &gt; 2.把带特殊符号内容写到CDATA-->
        <property name="name">
            <value><![CDATA[<<杨杰炜>>]]></value>
        </property>
        <property name="age" value="23"/>
    </bean>
```
11集