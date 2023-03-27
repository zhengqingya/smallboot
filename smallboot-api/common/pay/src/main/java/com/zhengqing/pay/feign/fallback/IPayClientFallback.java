package com.zhengqing.pay.feign.fallback;

import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.pay.feign.IPayClient;
import com.zhengqing.pay.model.dto.PayOrderCreateDTO;
import com.zhengqing.pay.model.dto.PayOrderQueryDTO;
import com.zhengqing.pay.model.dto.PayOrderRefundDTO;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Feign错误处理
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/4 11:53
 */
@Component
public class IPayClientFallback implements IPayClient {

    @Override
    public WxPayOrderQueryResult queryOrder(PayOrderQueryDTO params) {
        return null;
    }

    @Override
    public ApiResult<Boolean> refund(PayOrderRefundDTO params) {
        return null;
    }

    @Override
    public WxPayUnifiedOrderResult unifiedOrder(PayOrderCreateDTO params) {
        return null;
    }

}
