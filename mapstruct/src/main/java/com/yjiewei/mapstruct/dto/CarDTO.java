/*
 * @author yjiewei
 * @date 2022/2/26 20:35
 */
package com.yjiewei.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private Long id;
    private String vin; // 车编号
    private double price;
    private double totalPrice;
    private Date publishDate;
    private String brand;
    private List<PartDTO> partDTOS;
    private DriverDTO driverDTO;
}
