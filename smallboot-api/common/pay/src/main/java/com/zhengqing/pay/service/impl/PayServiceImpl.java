package com.zhengqing.pay.service.impl;

import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.pay.model.dto.PayOrderCreateDTO;
import com.zhengqing.pay.model.dto.PayOrderQueryDTO;
import com.zhengqing.pay.model.dto.PayOrderRefundDTO;
import com.zhengqing.pay.service.IPayService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/1 21:45
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayServiceImpl implements IPayService {

    private final WxPayService wxPayService;


    @Override
    @SneakyThrows(Exception.class)
    public WxPayOrderQueryResult queryOrder(PayOrderQueryDTO params) {
        return this.wxPayService.queryOrder(null, params.getOrderNo());
    }

    @Override
    @SneakyThrows(Exception.class)
    public Boolean refund(PayOrderRefundDTO params) {
        WxPayRefundResult wxPayRefundResult = this.wxPayService.refund(
                WxPayRefundRequest.newBuilder()
                        .outTradeNo(params.getOrderNo())
                        .outRefundNo(params.getRefundOrderNo())
                        .totalFee(params.getTotalPrice())
                        .refundFee(params.getRefundPrice())
                        .refundDesc(params.getRefundDesc())
                        .notifyUrl("/wx/callback/notify/refund/" + params.getTenantId())
                        .build()
        );

        if ("SUCCESS".equals(wxPayRefundResult.getResultCode())) {
            return true;
        }

        throw new MyException(wxPayRefundResult.getErrCodeDes());
    }

    @Override
    @SneakyThrows(Exception.class)
    public WxPayUnifiedOrderResult unifiedOrder(PayOrderCreateDTO params) {
        WxPayUnifiedOrderResult wxPayUnifiedOrderResult = this.wxPayService.unifiedOrder(
                WxPayUnifiedOrderRequest.newBuilder()
                        .outTradeNo(params.getOrderNo())
                        .openid(params.getOpenId())
                        .totalFee(params.getTotalPrice())
                        .body(params.getOrderDesc())
                        .notifyUrl("/wx/callback/notify/order/" + params.getTenantId())
                        .build()
        );
        return wxPayUnifiedOrderResult;
    }
    
}
