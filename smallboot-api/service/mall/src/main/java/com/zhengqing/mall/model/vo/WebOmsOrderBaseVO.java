package com.zhengqing.mall.model.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.mall.model.bo.OmsOrderReceiverAddressBO;
import com.zhengqing.mall.model.bo.WebOmsOrderReShippingBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>商城-订单base-展示视图</p>
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
@ApiModel("web-商城-订单base-展示视图")
public class WebOmsOrderBaseVO extends OmsOrderBaseVO {

    @ApiModelProperty("运费(单位:分 0:包邮)")
    private Integer freight;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户电话")
    private String userPhone;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

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

    @ApiModelProperty("配送信息（注：目前暂时只支持单个物流）")
    private WebOmsOrderReShippingBO shippingObj;

    @ApiModelProperty("订单关联商品信息")
    private List<OmsOrderItemVO> spuList;

    @ApiModelProperty("是否售后处理 -- 售后中/售后完成 (true->是 false->否)")
    private Boolean isAfterSale;

    @Override
    public void handleData() {
        super.handleData();
        this.receiverAddressObj = OmsOrderReceiverAddressBO.builder()
                .receiverName(this.receiverName)
                .receiverPhone(this.receiverPhone)
                .receiverAddress(this.receiverAddress)
                .build();
        if (this.isAfterSale == null) {
            this.isAfterSale = false;
        }
        if (!CollectionUtils.isEmpty(this.spuList)) {
            this.spuList.forEach(item -> {
                if (item.getIsAfterSale() == null) {
                    item.setIsAfterSale(false);
                }
            });
        }
    }


}
