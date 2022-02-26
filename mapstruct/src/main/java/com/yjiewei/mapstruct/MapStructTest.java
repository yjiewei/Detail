/*
 * @author yjiewei
 * @date 2022/2/26 20:50
 */
package com.yjiewei.mapstruct;

import com.yjiewei.mapstruct.dto.CarDTO;
import com.yjiewei.mapstruct.dto.DriverDTO;
import com.yjiewei.mapstruct.dto.PartDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MapstructApplication.class)
public class MapStructTest {

    /**
     * 使用mapstruct实现映射
     * CarDTO -> CarVO
     */
    @Test
    public void test1() {
        // 模拟业务构造CarDTO对象
        CarDTO carDTO = buildCarDTO();

        // 转换成VO 如果用传统的方式，这里hasPart是匹配不上的，需要手动判断
        System.out.println(carDTO.toString());

    }

    private CarDTO buildCarDTO() {
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setId(2L);
        driverDTO.setName("司机1");
        List<PartDTO> partDTOS = new ArrayList<>();
        PartDTO partDTO = new PartDTO();
        partDTO.setPartId(3L);
        partDTO.setPartName("零件1");
        PartDTO partDTO1 = new PartDTO();
        partDTO1.setPartId(3L);
        partDTO1.setPartName("零件1");
        partDTOS.add(partDTO1);

        return CarDTO.builder()
                .id(1L)
                .brand("无印良品")
                .price(12.00)
                .vin("vin123")
                .totalPrice(100.00)
                .publishDate(new Date())
                .driverDTO(driverDTO)
                .partDTOS(partDTOS)
                .build();
    }
}
