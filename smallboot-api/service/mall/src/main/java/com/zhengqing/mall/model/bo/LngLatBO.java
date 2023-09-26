package com.zhengqing.mall.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 经纬度 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/13 09:51
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LngLatBO extends BaseBO {

    /**
     * 经纬度：104.07511 , 30.55273 经度：104.07511，纬度：30.55273 地址：四川省成都市武侯区天府三街
     */
    @ApiModelProperty(value = "门店坐标-经度", example = "104.07511")
    private Double longitude;

    @ApiModelProperty(value = "门店坐标-纬度", example = "30.55273")
    private Double latitude;

}
