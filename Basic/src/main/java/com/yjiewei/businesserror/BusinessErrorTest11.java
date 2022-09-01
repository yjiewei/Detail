package com.yjiewei.businesserror;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 11 | 空值处理：分不清楚的null和恼人的空指针
 * https://time.geekbang.org/column/article/216830
 *    1.参数值是 Integer 等包装类型，使用时因为自动拆箱出现了空指针异常；
 *    2.字符串比较出现空指针异常；
 *    3.诸如 ConcurrentHashMap 这样的容器不支持 Key 和 Value 为 null，强行 put null 的 Key 或 Value 会出现空指针异常；
 *    4.A 对象包含了 B，在通过 A 对象的字段获得 B 之后，没有对字段判空就级联调用 B 的方法出现空指针异常；
 *    5.方法或远程服务返回的 List 不是空而是 null，没有进行判空就直接调用 List 的方法出现空指针异常。
 * @author yangjiewei
 * @date 2022/9/1
 */
@Slf4j
@RestController
@RequestMapping("/11")
public class BusinessErrorTest11 {

    private List<String> wrongMethod(FooService fooService, Integer i, String s, String t) {
        // 4个参数都有可能导致下面这一行空指针，i可能是在拆箱的时候发现的，只有当特定参数时才会出现空指针，生产上不好重现。
        log.info("result {} {} {} {}", i + 1, s.equals("OK"), s.equals(t),
                new ConcurrentHashMap<String, String>().put(null, null));
        if (fooService.getBarService().bar().equals("OK"))
            log.info("OK");
        log.info("字符串比较时，字面量放在前面");
        return null;
    }

    @GetMapping("/wrong")
    public int wrong(@RequestParam(value = "test", defaultValue = "1111") String test) {
        return wrongMethod(test.charAt(0) == '1' ? null : new FooService(),
                test.charAt(1) == '1' ? null : 1,
                test.charAt(2) == '1' ? null : "OK",
                test.charAt(3) == '1' ? null : "OK").size();
    }


    /**
     * 正确解决空指针的方法
     * @param fooService
     * @param i
     * @param s
     * @param t
     * @return
     */
    private List<String> rightMethod(FooService fooService, Integer i, String s, String t) {
        log.info("result {} {} {} {}", Optional.ofNullable(i).orElse(0) + 1, "OK".equals(s), Objects.equals(s, t), new HashMap<String, String>().put(null, null));
        Optional.ofNullable(fooService)
                .map(FooService::getBarService)
                .filter(barService -> "OK".equals(barService.bar()))
                .ifPresent(result -> log.info("OK"));
        return new ArrayList<>();
    }

    @GetMapping("right")
    public int right(@RequestParam(value = "test", defaultValue = "1111") String test) {
        // 如果没有实例化BarService，还是不能出现OK
        return Optional.ofNullable(rightMethod(test.charAt(0) == '1' ? null : new FooService(new BarService()),
                test.charAt(1) == '1' ? null : 1,
                test.charAt(2) == '1' ? null : "OK",
                test.charAt(3) == '1' ? null : "OK"))
                .orElse(Collections.emptyList()).size();
    }

    class FooService {
        @Getter
        private BarService barService;

        public FooService() {
        }

        public FooService(BarService barService) {
            this.barService = barService;
        }
    }

    class BarService {
        String bar() {
            return "OK";
        }
    }
}
