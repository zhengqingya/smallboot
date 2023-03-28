package com.zhengqing.mall.model.vo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.mall.model.bo.MallFileBO;
import com.zhengqing.mall.model.enums.TpsLogisticsCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * <p> 商城-售后-详情-展示视图 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 11:05
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-售后-详情-展示视图")
public class WebOmsOrderAfterSaleDetailVO extends WebOmsOrderAfterSaleBaseVO {

    @ApiModelProperty("退款,退/换货 原因")
    private String afterReason;

    @ApiModelProperty("说明")
    private String afterExplain;

    @ApiModelProperty("退款时间")
    private Date refundTime;

    @JsonIgnore
    @ApiModelProperty(value = "凭证图", hidden = true)
    private String certImgJson;

    @ApiModelProperty(value = "凭证图")
    private List<MallFileBO> certImgList;

    @ApiModelProperty("处理人结果-处理退款（true->同意 false->拒绝）")
    private Boolean handlerResultForRefund;

    @ApiModelProperty("处理人备注-处理退款")
    private String handlerRemarkForRefund;

    @ApiModelProperty("处理人处理时间-处理退款")
    private Date handlerTimeForRefund;

    @ApiModelProperty("处理人结果-处理申请（true->同意 false->拒绝）")
    private Boolean handlerResultForApply;

    @ApiModelProperty("处理人备注-处理申请")
    private String handlerRemarkForApply;

    @ApiModelProperty("处理人处理时间-处理申请")
    private Date handlerTimeForApply;

    @ApiModelProperty("售后关闭时间")
    private Date closeTime;

    @ApiModelProperty(value = "售后卖家自动关闭时间")
    private Date sellerAutoCloseTime;

    @ApiModelProperty(value = "售后买家自动关闭时间")
    private Date buyerAutoCloseTime;

    /**
     * {@link TpsLogisticsCodeEnum }
     */
    @ApiModelProperty("物流公司编码")
    private String returnLogisticsCode;

    @ApiModelProperty("退/换货 物流单号")
    private String returnLogisticsNo;

    // ======================= ↑↑↑↑↑↑ 售后主体详情 ↓↓↓↓↓↓ =======================

    // ======================= ↓↓↓↓↓↓ 其它详情 ↑↑↑↑↑↑ =======================

    @ApiModelProperty(value = "订单关联商品信息")
    private List<OmsOrderItemVO> spuList;

    @Override
    public void handleData() {
        super.handleData();
        this.certImgList = JSON.parseArray(this.certImgJson, MallFileBO.class);
    }

}
