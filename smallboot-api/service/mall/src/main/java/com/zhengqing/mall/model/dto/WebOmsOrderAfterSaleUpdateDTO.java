package com.zhengqing.mall.model.dto;

import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.core.custom.parameter.CheckParam;
import com.zhengqing.mall.model.enums.MallResultCodeEnum;
import com.zhengqing.mall.model.enums.OmsOrderAfterSaleStatusEnum;
import com.zhengqing.mall.model.enums.OmsOrderSaleTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> 商城-售后-更新-提交参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 14:52
 */
@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("web-商城-售后-更新-提交参数")
public class WebOmsOrderAfterSaleUpdateDTO implements CheckParam {

    @NotBlank(message = "售后编号不能为空！")
    @ApiModelProperty(value = "售后编号", required = true, example = "1")
    private String afterSaleNo;

    /**
     * {@link OmsOrderSaleTypeEnum}
     */
    @NotNull(message = "售后类型不能为空！")
    @Range(min = 1, max = 2, message = "售后类型值范围：1-2")
    @ApiModelProperty(value = "售后类型(1-退款 2-退货退款 3-换货)", required = true, example = "2")
    private Byte afterType;

    /**
     * {@link OmsOrderAfterSaleStatusEnum}
     */
    @NotNull(message = "售后状态不能为空！")
    @ApiModelProperty(value = "售后状态(前端在页面操作时获取的最新的订单状态)", required = true, example = "2")
    private Byte afterStatus;

    @NotNull(message = "处理人ID不能为空")
    @ApiModelProperty(value = "处理人ID", required = true, example = "1")
    private Long handlerId;

    @NotBlank(message = "处理人姓名不能为空")
    @ApiModelProperty(value = "处理人姓名", required = true, example = "zhengqingya")
    private String handlerName;

    @ApiModelProperty(value = "处理人结果-处理退款（true->同意 false->拒绝）", example = "true")
    private Boolean handlerResultForRefund;

    @ApiModelProperty(value = "处理人备注-处理退款", example = "It's OK!")
    private String handlerRemarkForRefund;

    @ApiModelProperty(value = "处理人结果-处理申请（true->同意 false->拒绝）", example = "true")
    private Boolean handlerResultForApply;

    @ApiModelProperty(value = "处理人备注-处理申请", example = "It's OK!")
    private String handlerRemarkForApply;

    @JsonIgnore
    @ApiModelProperty(value = "实际退款金额 (单位：分)", hidden = true)
    private Integer refundPrice;

    @Override
    public void checkParam() {
        // 拿到前端提交的操作订单状态 变更为 后端即将要更新的订单状态值
        log.info("[商城] web-商城-售后-更新-提交订单状态值：[{}]", this.afterStatus);
        switch (OmsOrderSaleTypeEnum.getEnum(this.afterType)) {
            case REFUND:
                Assert.notNull(this.handlerResultForRefund, "处理人结果不能为空！");
                Assert.isTrue(OmsOrderAfterSaleStatusEnum.APPLY_REFUND.getStatus().equals(this.afterStatus), MallResultCodeEnum.售后状态已变更.getDesc());
                this.afterStatus = this.handlerResultForRefund ?
                        OmsOrderAfterSaleStatusEnum.AGREE_REFUND.getStatus() : OmsOrderAfterSaleStatusEnum.REJECT_REFUND.getStatus();
                break;
            case SALE_RETURN_AND_REFUND:
                switch (OmsOrderAfterSaleStatusEnum.getEnum(this.afterStatus)) {
                    case APPLY:
                        Assert.notNull(this.handlerResultForApply, "处理人结果不能为空！");
                        this.afterStatus = this.handlerResultForApply ?
                                OmsOrderAfterSaleStatusEnum.AGREE_APPLY.getStatus() : OmsOrderAfterSaleStatusEnum.REJECT_APPLY.getStatus();
                        break;
                    case APPLY_REFUND:
                        Assert.notNull(this.handlerResultForRefund, "处理人结果不能为空！");
                        // 前端点击同意退款后，后端将状态变更为退款中
                        this.afterStatus = this.handlerResultForRefund ?
                                OmsOrderAfterSaleStatusEnum.AGREE_REFUND.getStatus() : OmsOrderAfterSaleStatusEnum.REJECT_REFUND.getStatus();
                        break;
                    default:
                        throw new MyException(MallResultCodeEnum.售后状态已变更.getDesc());
                }
                break;
            default:
                break;
        }
        switch (OmsOrderAfterSaleStatusEnum.getEnum(this.afterStatus)) {
            case REJECT_APPLY:
                Assert.notBlank(this.handlerRemarkForApply, "备注说明不能为空！");
                break;
            case REJECT_REFUND:
                Assert.notBlank(this.handlerRemarkForRefund, "备注说明不能为空！");
                break;
            default:
                break;
        }
    }

}
