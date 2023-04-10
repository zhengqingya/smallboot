package com.zhengqing.pay.service;

import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.zhengqing.pay.model.dto.PayOrderCreateDTO;
import com.zhengqing.pay.model.dto.PayOrderQueryDTO;
import com.zhengqing.pay.model.dto.PayOrderRefundDTO;

/**
 * <p>
 * 支付 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/1/1 21:45
 */
public interface IPayService {

    /**
     * 查询订单
     *
     * @param params 提交参数
     * @return 订单详情
     * @author zhengqingya
     * @date 2021/1/1 21:45
     */
    WxPayOrderQueryResult queryOrder(PayOrderQueryDTO params);

    /**
     * 退款
     *
     * @param params 提交参数
     * @return true->成功，false->失败
     * @author zhengqingya
     * @date 2021/1/1 21:45
     */
    Boolean refund(PayOrderRefundDTO params);


    /**
     * 原生的统一下单接口
     *
     * @param params 提交参数
     * @return 结果
     * @author zhengqingya
     * @date 2021/1/1 21:45
     */
    WxPayUnifiedOrderResult unifiedOrder(PayOrderCreateDTO params);

}
