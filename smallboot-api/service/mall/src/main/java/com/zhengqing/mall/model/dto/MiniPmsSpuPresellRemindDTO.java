package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * <p> mini-商品-预售提醒-请求参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/8 14:50
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("mini-商品-预售提醒-请求参数")
public class MiniPmsSpuPresellRemindDTO extends BaseDTO {

    @NotBlank(message = "商品ID不能为空！")
    @ApiModelProperty(value = "商品ID", required = true, example = "1")
    private String spuId;

    @NotBlank(message = "微信openid不能为空！")
    @ApiModelProperty(value = "微信openid", required = true, example = "1")
    private String wxOpenid;

}
