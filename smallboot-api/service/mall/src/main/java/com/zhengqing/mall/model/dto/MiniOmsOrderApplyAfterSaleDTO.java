package com.zhengqing.mall.model.dto;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.zhengqing.common.core.custom.parameter.ParamCheck;
import com.zhengqing.mall.model.bo.MallFileBO;
import com.zhengqing.mall.model.enums.MallResultCodeEnum;
import com.zhengqing.mall.model.enums.OmsOrderAfterSaleStatusEnum;
import com.zhengqing.mall.model.enums.OmsOrderSaleTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * <p> 商城-申请售后（退款/退货/退货退款）-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/6/25 10:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("mini-商城-申请售后（退款/退货/退货退款）-提交参数")
public class MiniOmsOrderApplyAfterSaleDTO implements ParamCheck {

    @NotNull(message = "操作用户id不能为空")
    @ApiModelProperty(value = "操作用户id", required = true, example = "1435486752507736065")
    private Long userId;

    /**
     * {@link OmsOrderSaleTypeEnum}
     */
    @Range(min = 1, max = 2, message = "售后类型范围：1-2")
    @NotNull(message = "售后类型不能为空!")
    @ApiModelProperty(value = "售后类型(1-退款 2-退货退款)", required = true, example = "2")
    private Byte afterType;

    /**
     * {@link OmsOrderAfterSaleStatusEnum}
     */
    @JsonIgnore
    @ApiModelProperty(value = "售后状态", hidden = true)
    private Byte afterStatus;

    @NotNull(message = "订单编号不能为空！")
    @ApiModelProperty(value = "订单编号", required = true, example = "211018515967000578")
    private String orderNo;

    @NotEmpty(message = "订单关联商品ids不能为空")
    @ApiModelProperty(value = "订单关联商品ids", required = true, example = "1")
    private List<String> orderItemIdList;

    @NotBlank(message = "退款/退货原因不能为空!")
    @ApiModelProperty(value = "退款/退货原因", required = true, example = "其它")
    private String reason;

    @Length(max = 300, message = "说明最多300字")
    @ApiModelProperty(value = "说明", example = "this is refund.")
    private String explain;

    @ApiModelProperty(value = "退款金额(单位:分)", required = true, example = "100")
    private Integer refundPrice;

    @Valid
    @Size(max = 4, message = "凭证图最多4张！")
    @ApiModelProperty(value = "凭证图")
    private List<MallFileBO> certImgList;

    @JsonIgnore
    @ApiModelProperty(value = "凭证图", hidden = true)
    private String certImgJson;

    @Override
    public void checkParam() {
        OmsOrderSaleTypeEnum orderSaleTypeEnum = OmsOrderSaleTypeEnum.getEnum(this.afterType);
        if (orderSaleTypeEnum == OmsOrderSaleTypeEnum.SALE_RETURN_AND_REFUND
                || orderSaleTypeEnum == OmsOrderSaleTypeEnum.REFUND) {
            Assert.notNull(this.refundPrice, MallResultCodeEnum.退款金额不能为空.getDesc());
        }
        if (CollectionUtils.isEmpty(this.certImgList)) {
            this.certImgList = Lists.newArrayList();
        }
        this.certImgJson = JSON.toJSONString(this.certImgList);

        // 售后状态处理
        this.afterStatus = OmsOrderSaleTypeEnum.REFUND == orderSaleTypeEnum
                ? OmsOrderAfterSaleStatusEnum.APPLY_REFUND.getStatus() : OmsOrderAfterSaleStatusEnum.APPLY.getStatus();
    }

}
