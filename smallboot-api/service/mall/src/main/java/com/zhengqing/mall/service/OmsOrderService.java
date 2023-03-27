package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.common.model.dto.OmsOrderCancelDTO;
import com.zhengqing.mall.common.model.dto.OmsOrderDeleteDTO;
import com.zhengqing.mall.entity.OmsOrder;
import com.zhengqing.mall.mini.model.dto.MiniOmsOrderConfirmReceiptDTO;

/**
 * <p>  商城-订单信息 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:40
 */
public interface OmsOrderService<T> extends IService<OmsOrder> {

    /**
     * 查询订单&检查订单是否存在
     *
     * @param orderNo 订单编号
     * @return 详情
     * @author zhengqingya
     * @date 2021/08/30 13:40
     */
    OmsOrder getOrder(String orderNo);

    /**
     * 删除数据
     *
     * @param orderNo 订单号
     * @return void
     * @author zhengqingya
     * @date 2021/08/30 13:40
     */
    void deleteData(String orderNo);

    /**
     * 新增
     *
     * @param omsOrder 保存参数
     * @return 主键id
     * @author zhengqingya
     * @date 2021/08/30 13:40
     */
    String addData(OmsOrder omsOrder);

    /**
     * 待支付订单-取消订单
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/6/25 11:30
     */
    void cancelOrderForBusiness(OmsOrderCancelDTO params);

    /**
     * 待支付订单-取消订单
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/6/25 11:30
     */
    void cancelOrderForMq(OmsOrderCancelDTO params);

    /**
     * 批量删除
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:05
     */
    void deleteBatch(OmsOrderDeleteDTO params);

    /**
     * 确认收货
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/10/20 10:44 下午
     */
    void confirmReceipt(MiniOmsOrderConfirmReceiptDTO params);

}
