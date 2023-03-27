package com.zhengqing.mall.web.model.vo;

import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.mall.common.model.enums.OmsOrderStockCheckTypeEnum;
import com.zhengqing.system.model.vo.SysDictVO;
import com.zhengqing.system.model.vo.SysPropertyVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <p>
 * 商城-订单设置-参数
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
@EqualsAndHashCode(callSuper = true)
@ApiModel("web-商城-订单设置-参数")
public class WebOmsOrderSetVO extends BaseVO {

    @ApiModelProperty("发货微信消息通知")
    private List<SysDictVO> wxMsgList;

    @ApiModelProperty("订单设置")
    private List<SysPropertyVO> setList;

    /**
     * {@link OmsOrderStockCheckTypeEnum}
     */
    @ApiModelProperty("减库存设置（1：提交订单减库存 2：付款减库存）")
    private Byte payType;

}
