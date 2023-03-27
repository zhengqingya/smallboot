package com.zhengqing.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>  商城-售后详情表 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oms_order_after_sale_item")
@ApiModel("商城-售后详情表")
public class OmsOrderAfterSaleItem extends Model<OmsOrderAfterSaleItem> {

    @ApiModelProperty("ID")
    @TableId(type = IdType.INPUT)
    private String id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("售后编号")
    private String afterSaleNo;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("订单详情id")
    private String orderItemId;

}
