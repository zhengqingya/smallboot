package com.zhengqing.mall.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * <p> 商城-申请售后-撤销申请-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 14:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("mini-商城-申请售后-撤销申请-提交参数")
public class MiniOmsOrderRepealAfterSaleDTO {

    @NotNull(message = "售后编号不能为空！")
    @ApiModelProperty(value = "售后编号", required = true, example = "1")
    private String afterSaleNo;

}
