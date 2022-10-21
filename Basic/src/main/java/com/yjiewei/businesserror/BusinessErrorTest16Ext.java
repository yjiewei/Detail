package com.yjiewei.businesserror;

import cn.hutool.core.date.*;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 对上一节(16 | 用好Java 8的日期时间类，少踩一些“老三样”的坑)中的时间操作用hutool工具使用
 * DateUtil 针对日期时间操作提供一系列静态方法
 * DateTime 提供类似于Joda-Time中日期时间对象的封装，继承自Date类，并提供更加丰富的对象方法。
 * FastDateFormat 提供线程安全的针对Date对象的格式化和日期字符串解析支持。此对象在实际使用中并不需要感知，相关操作已经封装在DateUtil和DateTime的相关方法中。
 * DateBetween 计算两个时间间隔的类，除了通过构造新对象使用外，相关操作也已封装在DateUtil和DateTime的相关方法中。
 * TimeInterval 一个简单的计时器类，常用于计算某段代码的执行时间，提供包括毫秒、秒、分、时、天、周等各种单位的花费时长计算，对象的静态构造已封装在DateUtil中。
 * DatePattern 提供常用的日期格式化模式，包括String类型和FastDateFormat两种类型。
 * @author yangjiewei
 * @date 2022/10/21
 */
public class BusinessErrorTest16Ext {

    /**
     * 1.月份枚举 Month
     * 2.季度枚举 Quarter
     * 3.表示某个时间单位对应的毫秒数 DateUnit
     */
    @Test
    public void test1() {
        // 31
        int lastDay = Month.of(Calendar.JANUARY).getLastDay(false);
        System.out.println("通过hutool工具获取一月份的最后一天：" + lastDay);

        Quarter spring = Quarter.of(1);
        System.out.println("只能表明季度：" + spring);

        DateUnit dateUnit = DateUnit.of(ChronoUnit.SECONDS);
        System.out.println(dateUnit.getMillis());
    }

    /**
     * 4.DateUtil 获取当前时间
     * 对于Date对象，为了便捷，使用了一个DateTime类来代替之，继承自Date对象，主要的便利在于，
     * 覆盖了toString()方法，返回yyyy-MM-dd HH:mm:ss形式的字符串，方便在输出时的调用（例如日志记录等），
     * 提供了众多便捷的方法对日期对象操作。
     */
    @Test
    public void test2(){
        DateTime date = DateUtil.date();
        DateTime date2 = DateUtil.date(Calendar.getInstance());
        DateTime date3 = DateUtil.date(System.currentTimeMillis());
        String now = DateUtil.now();
        String today= DateUtil.today();
        System.out.println(today);
    }

    @Test
    public void test3() {
        String dateStr = "2022-10-01 12:12:12";
        Date date = DateUtil.parse(dateStr);
        DateTime dateTime = DateUtil.parse(dateStr);
        System.out.println(DateUtil.format(date, DatePattern.NORM_DATETIME_FORMAT));
        System.out.println(DateUtil.formatDate(date));
        System.out.println(DateUtil.formatDateTime(dateTime));
        System.out.println(DateUtil.formatTime(date));
        System.out.println(DateUtil.formatChineseDate(date, true, true));
        System.out.println(DateUtil.formatChineseDate(date, false, true));
        System.out.println(DateUtil.year(date));
        System.out.println(DateUtil.month(date));
        System.out.println(DateUtil.second(date));
        System.out.println(DateUtil.monthEnum(date));
        System.out.println(DateUtil.quarter(date));
        System.out.println(DateUtil.quarterEnum(date));

        System.out.println(DateUtil.offsetHour(date, -3));
        System.out.println(DateUtil.yesterday());
        System.out.println(DateUtil.tomorrow());


        String dateStr1 = "2022-03-01 22:33:23";
        Date date1 = DateUtil.parse(dateStr1);
        String dateStr2 = "2022-04-01 23:33:23";
        Date date2 = DateUtil.parse(dateStr2);
        //相差一个月，31天
        long betweenDay = DateUtil.between(date1, date2, DateUnit.DAY);
        System.out.println(betweenDay);

        // "摩羯座"
        String zodiac = DateUtil.getZodiac(Month.FEBRUARY.getValue(), 28);
        // "狗"
        String chineseZodiac = DateUtil.getChineseZodiac(1998);
        System.out.println(chineseZodiac + ":" + zodiac);

        //年龄
        System.out.println(DateUtil.ageOfNow("1998-02-28"));
        //是否闰年
        System.out.println(DateUtil.isLeapYear(2022));
        System.out.println(dateTime.toString());
    }

    /**
     * 5.LocalDateTimeUtil
     * Hutool加入了针对JDK8+日期API的封装，此工具类的功能包括LocalDateTime和LocalDate的解析、格式化、转换等操作
     *
     */
    @Test
    public void test4() {
        DateTime dt = DateUtil.parse(new Date().toString());

        // Date对象转换为LocalDateTime
        LocalDateTime of = LocalDateTimeUtil.of(dt);
        LocalDate localDate = LocalDateTimeUtil.parseDate(dt.toDateStr());

        System.out.println(of);

        // 时间戳转换为LocalDateTime
        of = LocalDateTimeUtil.ofUTC(dt.getTime());


        System.out.println(of);

        String format = LocalDateTimeUtil.format(of, DateTimeFormatter.ISO_LOCAL_TIME);
        String format1 = LocalDateTimeUtil.format(of, DatePattern.NORM_DATETIME_PATTERN);
        String format2 = LocalDateTimeUtil.format(localDate, DatePattern.NORM_DATETIME_PATTERN);
        System.out.println(format);
        System.out.println(format1);
        System.out.println(format2);

        System.out.println(dt.toString() + "=" + dt.toDateStr() + "=" + dt.toLocalDateTime());
    }
}
