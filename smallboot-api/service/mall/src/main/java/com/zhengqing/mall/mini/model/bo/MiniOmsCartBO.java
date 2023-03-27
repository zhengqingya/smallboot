package com.zhengqing.mall.mini.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p> mini-商城-购物车-存储参数 </p>
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
@ApiModel("mini-商城-购物车-存储参数")
public class MiniOmsCartBO extends BaseBO {

    @ApiModelProperty(value = "用户id", example = "1")
    private Long userId;

    @ApiModelProperty(value = "商品id", example = "1")
    private String spuId;

    @ApiModelProperty(value = "商品规格ID(redis购物车中的hashKey-标识一个sku的商品)", example = "888")
    private String skuId;

    @ApiModelProperty(value = "商品数量", example = "1")
    private Integer num;

    @ApiModelProperty(value = "操作时间")
    private Date time;

}
