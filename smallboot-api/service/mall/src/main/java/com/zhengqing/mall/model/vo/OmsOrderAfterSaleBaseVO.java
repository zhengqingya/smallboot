package com.zhengqing.mall.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.mall.model.bo.MallFileBO;
import com.zhengqing.mall.model.bo.OmsOrderReceiverAddressBO;
import com.zhengqing.mall.enums.OmsOrderAfterSaleStatusEnum;
import com.zhengqing.mall.enums.OmsOrderSaleTypeEnum;
import com.zhengqing.mall.enums.TpsLogisticsCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
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

    // ======================= ↓↓↓↓↓↓ 售后主体详情 ↑↑↑↑↑↑ =======================

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

    @ApiModelProperty("售后申请时间")
    private Date applyTime;

    @ApiModelProperty("退款,退/换货 原因")
    private String afterReason;

    @ApiModelProperty("说明")
    private String afterExplain;

    @ApiModelProperty("退款时间")
    private Date refundTime;

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

    // ========================== ↓↓↓↓↓↓ 收货人信息 ↓↓↓↓↓↓ ==========================

    /**
     * {@link OmsOrderReceiverAddressBO }
     */
    @JsonIgnore
    @ApiModelProperty(value = "收货人姓名", hidden = true)
    private String receiverName;

    @JsonIgnore
    @ApiModelProperty(value = "收货人电话", hidden = true)
    private String receiverPhone;

    @JsonIgnore
    @ApiModelProperty(value = "收货人地址", hidden = true)
    private String receiverAddress;

    @ApiModelProperty("收货人地址信息（注：目前暂时只支持单个发货）")
    private OmsOrderReceiverAddressBO receiverAddressObj;

    // ========================== ↑↑↑↑↑↑ 收货人信息 ↑↑↑↑↑↑ ==========================


    // ======================= ↓↓↓↓↓↓ 其它详情 ↑↑↑↑↑↑ =======================

    @JsonIgnore
    @ApiModelProperty(value = "关联售后商品ids(英文逗号分隔)", hidden = true)
    private String orderItemIds;

    @JsonIgnore
    @ApiModelProperty(value = "订单商品详情ids", hidden = true)
    private List<String> orderItemIdList;

    @ApiModelProperty(value = "订单关联商品信息")
    private List<OmsOrderItemVO> spuList;

    @ApiModelProperty("封面图")
    private String coverImg;

    @ApiModelProperty("商品名称(多个以英文逗号分隔)")
    private String spuNames;

    @ApiModelProperty("商品数量(关联所有商品的总件数)")
    private Integer spuNum;

    @ApiModelProperty("实付总金额(单位:分)")
    private Integer payPrice;

    @ApiModelProperty("运费(单位:分 0:包邮)")
    private Integer freight;

    @ApiModelProperty("用户电话")
    private String userPhone;

    public void handleOrderItemIdList() {
        this.orderItemIdList = Arrays.stream(this.orderItemIds.split(",")).map(String::trim).collect(Collectors.toList());
    }

    public void handleData() {
        this.afterTypeName = OmsOrderSaleTypeEnum.getEnum(this.afterType).getDesc();
        this.afterStatusName = OmsOrderAfterSaleStatusEnum.getEnum(this.afterStatus).getDesc();
        this.orderItemIdList = Arrays.stream(this.orderItemIds.split(",")).map(String::trim).collect(Collectors.toList());

        this.coverImg = this.spuList.get(0).getCoverImg();
        StringJoiner spuNameSj = new StringJoiner(",");
        this.spuNum = 0;
        this.spuList.forEach(item -> {
            spuNameSj.add(item.getName());
            this.spuNum += item.getNum();
        });
        this.spuNames = spuNameSj.toString();
    }

}
