/*
 * @author yjiewei
 * @date 2022/2/26 20:36
 */
package com.yjiewei.mapstruct.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarVO {
    private Long id;
    private String vin; // 车编号
    private double price;
    private double totalPrice;
    private String publishDate;
    private String brandName;
    private boolean hasPart;
    private DriverVO driverVO;
}
