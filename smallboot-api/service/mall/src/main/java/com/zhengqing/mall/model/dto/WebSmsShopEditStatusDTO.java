package com.zhengqing.mall.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p> 商城-店铺状态-保存参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/20 16:11
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-店铺状态-保存参数")
public class WebSmsShopEditStatusDTO extends BaseDTO {

    @NotEmpty(message = "门店id不能为空")
    @ApiModelProperty(value = "门店ids", required = true, example = "[1]")
    private List<Integer> shopIdList;

    @ApiModelProperty("是否显示（1：是 0：否）")
    private Boolean isShow;

    @ApiModelProperty("堂食状态（1：开启 0：关闭）")
    private Boolean snackStatus;

    @ApiModelProperty("外卖状态（1：开启 0：关闭）")
    private Boolean takeoutStatus;

    @ApiModelProperty("门店营业状态（1：营业中 0：未营业）")
    private Boolean openStatus;

}
