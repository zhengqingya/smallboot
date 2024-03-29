package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 商城-店铺信息-分页列表-请求参数 </p>
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
@ApiModel("商城-店铺信息-分页列表-请求参数")
public class SmsShopPageDTO extends BasePageDTO {

    @ApiModelProperty("门店ID")
    private Integer shopId;

    @ApiModelProperty("门店名称")
    private String shopName;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty("是否显示（1：是 0：否）")
    private Boolean isShow;

    @ApiModelProperty(value = "门店坐标-经度", example = "104.07511")
    private Double longitude;

    @ApiModelProperty(value = "门店坐标-纬度", example = "30.55273")
    private Double latitude;

}
