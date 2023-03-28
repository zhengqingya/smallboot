package com.zhengqing.mall.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.mall.model.enums.OmsOrderAfterSaleStatusEnum;
import com.zhengqing.mall.model.enums.OmsOrderSaleTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>商城-售后列表base-展示视图</p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商城-售后列表base-展示视图")
public class OmsOrderAfterSaleBaseVO extends BaseVO {

    @ApiModelProperty("售后编号")
    private String afterSaleNo;

    @ApiModelProperty("订单编号")
    private String orderNo;

    /**
     * {@link OmsOrderSaleTypeEnum}
     */
    @ApiModelProperty("售后类型(1-退款 2-退货退款 3-换货)")
    private Byte afterType;

    @ApiModelProperty("售后类型名称")
    private String afterTypeName;

    /**
     * {@link OmsOrderAfterSaleStatusEnum}
     */
    @ApiModelProperty("售后状态")
    private Byte afterStatus;

    @ApiModelProperty("售后状态名称")
    private String afterStatusName;

    @ApiModelProperty("退款金额 (单位：分)")
    private Integer refundPrice;

    // ======================= ↑↑↑↑↑↑ 售后主体详情 ↓↓↓↓↓↓ =======================

    // ======================= ↓↓↓↓↓↓ 其它详情 ↑↑↑↑↑↑ =======================

    @JsonIgnore
    @ApiModelProperty(value = "关联售后商品ids(英文逗号分隔)", hidden = true)
    private String orderItemIds;

    @JsonIgnore
    @ApiModelProperty(value = "订单商品详情ids", hidden = true)
    private List<String> orderItemIdList;

    public void handleOrderItemIdList() {
        this.orderItemIdList = Arrays.stream(this.orderItemIds.split(",")).map(String::trim).collect(Collectors.toList());
    }

    public void handleData() {
        this.afterTypeName = OmsOrderSaleTypeEnum.getEnum(this.afterType).getDesc();
        this.afterStatusName = OmsOrderAfterSaleStatusEnum.getEnum(this.afterStatus).getDesc();
        this.orderItemIdList = Arrays.stream(this.orderItemIds.split(",")).map(String::trim).collect(Collectors.toList());
    }

}
