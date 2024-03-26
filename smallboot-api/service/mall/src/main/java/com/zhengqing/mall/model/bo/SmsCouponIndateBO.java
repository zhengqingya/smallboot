package com.zhengqing.mall.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p> 商城-优惠券-有效期 </p>
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
@ApiModel("商城-优惠券-有效期")
public class SmsCouponIndateBO extends BaseBO {

    @NotNull(message = "有效期类型不能为空！")
    @ApiModelProperty("类型（1：绝对时间 2：相对时间）")
    private Integer type;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("发放后n天内有效")
    private Integer day;

}
