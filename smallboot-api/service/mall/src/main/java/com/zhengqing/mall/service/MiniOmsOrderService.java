package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.common.model.vo.MallTabConditionListVO;
import com.zhengqing.mall.entity.OmsOrder;
import com.zhengqing.mall.mini.model.dto.*;
import com.zhengqing.mall.mini.model.vo.MiniOmsOrderDetailVO;
import com.zhengqing.mall.mini.model.vo.MiniOmsOrderPageVO;
import com.zhengqing.mall.mini.model.vo.MiniOmsSpuBuyVO;
import com.zhengqing.mall.mini.model.vo.MiniPmsOrderReAfterSaleStatusVO;

import java.util.List;

/**
 * <p>  商城-订单信息 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:40
 */
public interface MiniOmsOrderService extends OmsOrderService<OmsOrder> {

    /**
     * 购买商品-创建订单
     *
     * @param params 提交参数
     * @return 微信所需支付参数
     * @author zhengqingya
     * @date 2021/6/25 14:51
     */
    MiniOmsSpuBuyVO createOrder(MiniOmsSpuBuyDTO params);

    /**
     * 订单支付成功-回调处理
     *
     * @param payOrderNotifyBO 支付回调信息
     * @return void
     * @author zhengqingya
     * @date 2021/6/25 14:02
     */
//    void paySuccessCallback(PayOrderNotifyBO payOrderNotifyBO);

    /**
     * 获取tab条件
     *
     * @param params 查询参数
     * @return tab条件
     * @author zhengqingya
     * @date 2021/8/26 15:45
     */
    List<MallTabConditionListVO> getTabCondition(MiniOmsOrderPageDTO params);

    /**
     * 分页列表
     *
     * @param params 提交参数
     * @return 订单信息
     * @author zhengqingya
     * @date 2021/10/18 11:46
     */
    IPage<MiniOmsOrderPageVO> page(MiniOmsOrderPageDTO params);

    /**
     * 详情
     *
     * @param orderNo 订单号
     * @return 详情数据
     * @author zhengqingya
     * @date 2021/10/21 11:06
     */
    MiniOmsOrderDetailVO detailByMini(String orderNo);

    /**
     * 订单未支付处理
     *
     * @param params 订单信息
     * @return void
     * @author zhengqingya
     * @date 2021/6/25 14:02
     */
    void unPayCallback(MiniOmsOrderUnPayDTO params);

    /**
     * 待支付订单-支付
     *
     * @param params 提交参数
     * @return 微信所需支付参数
     * @author zhengqingya
     * @date 2021/10/19 9:46
     */
//    WxPayUnifiedOrderResult payOrder(MiniOmsOrderPayDTO params);

    /**
     * 修改收货人地址
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/10/19 11:16
     */
    void updateReceiverAddress(MiniOmsOrderUpdateReceiverAddressDTO params);


    /**
     * 售后状态(判断是否可申请售后)
     *
     * @param orderNo 订单号
     * @return 售后状态信息
     * @author zhengqingya
     * @date 2021/12/10 14:19
     */
    MiniPmsOrderReAfterSaleStatusVO getAfterSaleStatus(String orderNo);

    /**
     * 申请售后（退款/退货/退货退款）
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/10/19 11:52
     */
    void applyAfterSale(MiniOmsOrderApplyAfterSaleDTO params);

}
