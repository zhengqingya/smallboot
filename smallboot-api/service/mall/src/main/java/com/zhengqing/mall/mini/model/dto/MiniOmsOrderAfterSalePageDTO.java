package com.zhengqing.mall.mini.model.dto;

import com.zhengqing.common.base.model.dto.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * <p> 商城-售后列表-查询参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 15:30
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("mini-商城-售后列表-查询参数")
public class MiniOmsOrderAfterSalePageDTO extends BasePageDTO {

    @ApiModelProperty("搜索关键字（售后编号或商品名称）")
    private String keyWord;

    @NotNull(message = "用戶ID不能为空！")
    @ApiModelProperty(value = "用户ID", required = true)
    private Long userId;

}
