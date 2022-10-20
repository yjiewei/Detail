package com.yjiewei.businesserror;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;

/**
 * 16 | 用好Java 8的日期时间类，少踩一些“老三样”的坑
 * JDK8用于计算时间和日期格式化都很强大，不能只盯着SimpleDateFormat，也得看看 java.time包下的东西
 * 看看当前目录下的 JAVA日期时间类型.webp 描述了日期时间类
 * @author yangjiewei
 * @date 2022/10/20
 */
@Slf4j
public class BusinessErrorTest16 {

    // 线程不安全
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // 只有一个线程
    private static ThreadLocal<SimpleDateFormat> threadSafeSimpleDateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


    // jdk8 用于规避SimpleDateFormat的几个坑的新类，DateTimeFormatter，定义为static是因为它是线程安全的
    private static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR) //年
            .appendLiteral("/")
            .appendValue(MONTH_OF_YEAR) //月
            .appendLiteral("/")
            .appendValue(DAY_OF_MONTH) //日
            .appendLiteral(" ")
            .appendValue(ChronoField.HOUR_OF_DAY) //时
            .appendLiteral(":")
            .appendValue(ChronoField.MINUTE_OF_HOUR) //分
            .appendLiteral(":")
            .appendValue(ChronoField.SECOND_OF_MINUTE) //秒
            .appendLiteral(".")
            .appendValue(ChronoField.MILLI_OF_SECOND) //毫秒
            .toFormatter();

    @Test
    public void test1() {
        Date date = new Date(2022, Calendar.DECEMBER, 31, 11, 12, 13);
        // Sun Dec 31 11:12:13 CST 3922
        log.info("输出的时间是：{}", date);
        log.info("为什么输出的时间不对呢，是因为这里的起始时间应该是1900+year，当然这个方法已经弃用了");
        log.info("如果有国际化需求时，需要使用calendar来初始化时间");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.DECEMBER, 31, 11, 12, 13);
        log.info("国际化时间：{}，当前时区", calendar.getTime());
        Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        calendar2.set(2022, Calendar.DECEMBER, 31, 11, 12, 13);
        log.info("国际化时间：{}，纽约时区", calendar2.getTime());
        log.info("当前时区和纽约时区相差时间：{}小时", (calendar2.getTimeInMillis() - calendar.getTimeInMillis()) / 1000 / 60 / 60);

        log.info("使用Date并没有国际时间的概念，世界上所有计算机使用new Date()出来的时间都一样，UTC时间，当前new Date():{}", new Date());
        log.info("Date(0):{}", new Date(0));
        log.info("当前时区:{}，相比于UTC时差:{}小时", TimeZone.getDefault().getID(), TimeZone.getDefault().getRawOffset() / 3600000);
        log.info("纽约时区:{}，相比于UTC时差:{}小时", TimeZone.getTimeZone("America/New_York").getID(), TimeZone.getTimeZone("America/New_York").getRawOffset() / 3600000);
    }

    @Test
    public void test2() throws Exception {
        System.out.println("不同时区解析转换同一个Date，可能解析出来的还是不一样");
        String stringDate = "2022-01-02 22:00:00";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //默认时区解析时间表示
        Date date1 = inputFormat.parse(stringDate);
        System.out.println(date1 + ",时间戳:" + date1.getTime());
        //纽约时区解析时间表示 比我们当前时间快13个小时
        inputFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date date2 = inputFormat.parse(stringDate);
        System.out.println(date2 + ",时间戳:" + date2.getTime());
    }

    /**
     * [2022-01-02 22:00:00 +0800]
     * [2022-01-02 09:00:00 -0500]
     */
    @Test
    public void test3() throws Exception {
        System.out.println("不同时区转换同一个时间，转换出来的Date会得到不同的时间戳");
        String stringDate = "2022-01-02 22:00:00";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //同一Date
        Date date = inputFormat.parse(stringDate);
        //默认时区格式化输出：
        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss Z]").format(date));
        //纽约时区格式化输出
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss Z]").format(date));
    }

    /**
     * ZoneId.of 来初始化一个标准的时区，也可以使用 ZoneOffset.ofHours 通过一个 offset，来初始化一个具有指定时间差的自定义时区
     * LocalDateTime 不带有时区属性，所以命名为本地时区的日期时间
     * 而 ZonedDateTime=LocalDateTime+ZoneId，具有时区属性。
     * 因此，LocalDateTime 只能认为是一个时间表示，ZonedDateTime 才是一个有效的时间
     */
    @Test
    public void test4() {
        //一个时间表示
        String stringDate = "2020-01-02 22:00:00";

        //初始化三个时区
        ZoneId timeZoneSH = ZoneId.of("Asia/Shanghai");
        ZoneId timeZoneNY = ZoneId.of("America/New_York");
        // 自定义的具有9小时时差的时区
        ZoneId timeZoneJST = ZoneOffset.ofHours(9);

        //格式化器
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime date = ZonedDateTime.of(LocalDateTime.parse(stringDate, dateTimeFormatter), timeZoneJST);

        //使用DateTimeFormatter格式化时间，可以通过withZone方法直接设置格式化使用的时区
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        System.out.println(timeZoneSH.getId() + outputFormat.withZone(timeZoneSH).format(date));
        System.out.println(timeZoneNY.getId() + outputFormat.withZone(timeZoneNY).format(date));
        System.out.println(timeZoneJST.getId() + outputFormat.withZone(timeZoneJST).format(date));
        //Asia/Shanghai2020-01-02 21:00:00 +0800
        //America/New_York2020-01-02 08:00:00 -0500
        //+09:002020-01-02 22:00:00 +0900

    }


    /**
     * 使用遗留的SimpleDateFormat继续日期的格式化和解析，会遇到什么问题
     * 这几个坑可以用DateTimeFormatter解决，不用记忆大小写，它是线程安全的，解析也是严格的
     * 1.格式化式子出错
     * - 小写 y 是年，而大写 Y 是 week year，也就是所在的周属于哪一年。
     * 使用2019-12-29
     * defaultLocale:zh_CN
     * 格式化: 2020-12-29
     * weekYear:2020
     * firstDayOfWeek:1
     * minimalDaysInFirstWeek:1
     * <p>
     * 2.定义static的SimpleDateFormat可能会出现线程安全问题
     * <p>
     * 3.当需要解析的字符串和格式不匹配的时候，SimpleDateFormat 表现得很宽容，不会报错但不对
     */
    @Test
    public void test5() {
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
        System.out.println("defaultLocale:" + Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 29, 0, 0, 0);
        SimpleDateFormat YYYY = new SimpleDateFormat("YYYY-MM-dd");
        System.out.println("格式化: " + YYYY.format(calendar.getTime()));
        System.out.println("weekYear:" + calendar.getWeekYear());
        System.out.println("firstDayOfWeek:" + calendar.getFirstDayOfWeek());
        System.out.println("minimalDaysInFirstWeek:" + calendar.getMinimalDaysInFirstWeek());
    }


    /**
     * 2.定义static的SimpleDateFormat可能会出现线程安全问题
     * 它的解析和格式化操作是非线程安全的
     * java.text.CalendarBuilder#establish(java.util.Calendar)
     * 方法内部先清空 Calendar 再构建 Calendar，整个操作没有加锁 cal.clear();
     * <p>
     * java.text.SimpleDateFormat#format(java.util.Date, java.lang.StringBuffer, java.text.Format.FieldDelegate)
     * calendar.setTime(date)这条语句改变了calendar，稍后，calendar还会用到（在subFormat方法里）
     * <p>
     * https://blog.csdn.net/qq_43842093/article/details/124563744
     * <p>
     * 解析和格式化只能在同一个线程里复用SimpleDateFormat，可以通过threadLocal方式来存放simpleDateFormat
     */
    @Test
    public void test6() throws Exception {

        // 初始化100个线程的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 20; i++) {
            //提交20个并发解析时间的任务到线程池，模拟并发环境
            threadPool.execute(() -> {
                // 每个任务里面解析十次时间
                for (int j = 0; j < 10; j++) {
                    try {
                        System.out.println(threadSafeSimpleDateFormat.get().parse("2020-01-01 11:12:13"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
        //该方法调用会被阻塞，并且在以下几种情况任意一种发生时都会导致该方法的执行:
        // 即shutdown方法被调用之后，或者参数中定义的timeout时间到达或者当前线程被打断，
        // 这几种情况任意一个发生了都会导致该方法在所有任务完成之后才执行。
        // 第一个参数是long类型的超时时间，第二个参数可以为该时间指定单位。
        threadPool.awaitTermination(1, TimeUnit.MINUTES);
    }

    /**
     * 3.当需要解析的字符串和格式不匹配的时候，SimpleDateFormat 表现得很宽容
     * result:Mon Jan 01 00:00:00 CST 2091
     * 0901作为月份，直接算成75年，+2016=2091
     */
    @Test
    public void test7() throws ParseException {
        String dateString = "20160901";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        System.out.println("result:" + dateFormat.parse(dateString));
    }

    /**
     * 相比于SimpleDateFormat，DateTimeFormatter是jdk8引入的线程安全的时间操作类
     */
    @Test
    public void test8() {
        //使用刚才定义的DateTimeFormatterBuilder构建的DateTimeFormatter来解析这个时间
        LocalDateTime localDateTime = LocalDateTime.parse("2020/1/2 12:34:56.789", dateTimeFormatter);
        //解析成功
        System.out.println(localDateTime.format(dateTimeFormatter));
        //使用yyyyMM格式解析20160901是否可以成功呢？
        String dt = "20160901";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM");
        System.out.println("result:" + dateTimeFormatter.parse(dt));
    }

    /**
     * 日期时间的计算
     * 1.用当前时间的时间戳+30天的时间戳毫秒数，你觉得会是下个月的时间戳吗？
     * 实际上是不对的，甚至得到的时候是提前的，int溢出问题
     */
    @Test
    public void test9() {
        Date today = new Date();
        System.out.println(today.getTime());
        System.out.println("Numeric overflow in expression: 30 * 1000 * 60 * 60 * 24 = " + 30 * 1000 * 60 * 60 * 24);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(30L * 1000 * 60 * 60 * 24);
        // Date nextMonth = new Date(today.getTime() + 30 * 1000 * 60 * 60 * 24);
        Date nextMonth = new Date(today.getTime() + 30L * 1000 * 60 * 60 * 24);
        // Thu Oct 20 16:00:32 CST 2022
        // Fri Sep 30 22:57:45 CST 2022
        System.out.println(today);
        System.out.println(nextMonth);
        System.out.println("为什么不是一个月之后呢，是因为int溢出了。");

        System.out.println("不推荐使用时间戳的方式计算时间，jdk8以前的操作可以用calendar");

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 30);
        System.out.println(c.getTime());

        System.out.println("如果是jdk8，可以使用时间类型LocalDateTime进行计算，更加方便简洁");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")));


        System.out.println("//测试操作日期");
        System.out.println(LocalDate.now()
                .minus(Period.ofDays(1))
                .plus(1, ChronoUnit.DAYS)
                .minusMonths(1)
                .plus(Period.ofMonths(1)));


        System.out.println("//本月的第一天");
        System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));

        System.out.println("//今年的程序员日");
        System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).plusDays(255));

        System.out.println("//今天之前的一个周六");
        System.out.println(LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SATURDAY)));

        System.out.println("//本月最后一个工作日");
        System.out.println(LocalDate.now().with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY)));

        System.out.println("可以使用lambda表达式进行自定义的时间调整 100天以内的随机加");
        System.out.println(LocalDate.now().with(temporal -> temporal.plus(ThreadLocalRandom.current().nextInt(100), ChronoUnit.DAYS)));

        System.out.println("//查询是否是今天要举办生日");
        System.out.println(BusinessErrorTest16.isFamilyBirthday(LocalDateTime.now()));
        System.out.println(LocalDate.now().query(BusinessErrorTest16::isFamilyBirthday));


        System.out.println("//计算日期差");
        LocalDate today1 = LocalDate.of(2020, 12, 12);
        LocalDate specifyDate = LocalDate.of(2020, 10, 1);
        System.out.println(Period.between(specifyDate, today1).getDays());
        // P2M11D 两个月11天
        System.out.println(Period.between(specifyDate, today1));
        System.out.println(ChronoUnit.DAYS.between(specifyDate, today1));

    }

    /**
     * 在把 Date 转换为 LocalDateTime 的时候，需要通过 Date 的 toInstant 方法得到一个 UTC 时间戳进行转换，
     * 并需要提供当前的时区，这样才能把 UTC 时间转换为本地日期时间（的表示）。
     * 反过来，把 LocalDateTime 的时间表示转换为 Date 时，也需要提供时区，用于指定是哪个时区的时间表示，
     * 也就是先通过 atZone 方法把 LocalDateTime 转换为 ZonedDateTime，然后才能获得 UTC 时间戳
     */
    @Test
    public void test10() {
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(out);
    }


    public static Boolean isFamilyBirthday(TemporalAccessor date) {
        int month = date.get(MONTH_OF_YEAR);
        int day = date.get(DAY_OF_MONTH);
        if (month == Month.FEBRUARY.getValue() && day == 17)
            return Boolean.TRUE;
        if (month == Month.OCTOBER.getValue() && day == 20)
            return Boolean.TRUE;
        if (month == Month.MAY.getValue() && day == 22)
            return Boolean.TRUE;
        return Boolean.FALSE;
    }
}

/**
 * 1.我今天多次强调 Date 是一个时间戳，是 UTC 时间、没有时区概念，为什么调用其 toString 方法会输出类似 CST 之类的时区字样呢？
 *    Date的toString()方法处理的，同String中有BaseCalendar.Date date = normalize();
 *    而normalize中进行这样处理cdate = (BaseCalendar.Date) cal.getCalendarDate(fastTime,TimeZone.getDefaultRef()；
 *    因此其实是获取当前的默认时区的。
 *
 *    虽然 Date 本质是一个时间戳没有时区的概念，但是在 toString 的时候为了可读性会推测当前时区，如果得不到就会使用 GMT。
 *
 * 2.日期时间数据始终要保存到数据库中，MySQL 中有两种数据类型 datetime 和 timestamp 可以用来保存日期时间。你能说说它们的区别吗，它们是否包含时区信息呢？
 *     从下面几个维度进行区分：
 *      占用空间：datetime：8字节，timestamp 4字节
 *      表示范围：datetime	'1000-01-01 00:00:00.000000' to '9999-12-31 23:59:59.999999'
 * 		        timestamp	'1970-01-01 00:00:01.000000' to '2038-01-19 03:14:07.999999'
 * 		        时区：timestamp 只占 4 个字节，而且是以utc的格式储存， 它会自动检索当前时区并进行转换。
 * 	    时区：datetime以 8 个字节储存，不会进行时区的检索.
 * 	         也就是说，对于timestamp来说，如果储存时的时区和检索时的时区不一样，那么拿出来的数据也不一样。对于datetime来说，存什么拿到的就是什么。
 */























