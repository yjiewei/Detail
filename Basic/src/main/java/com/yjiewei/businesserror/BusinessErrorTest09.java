package com.yjiewei.businesserror;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * @author yjiewei
 * @date 2022/8/1
 */
public class BusinessErrorTest09 {

    private static Log log = LogFactory.getLog(BusinessErrorTest09.class);

    @Test
    public void test1() {
        log.info("09 数值计算：注意精度、舍入和溢出问题");

        System.out.println(0.1+0.2);
        System.out.println(1.0-0.8);
        System.out.println(4.015*100);
        System.out.println(123.3/100);

        double amount1 = 2.15;
        double amount2 = 1.10;
        if (amount1 - amount2 == 1.05)
            System.out.println("OK");

        System.out.println("出现这种问题的主要原因是，计算机是以二进制存储数值的，浮点数也不例外");
        System.out.println("0.1 的二进制表示为 0.0 0011 0011 0011… （0011 无限循环)，再转换为十进制就是 0.1000000000000000055511151231257827021181583404541015625。" +
                "对于计算机而言，0.1 无法精确表达，这是浮点数计算造成精度损失的根源");
    }
}
