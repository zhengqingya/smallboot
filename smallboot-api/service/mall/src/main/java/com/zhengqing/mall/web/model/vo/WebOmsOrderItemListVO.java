package com.zhengqing.mall.web.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>商城-订单详情-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-订单详情-响应参数")
public class WebOmsOrderItemListVO extends BaseVO {

    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("数据ID")
    private String dataId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("封面图")
    private String coverImg;

    @ApiModelProperty("商品规格")
    private String skuJson;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("单价(单位:分)  ")
    private Integer unitPrice;

    @ApiModelProperty("总价(单位:分)")
    private Integer totalPrice;

    @ApiModelProperty("类型(1->实物 2->虚拟)")
    private Byte type;

}
