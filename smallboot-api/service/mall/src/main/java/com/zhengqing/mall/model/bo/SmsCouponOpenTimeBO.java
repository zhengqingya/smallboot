package com.zhengqing.mall.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p> 商城-优惠券-可用营业时间 </p>
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
@ApiModel("商城-优惠券-可用营业时间")
public class SmsCouponOpenTimeBO extends BaseBO {

    @NotEmpty(message = "营业时间不能为空！")
    @ApiModelProperty("营业时间(1~7)")
    private List<Integer> weekList;

    @NotBlank(message = "营业时间不能为空！")
    @ApiModelProperty("一天中开始营业时间点(HH:mm)")
    private String startTime;

    @NotBlank(message = "营业时间不能为空！")
    @ApiModelProperty("一天中结束营业时间点(HH:mm)")
    private String endTime;

}
