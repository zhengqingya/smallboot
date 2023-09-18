package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.entity.OmsOrder;
import com.zhengqing.mall.model.dto.*;
import com.zhengqing.mall.model.vo.*;
import com.zhengqing.pay.model.bo.PayOrderNotifyBO;
import com.zhengqing.pay.model.vo.PayOrderCreateVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>  商城-订单信息 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:40
 */
public interface IOmsOrderService extends IService<OmsOrder> {

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
    void paySuccessCallback(PayOrderNotifyBO payOrderNotifyBO);

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
    PayOrderCreateVO payOrder(MiniOmsOrderPayDTO params);


    /**
     * 待支付订单-支付 (仅测试环境使用，订单直接变成已支付状态流程)
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/10/19 9:46
     */
    void payOrderTest(MiniOmsOrderPayDTO params);

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

    // web ------------------------------------------

    /**
     * 获取订单设置数据
     *
     * @return 订单设置数据
     * @author zhengqingya
     * @date 2021/9/24 14:29
     */
//    WebOmsOrderSetVO getOrderSet();

    /**
     * 更新订单设置数据
     *
     * @param params 提交数据
     * @return void
     * @author zhengqingya
     * @date 2021/9/24 14:30
     */
//    void updateOrderSet(@Validated @RequestBody WebOmsOrderSetDTO params);

    /**
     * 获取tab条件
     *
     * @param params 查询参数
     * @return tab条件
     * @author zhengqingya
     * @date 2021/8/26 15:45
     */
    List<MallTabConditionListVO> getTabCondition(WebOmsOrderPageDTO params);

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/30 13:40
     */
    IPage<WebOmsOrderPageVO> page(WebOmsOrderPageDTO params);

    /**
     * 查询订单
     *
     * @param orderNo 订单编号
     * @return 详情
     * @author zhengqingya
     * @date 2021/08/30 13:40
     */
    WebOmsOrderDetailVO detail(String orderNo);

    /**
     * 订单发货
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/10/21 9:25
     */
    void sendSpu(WebOmsOrderSendSpuDTO params);

    /**
     * 导出
     *
     * @param response 响应
     * @param params   请求参数
     * @return void
     * @author zhengqingya
     * @date 2021/7/6 17:53
     */
    void export(HttpServletResponse response, WebOmsOrderPageDTO params);

    /**
     * 批量导入发货
     *
     * @param file 提交文件
     * @return void
     * @author zhengqingya
     * @date 2022/1/25 10:10
     */
    void importBatchSendSpu(MultipartFile file);
    
}
