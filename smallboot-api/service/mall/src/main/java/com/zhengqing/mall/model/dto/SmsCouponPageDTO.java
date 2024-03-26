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
 * <p> 商城-优惠券-分页列表-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/03/26 15:37
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商城-优惠券-分页列表-请求参数")
public class SmsCouponPageDTO extends BasePageDTO {

    @ApiModelProperty("名称")
    private String name;

}
