package com.yjiewei.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * 其他涉及到的知识点
 * @author yjiewei
 * @date 2021/11/27
 */
public class BasicKnowledge {
    public static void main(String[] args) throws ParseException {
        // 1.时间字符串转换成对应的date类对象
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateTime = simpleDateFormat.format(date);
        // 格式化为字符串
        System.out.println(dateTime);
        // 解析为日期
        Date parse1 = simpleDateFormat.parse(dateTime);
        System.out.println(parse1);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TemporalAccessor parse = dateTimeFormatter.parse("2021-11-27");
        System.out.println(parse);

        Instant instant = new Date().toInstant(); // 获取时间点
        System.out.println(instant.atZone(ZoneId.of("Asia/Shanghai"))); // 调整时区

        // 2.Java比较器 通常是用来比较对象进行排序（本质上还是用基本数据类型去比较）
        //   - 重写bean中的compareTo()方法
        //   - 定制排序，使用Comparator接口并重写compare方法，实际上内部重写时使用String.compareTo() 或者数字大小

    }
}
