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
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p> mini-商城-购物车-批量更新数量 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/12 17:27
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class MiniOmsCartBatchUpdateNumDTO extends BaseDTO {

    @ApiModelProperty(value = "数据", example = "1")
    private List<UpdateNum> list;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateNum {
        @NotBlank(message = "商品id不能为空！")
        @ApiModelProperty(value = "商品id", required = true, example = "1")
        private String spuId;

        @NotBlank(message = "商品规格ID不能为空！")
        @ApiModelProperty(value = "商品规格ID", example = "888", required = true)
        private String skuId;

        @NotNull(message = "商品数量不能为空！")
        @ApiModelProperty(value = "商品数量", required = true, example = "1")
        private Integer num;
    }

}
