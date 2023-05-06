package com.zhengqing.pay.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.zhengqing.common.base.constant.BaseConstant;
import com.zhengqing.pay.factory.WxPayFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * <p> wx-回调 接口 </p>
 *
 * @author zhengqingya
 * @description 记得返回值放行处理 {@link BaseConstant#RETURN_VALUE_HANDLER_EXCLUDE_API_LIST}
 * @date 2021/08/17 15:33
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wx/callback")
@Api(tags = {"wx-回调"})
public class WxCallbackController {

    private final WxPayFactory wxPayFactory;


    @SneakyThrows(Exception.class)
    @ApiOperation("支付回调通知处理")
    @PostMapping("/notify/order/{tenantId}")
    public String uploadFile(@PathVariable Integer tenantId, @RequestBody String xmlData) {
        final WxPayOrderNotifyResult wxPayOrderNotifyResult = this.wxPayFactory.wxPayService().parseOrderNotifyResult(xmlData);
        log.info("支付回调通知处理：{}", wxPayOrderNotifyResult);
        // TODO 根据自己业务场景需要构造返回对象
        return WxPayNotifyResponse.success("成功");
    }

    @ApiOperation(value = "退款回调通知处理")
    @PostMapping("/notify/refund/{tenantId}")
    public String parseRefundNotifyResult(@PathVariable Integer tenantId, @RequestBody String xmlData) throws WxPayException {
        final WxPayRefundNotifyResult wxPayRefundNotifyResult = this.wxPayFactory.wxPayService().parseRefundNotifyResult(xmlData);
        log.info("退款回调通知处理：{}", wxPayRefundNotifyResult);
        // TODO 根据自己业务场景需要构造返回对象
        return WxPayNotifyResponse.success("成功");
    }

}
