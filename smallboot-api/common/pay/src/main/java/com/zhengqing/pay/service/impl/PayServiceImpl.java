package com.zhengqing.pay.service.impl;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.pay.factory.WxPayFactory;
import com.zhengqing.pay.model.dto.PayOrderCreateDTO;
import com.zhengqing.pay.model.dto.PayOrderQueryDTO;
import com.zhengqing.pay.model.dto.PayOrderRefundDTO;
import com.zhengqing.pay.model.vo.PayOrderCreateVO;
import com.zhengqing.pay.service.IPayService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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

    //    private final WxPayService wxPayService;
    private final WxPayFactory wxPayFactory;

    @Value("${wx.pay.notifyHost:http://127.0.0.1}")
    private String notifyHost;

    @Override
    @SneakyThrows(Exception.class)
    public WxPayOrderQueryResult queryOrder(PayOrderQueryDTO params) {
        return this.wxPayFactory.wxPayService().queryOrder(null, params.getOrderNo());
    }

    @Override
    @SneakyThrows(Exception.class)
    public Boolean refund(PayOrderRefundDTO params) {
        WxPayRefundResult wxPayRefundResult = this.wxPayFactory.wxPayService().refund(
                WxPayRefundRequest.newBuilder()
                        .outTradeNo(params.getOrderNo())
                        .outRefundNo(params.getRefundOrderNo())
                        .totalFee(params.getTotalPrice())
                        .refundFee(params.getRefundPrice())
                        .refundDesc(params.getRefundDesc())
                        .notifyUrl(this.notifyHost + "/wx/callback/notify/refund/" + params.getTenantId())
                        .build()
        );

        if ("SUCCESS".equals(wxPayRefundResult.getResultCode())) {
            return true;
        }

        throw new MyException(wxPayRefundResult.getErrCodeDes());
    }

    @Override
    @SneakyThrows(Exception.class)
    public PayOrderCreateVO unifiedOrder(PayOrderCreateDTO params) {
        WxPayService wxPayService = this.wxPayFactory.wxPayService();

        // 1、创建订单
        WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(
                WxPayUnifiedOrderRequest.newBuilder()
                        .outTradeNo(params.getOrderNo())
                        .openid(params.getOpenId())
                        .totalFee(params.getTotalPrice())
                        .body(params.getOrderDesc())
                        .tradeType("JSAPI")
                        .spbillCreateIp(NetUtil.getLocalhostStr())
                        .notifyUrl(this.notifyHost + "/wx/callback/notify/order/" + params.getTenantId())
                        .build()
        );

        // 2、返回小程序需要的支付参数
        // 小程序调起支付 https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=5
        String nonceStr = RandomUtil.randomString(20);
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String wxPackage = "prepay_id=" + wxPayUnifiedOrderResult.getPrepayId();
        String signType = "MD5";
        String paySign = SignUtils.createSign(new HashMap<String, String>(5) {
            {
                this.put("appId", wxPayUnifiedOrderResult.getAppid());
                this.put("timeStamp", timeStamp);
                this.put("nonceStr", nonceStr);
                this.put("package", wxPackage);
                this.put("signType", signType);
            }
        }, "MD5", wxPayService.getConfig().getMchKey(), null);

        return PayOrderCreateVO.builder()
                .nonceStr(nonceStr)
                .wxPackage(wxPackage)
                .timeStamp(timeStamp)
                .signType(signType)
                .paySign(paySign)
                .build();
    }

}
