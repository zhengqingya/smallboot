package com.zhengqing.mall.common.model.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.mall.common.model.enums.OmsOrderShippingReceiptTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * <p>商城-订单配送表-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商城-订单配送-参数")
public class OmsOrderShippingVO {

    @ApiModelProperty("主键ID")
    private String shippingId;

    @JsonIgnore
    @ApiModelProperty(value = "订单编号", hidden = true)
    private String orderNo;

    // ========================== ↓↓↓↓↓↓ 收货人信息 ↓↓↓↓↓↓ ==========================

    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @ApiModelProperty("收货人电话")
    private String receiverPhone;

    @ApiModelProperty("收货人地址")
    private String receiverAddress;

    // ========================== ↓↓↓↓↓↓ 发货人信息 ↓↓↓↓↓↓ ==========================

    @ApiModelProperty("发货人")
    private String delivererName;

    @ApiModelProperty("发货人电话")
    private String delivererPhone;

    @ApiModelProperty("发货地址")
    private String delivererAddress;

    // ========================== ↓↓↓↓↓↓ 其它 ↓↓↓↓↓↓ ==========================

    @ApiModelProperty("发货时间")
    private Date deliverTime;

    @ApiModelProperty("物流公司")
    private String logisticsCompany;

    @ApiModelProperty("物流公司编码")
    private String logisticsCode;

    @ApiModelProperty("物流单号")
    private String logisticsNo;

    /**
     * {@link OmsOrderShippingReceiptTypeEnum }
     */
//    @ApiModelProperty("收货类型(1->手动收货 2->自动收货)")
//    private Byte receiptType;

    @ApiModelProperty("收货时间")
    private Date receiptTime;

    @ApiModelProperty(value = "自动收货时间")
    private Date autoReceiptTime;

    @ApiModelProperty(value = "关联商品详情")
    private List<OmsOrderShippingItemVO> spuList;

    // ========================== ↓↓↓↓↓↓ 隐藏字段->只在后台处理使用 ↓↓↓↓↓↓ ==========================

    @JsonIgnore
    @ApiModelProperty(value = "商品-名称", hidden = true)
    private String spuName;

    @JsonIgnore
    @ApiModelProperty(value = "商品-封面图", hidden = true)
    private String spuCoverImg;

    public void handleData() {

    }

}
