package com.zhengqing.mall.mini.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengqing.common.core.custom.parameter.ParamCheck;
import com.zhengqing.mall.common.model.bo.OmsOrderReceiverAddressBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p> 商城-商品购买-保存参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/06/23 15:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("mini-商城-商品购买-保存参数")
public class MiniOmsSpuBuyDTO implements ParamCheck {

//    @NotNull(message = "操作用户id不能为空")
//    @ApiModelProperty(value = "操作用户id", required = true, example = "666")
//    private Long userId;

//    @NotBlank(message = "微信openid不能为空")
//    @ApiModelProperty(value = "微信openid", required = true, example = "xxx")
//    private String wxOpenid;

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

    @Valid
//    @NotNull(message = "收货人地址信息不能为空")
    @ApiModelProperty("收货人地址信息（实物时填写地址）")
    private OmsOrderReceiverAddressBO receiverAddressObj;

    // ========================== ↑↑↑↑↑↑ 收货人信息 ↑↑↑↑↑↑ ==========================

    @Valid
    @NotEmpty(message = "商品sku信息不能为空")
    @ApiModelProperty(value = "商品sku", required = true)
    private List<MiniPmsSpuBuySkuDTO> skuList;

    @NotNull(message = "商品运费不能为空！")
    @ApiModelProperty(value = "商品运费(单位:分 0:包邮) - 多商品中的最大运费", required = true, example = "10")
    private Integer freight;

    @NotNull(message = "商品原价总金额不能为空！")
    @ApiModelProperty(value = "商品原价总金额(单位:分)", required = true, example = "1000")
    private Integer totalPrice;

    @NotNull(message = "实付总金额不能为空！")
    @ApiModelProperty(value = "实付总金额(单位:分)", required = true, example = "1000")
    private Integer payPrice;

    @Length(max = 100, message = "备注最多100字！")
    @ApiModelProperty("订单备注")
    private String orderRemark;

    @Override
    public void checkParam() {
        if (this.receiverAddressObj != null) {
            this.receiverName = this.receiverAddressObj.getReceiverName();
            this.receiverPhone = this.receiverAddressObj.getReceiverPhone();
            this.receiverAddress = this.receiverAddressObj.getReceiverAddress();
        }
    }

}
