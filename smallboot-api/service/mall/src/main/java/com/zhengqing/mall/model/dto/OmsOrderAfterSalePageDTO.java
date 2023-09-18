package com.zhengqing.mall.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.model.dto.BasePageDTO;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.core.custom.parameter.CheckParam;
import com.zhengqing.mall.model.enums.OmsOrderAfterSaleStatusEnum;
import com.zhengqing.mall.model.enums.OmsOrderAfterSaleTabEnum;
import com.zhengqing.mall.model.enums.OmsOrderSaleTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * <p> 商城-售后列表-查询参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 15:30
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-售后列表-查询参数")
public class OmsOrderAfterSalePageDTO extends BasePageDTO implements CheckParam {

    /**
     * {@link OmsOrderAfterSaleTabEnum}
     */
    @ApiModelProperty("tab条件")
    private Byte tabValue;

    /**
     * {@link OmsOrderAfterSaleStatusEnum}
     */
    @JsonIgnore
    @ApiModelProperty(value = "售后状态", hidden = true)
    private List<Byte> afterStatusList;

    /**
     * {@link OmsOrderSaleTypeEnum}
     */
    @ApiModelProperty("售后类型(1-退款 2-退货退款 3-换货)")
    private Byte afterType;

    @ApiModelProperty("售后编号")
    private String afterSaleNo;

    @ApiModelProperty("商品名称")
    private String spuName;


    @ApiModelProperty("用户电话")
    private String userPhone;

    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @ApiModelProperty("收货人电话")
    private String receiverPhone;

    @ApiModelProperty("收货人电话后4位")
    private String receiverPhoneAfterFour;

    @ApiModelProperty(value = "开始时间(申请时间)", example = "2021-06-06 10:10:55")
    @DateTimeFormat(pattern = MyDateUtil.DATE_TIME_FORMAT, iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startTime;

    @ApiModelProperty(value = "结束时间(申请时间)", example = "2022-06-06 10:10:55")
    @DateTimeFormat(pattern = MyDateUtil.DATE_TIME_FORMAT, iso = DateTimeFormat.ISO.DATE_TIME)
    private Date endTime;

    @Override
    public void checkParam() {
        this.afterStatusList = this.getAfterStatusList(this.tabValue);
    }


    /**
     * 根据tab值获取售后订单状态
     *
     * @param tabValue tab值
     * @return 售后订单状态
     * @author zhengqingya
     * @date 2021/11/23 18:00
     */
    public List<Byte> getAfterStatusList(Byte tabValue) {
        List<Byte> afterStatusList = Lists.newLinkedList();
        if (tabValue == null) {
            return afterStatusList;
        }
        OmsOrderAfterSaleTabEnum saleTabEnum = OmsOrderAfterSaleTabEnum.getEnum(tabValue);
        switch (saleTabEnum) {
            case ALL:
                break;
            case UN_HANDLE:
                afterStatusList = Lists.newArrayList(
                        OmsOrderAfterSaleStatusEnum.APPLY.getStatus(),
                        OmsOrderAfterSaleStatusEnum.APPLY_REFUND.getStatus()
                );
                break;
            case HANDLE_ING:
                afterStatusList = Lists.newArrayList(
                        OmsOrderAfterSaleStatusEnum.AGREE_APPLY.getStatus(),
                        OmsOrderAfterSaleStatusEnum.REFUND_ING.getStatus()
                );
                break;
            case CLOSE:
                afterStatusList = Lists.newArrayList(
                        OmsOrderAfterSaleStatusEnum.REVOKE.getStatus(),
                        OmsOrderAfterSaleStatusEnum.CLOSE.getStatus()
                );
                break;
            case OK:
                afterStatusList = Lists.newArrayList(
                        OmsOrderAfterSaleStatusEnum.AGREE_REFUND.getStatus(),
                        OmsOrderAfterSaleStatusEnum.FINISH.getStatus()
                );
                break;
            case FAIL:
                afterStatusList = Lists.newArrayList(
                        OmsOrderAfterSaleStatusEnum.REJECT_APPLY.getStatus(),
                        OmsOrderAfterSaleStatusEnum.REJECT_REFUND.getStatus()
                );
                break;
            default:
                break;
        }
        return afterStatusList;
    }

}
