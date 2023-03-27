package com.zhengqing.pay.feign;

import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.pay.model.dto.PayOrderCreateDTO;
import com.zhengqing.pay.model.dto.PayOrderQueryDTO;
import com.zhengqing.pay.model.dto.PayOrderRefundDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>
 * 支付 Feign接口类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/1 21:45
 */
//@FeignClient(value = RpcConstant.RPC_PAY,
//        path = RpcConstant.RPC_API_PREFIX_PAY + "/wx",
//        contextId = "IPayClient",
//        fallback = IPayClientFallback.class)
public interface IPayClient {

    /**
     * 查询订单
     *
     * @param params 提交参数
     * @return 订单详情
     * @author zhengqingya
     * @date 2021/1/1 21:45
     */
    @ApiOperation(value = "查询订单")
    @GetMapping("queryOrder")
    WxPayOrderQueryResult queryOrder(@ModelAttribute PayOrderQueryDTO params);

    /**
     * 退款
     *
     * @param params 提交参数
     * @return true->成功，false->失败
     * @author zhengqingya
     * @date 2021/1/1 21:45
     */
    @ApiOperation(value = "退款")
    @PostMapping("refund")
    ApiResult<Boolean> refund(@ModelAttribute PayOrderRefundDTO params);

    @ApiOperation(value = "原生的统一下单接口")
    @PostMapping("unifiedOrder")
    WxPayUnifiedOrderResult unifiedOrder(@ModelAttribute PayOrderCreateDTO params);

}
