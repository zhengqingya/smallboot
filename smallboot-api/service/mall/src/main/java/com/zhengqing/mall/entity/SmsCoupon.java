package com.zhengqing.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.zhengqing.common.db.entity.BaseEntity;
import com.zhengqing.mall.model.bo.SmsCouponIndateBO;
import com.zhengqing.mall.model.bo.SmsCouponOpenTimeBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * <p>  商城-优惠券 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/03/26 15:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sms_coupon", autoResultMap = true)
@ApiModel("商城-优惠券")
public class SmsCoupon extends BaseEntity<SmsCoupon> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("状态（1:正常 2:停用 3:作废）")
    private Integer status;

    /**
     * {@link com.zhengqing.mall.enums.SmsCouponTypeEnum }
     */
    @ApiModelProperty("类型")
    private Integer type;

    /**
     * {@link com.zhengqing.mall.enums.SmsCouponTypeEnum#折扣券 }
     */
    @ApiModelProperty("折扣")
    private Integer discount;

    @ApiModelProperty("最高折扣金额（单位：分）")
    private Integer discountMaxPrice;

    /**
     * {@link com.zhengqing.mall.enums.SmsCouponTypeEnum#满减券 }
     */
    @ApiModelProperty("满")
    private Integer fullPrice;

    @ApiModelProperty("减")
    private Integer reducePrice;

    @ApiModelProperty("面额值（单位:分）")
    private Integer faceValue;

    @ApiModelProperty("使用门槛（满xx分可用，0:无限制）")
    private Integer threshold;

    @ApiModelProperty("是否可赠送（1:是 2:否）")
    private Boolean isGive;

    @ApiModelProperty("有效期")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<SmsCouponIndateBO> indateObj;

    @ApiModelProperty("可用店铺ID（为空标识所有店铺）")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Integer> useShopIdList;

    @ApiModelProperty("可用商品ID（为空标识所有商品）")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> useSpuIdList;

    @ApiModelProperty("说明")
    private String explain;

    @ApiModelProperty("总库存(0:不限制)")
    private Integer totalStock;

    @ApiModelProperty("已用库存")
    private Integer useStock;

    @ApiModelProperty("每人限购(0:不限制)")
    private Integer limitCount;

    @TableField(typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty("可用营业时间")
    private List<SmsCouponOpenTimeBO> openTimeList;

}
