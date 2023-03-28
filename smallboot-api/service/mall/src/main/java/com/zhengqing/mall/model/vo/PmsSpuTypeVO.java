package com.zhengqing.mall.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.mall.model.enums.PmsSpuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>商城-商品类型列表-展示视图</p>
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
@ApiModel
public class PmsSpuTypeVO extends BaseVO {

    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("名称")
    private String name;

    /**
     * {@link PmsSpuTypeEnum}
     */
    @ApiModelProperty("类型")
    private Byte type;

    @ApiModelProperty("优惠券ID")
    private Long couponId;

    @ApiModelProperty("优惠券名称")
    private String couponName;

    @ApiModelProperty("优惠券数量")
    private Integer couponNum;

    /**
     * 是否为虚拟-优惠券商品
     *
     * @return true->是 false->否
     * @author zhengqingya
     * @date 2021/12/22 15:45
     */
    public boolean isVirtualCoupon() {
        return PmsSpuTypeEnum.VIRTUAL_COUPON.getType().equals(this.type);
    }

}
