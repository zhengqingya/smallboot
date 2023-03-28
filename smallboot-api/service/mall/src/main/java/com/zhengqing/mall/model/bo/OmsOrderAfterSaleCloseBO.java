package com.zhengqing.mall.model.bo;

import com.zhengqing.common.base.model.bo.BaseBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> 商城-申请售后-卖家/买家未处理-自动关闭-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/25 10:46
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("商城-申请售后-卖家/买家未处理-自动关闭-提交参数")
public class OmsOrderAfterSaleCloseBO extends BaseBO {

    @ApiModelProperty("售后编号")
    private String afterSaleNo;

    @ApiModelProperty("租户id(mq使用)")
    private Integer tenantId;

    @ApiModelProperty("是否为买家未处理而导致的售后关闭")
    private Boolean isBuyer;

}
