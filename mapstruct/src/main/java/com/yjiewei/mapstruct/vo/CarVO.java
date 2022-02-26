/*
 * @author yjiewei
 * @date 2022/2/26 20:36
 */
package com.yjiewei.mapstruct.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CarVO {
    private Long id;
    private String vin; // 车编号
    private double price;
    private double totalPrice;
    private Date publishDate;
    private String brandName;
    private boolean hasPart;
    private DriverVO driverVO;
}
