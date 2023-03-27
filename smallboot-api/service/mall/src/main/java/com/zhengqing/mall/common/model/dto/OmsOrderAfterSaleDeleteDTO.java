package com.zhengqing.mall.common.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p> 商城-订单-售后-删除-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/19 9:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商城-订单-售后-删除-提交参数")
public class OmsOrderAfterSaleDeleteDTO {

    @NotEmpty(message = "售后编号不能为空！")
    @ApiModelProperty(value = "售后编号", required = true, example = "[1]")
    private List<String> afterSaleNoList;

}
