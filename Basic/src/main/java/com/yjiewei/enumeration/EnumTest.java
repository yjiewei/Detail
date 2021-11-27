package com.yjiewei.enumeration;

import java.util.Arrays;

/**
 * 枚举 enum
 *   1.类的对象只有有限个，确定的；
 *   2.定义一组常量时
 *   3.如果只有一个对象，可以作为单例模式的实现方式
 *   4.实现接口，和类使用一样
 *   常用方法：
 *      1.values()
 *      2.valueOf(String enumObjName)
 *      3.toString()
 */
public class EnumTest {

    public static void main(String[] args) {
        Season autumn = Season.AUTUMN;
        System.out.println(autumn.getSeasonName());
        System.out.println(autumn.getSeasonDesc());
        System.out.println(autumn.toString());

        SeasonEnum spring = SeasonEnum.SPRING;
        System.out.println(spring.getSeasonDesc());
        System.out.println(spring.getSeasonName());
        System.out.println(spring.toString()); // 默认继承 java.lang.Enum
        System.out.println(Arrays.toString(SeasonEnum.values()));
    }
}

/**
 * 1.使用类来定义枚举类
 */
class Season{
    // 1.声明Season对象的属性
    private final String seasonName;
    private final String seasonDesc;

    // 2.私有化类构造器，并给对象属性赋值
    private Season(String seasonName, String seasonDesc) {
        this.seasonDesc = seasonDesc;
        this.seasonName = seasonName;
    }

    // 3.提供当前枚举类的多个对象 public static final
    public static final Season SPRING = new Season("春天", "春暖花开");
    public static final Season SUMMER = new Season("夏天", "夏日炎炎");
    public static final Season AUTUMN = new Season("秋天", "秋高气爽");
    public static final Season WINTER = new Season("冬天", "冬日烈烈");

    // 4.其他：获取枚举类对象属性
    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonDesc() {
        return seasonDesc;
    }

    @Override
    public String toString() {
        return "Season{" +
                "seasonName='" + seasonName + '\'' +
                ", seasonDesc='" + seasonDesc + '\'' +
                '}';
    }
}

/**
 * 2.使用enum来定义枚举类
 *   默认实现java.lang.Enum，
 *   toString()方法返回当前枚举对象名字
 *   values() 返回所有枚举对象
 *   valueOf(String enumObjName) 返回对应的枚举对象
 */
enum SeasonEnum{
    SPRING("春天", "春暖花开"),
    SUMMER("夏天", "夏日炎炎")
    ;

    // 1.声明Season对象的属性
    private final String seasonName;
    private final String seasonDesc;

    SeasonEnum(String seasonName, String seasonDesc) {
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonDesc() {
        return seasonDesc;
    }
}




