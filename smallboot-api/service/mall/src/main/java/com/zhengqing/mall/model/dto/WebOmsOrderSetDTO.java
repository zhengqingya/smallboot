package com.zhengqing.mall.model.dto;

import com.zhengqing.common.core.custom.parameter.CheckParam;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.mall.model.enums.OmsOrderStockCheckTypeEnum;
import com.zhengqing.system.enums.SysPropertyKeyEnum;
import com.zhengqing.system.model.dto.SysDictSaveBatchDTO;
import com.zhengqing.system.model.dto.SysPropertySaveDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 商城-订单设置-提交参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/9/24 14:28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("web-商城-订单设置-提交参数")
public class WebOmsOrderSetDTO implements CheckParam {

    @Valid
    @NotEmpty(message = "发货微信消息通知不能为空！")
    @ApiModelProperty("发货微信消息通知")
    private ValidList<SysDictSaveBatchDTO> wxMsgList;

    @Valid
    @NotEmpty(message = "订单设置数据不能为空！")
    @ApiModelProperty("订单设置")
    private ValidList<SysPropertySaveDTO> setList;

    /**
     * {@link OmsOrderStockCheckTypeEnum}
     */
    @NotNull(message = "减库存设置不能为空！")
    @ApiModelProperty(value = "减库存设置（1：提交订单减库存 2：付款减库存）", required = true, example = "1")
    private Byte payType;

    @Override
    public void checkParam() {
        this.setList.add(SysPropertySaveDTO.builder()
                .key(SysPropertyKeyEnum.MALL_ORDER_SET_STOCK_CHECK_TYPE.getKey())
                .value(String.valueOf(this.payType))
                .remark(SysPropertyKeyEnum.MALL_ORDER_SET_STOCK_CHECK_TYPE.getDesc())
                .build());
    }

}
