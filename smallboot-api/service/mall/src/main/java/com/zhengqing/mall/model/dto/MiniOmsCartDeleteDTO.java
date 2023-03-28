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
 * <p>
 * mini-商城-购物车-删除-提交参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/12 9:58
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("mini-商城-购物车-删除-提交参数")
public class MiniOmsCartDeleteDTO extends BaseDTO {

//    @NotNull(message = "用户id不能为空！")
//    @ApiModelProperty(value = "用户id", required = true, example = "1")
//    private Long userId;

    @NotEmpty(message = "商品sku-id不能为空！")
    @ApiModelProperty(value = "redis购物车中的hashKey-标识一个sku的商品", required = true, example = "[\"666\"]")
    private List<String> skuIdList;

}
