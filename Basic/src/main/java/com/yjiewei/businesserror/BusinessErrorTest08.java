package com.yjiewei.businesserror;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author yjiewei
 * @date 2022/8/1
 * https://time.geekbang.org/column/article/213604
 * https://github.com/JosephZhu1983/java-common-mistakes
 * https://projectlombok.org/features/Data
 */
public class BusinessErrorTest08 {

    private static final Log log = LogFactory.getLog(BusinessErrorTest08.class);
    public static final int SIZE = 10000000;

    /**
     * 测试compareTo方法需要equals方法和hashcode方法的逻辑一致性，@Data会默认实现@EqualsAndHashCode
     * https://projectlombok.org/features/Data
     * 如果类型之间有继承，equals方法和hashcode是怎么处理的？
     */
    @Data
    @AllArgsConstructor
    class Student implements Comparable<Student>{
        private int id;
        //EqualsAndHashCode.Exclude // 排除比较
        private String name;

        @Override
        public int compareTo(Student other) {
//            int result = Integer.compare(other.id, id);
//            if (result == 0)
            // 这里不应该相等，但是这里输出相等的结果，这里是因为重写的compareTo和@Data中的重写逻辑不一致
//                log.info("compareTo比较两个对象分别是：" + this + other);
//            return result;

            // 修正后的方式 也就是比较名字、id，和equals保持一致
            return Comparator.comparing(Student::getName)
                    .thenComparingInt(Student::getId)
                    .compare(this, other);
        }
    }

    @Test
    public void test1() {
        List list = new ArrayList<>();
        list.add(new Student(1, "zhang"));
        list.add(new Student(2, "yang"));
        Student student = new Student(2, "li");
        log.info("ArrayList.indexOf");
        int index1 = list.indexOf(student);

        Collections.sort(list);
        log.info(list);
        log.info("Collections.binarySearch");
        int index2 = Collections.binarySearch(list, student);
        log.info("index1 = " + index1); // -1
        log.info("index2 = " + index2); // 0
        // 这里的比较结果是不对的，为什么呢？binarySearch方法调用了compareTo方法，但是和equals比较逻辑不一致，这里没有重写
    }

    @Test
    public void test2() {
        List<String> list;
        //-XX:+PrintStringTableStatistics
        //-XX:StringTableSize=10000000
        long begin = System.currentTimeMillis();
        list = IntStream.rangeClosed(1, SIZE)
                .mapToObj(i-> String.valueOf(i).intern()) // 使用 String 提供的 intern 方法也会走常量池机制，但是会出现性能问题
                .collect(Collectors.toList());
        log.info("size:"+ SIZE + " and time:" + (System.currentTimeMillis() - begin));
        System.out.println(list.size());

        System.out.println("==比较的是值，而对于引用类型来说，会去比较地址。主要用于比较基本数据类型");
        System.out.println("equals比较的是内容，主要用于比较引用类型的值而非地址");
//      Integer类重写了equals方法，人家比较的是值。
//        public boolean equals(Object obj) {
//            if (obj instanceof Integer) {
//                return value == ((Integer)obj).intValue();
//            }
//            return false;
//        }
    }


    @Test
    public void test3() {
        Point p1 = new Point(1, 2, "a");
        Point p2 = new Point(1, 2, "b");
        Point p3 = new Point(1, 2, "a");
        log.info("p1.equals(p2) ? " + p1.equals(p2));
        log.info("p1.equals(p3) ? " + p1.equals(p3));

        log.info("\r\n");


        PointWrong pw1 = new PointWrong(1, 2, "a");
        try {
            log.info("pw1.equals(null) ? " + pw1.equals(null));
        } catch (Exception ex) {
            log.error(ex);
        }

        Object o = new Object();
        try {
            log.info("pw1.equals(expression) ? " + pw1.equals(o));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        PointWrong pw2 = new PointWrong(1, 2, "b");
        log.info("pw1.equals(pw2) ? " + pw1.equals(pw2));

        System.out.println();

        PointWrong p1a = new PointWrong(1, 2, "a");
        PointWrong p2b = new PointWrong(1, 2, "b");

        HashSet<PointWrong> points = new HashSet<>();
        points.add(p1a);
        // 我觉得这里很明显就是不包含啊，对象都不一样。。。但是equals方法重写过，只用了两个值判断是否相等，这里还是不包含。
        // 我们没有重写hashCode方法 出现这个 Bug 的原因是，散列表需要使用 hashCode 来定位元素放到哪个桶。
        // 如果自定义对象没有实现自定义的 hashCode 方法，就会使用 Object 超类的默认实现，得到的两个 hashCode 是不同的，导致无法满足需求。
        // Object会用key去算hashCode，这里明显拿的就是地址去算，重写方法里面用的x,y
        log.info("points.contains(p1a) ? " + points.contains(p1a));
        log.info("points.contains(p2b) ? " + points.contains(p2b));
    }


    @Data
    class PointWrong {
        private int x;
        private int y;
        private final String desc;

        public PointWrong(int x, int y, String desc) {
            this.x = x;
            this.y = y;
            this.desc = desc;
        }

//    @Override
//    public boolean equals(Object o) {
//        PointWrong that = (PointWrong) o;
//        return x == that.x && y == that.y; // o可能会引起空指针 这是错误的写法
//    }

        /**
         * 1.判断是否是同一个对象 2.判断类是否相同 3.比较值
         * 另外，实现了equals方法，hashcode方法也要配对实现
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PointWrong that = (PointWrong) o;
            return x == that.x && y == that.y;
        }

        @Override public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    @Data
    class Point{
        private int x;
        private int y;
        private final String desc;

        public Point(int x, int y, String desc) {
            this.x = x;
            this.y = y;
            this.desc = desc;
        }
    }
}


