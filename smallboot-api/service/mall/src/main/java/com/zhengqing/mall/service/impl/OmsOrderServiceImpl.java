package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.mall.model.bo.PmsSkuStockBO;
import com.zhengqing.mall.model.dto.OmsOrderCancelDTO;
import com.zhengqing.mall.model.dto.OmsOrderDeleteDTO;
import com.zhengqing.mall.model.enums.OmsOrderItemStatusEnum;
import com.zhengqing.mall.model.enums.OmsOrderStatusEnum;
import com.zhengqing.mall.model.enums.OmsOrderStockCheckTypeEnum;
import com.zhengqing.mall.model.vo.OmsOrderItemVO;
import com.zhengqing.mall.constant.MallAppConstant;
import com.zhengqing.mall.entity.OmsOrder;
import com.zhengqing.mall.entity.OmsOrderShipping;
import com.zhengqing.mall.entity.OmsOrderShippingItem;
import com.zhengqing.mall.entity.PmsSpu;
import com.zhengqing.mall.mapper.OmsOrderMapper;
import com.zhengqing.mall.model.dto.MiniOmsOrderConfirmReceiptDTO;
import com.zhengqing.mall.service.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p> 商城-订单信息 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:40
 */
@Slf4j
@Service
public class OmsOrderServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService<OmsOrder> {

    @Resource
    private OmsOrderMapper omsOrderMapper;


    @Resource
    @Qualifier("omsOrderShippingServiceImpl")
    private OmsOrderShippingService<OmsOrderShipping> omsOrderShippingService;

    @Resource
    @Qualifier("omsOrderShippingItemServiceImpl")
    private OmsOrderShippingItemService<OmsOrderShippingItem> omsOrderShippingItemService;

    @Resource
    @Qualifier("omsOrderItemServiceImpl")
    private OmsOrderItemService<OmsOrderItemVO> omsOrderItemService;

    @Resource
    @Qualifier("pmsSpuServiceImpl")
    private PmsSpuService<PmsSpu> pmsSpuService;

//    @Resource
//    private IPayClient payClient;

    @Override
    public OmsOrder getOrder(String orderNo) {
        OmsOrder detailData = this.omsOrderMapper.selectOrder(orderNo);
        Assert.notNull(detailData, "订单:" + orderNo + " 不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(String orderNo) {
        this.deleteBatch(OmsOrderDeleteDTO.builder().orderNoList(Lists.newArrayList(orderNo)).build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addData(OmsOrder omsOrder) {
        String orderNo = omsOrder.getOrderNo();
        if (StringUtils.isBlank(orderNo)) {
            orderNo = IdGeneratorUtil.nextStrId();
        }
        omsOrder.setOrderNo(orderNo);
        omsOrder.insert();
        return orderNo;
    }

    /**
     * 取消订单
     *
     * @param params 提交参数
     * @param isMq   true->mq处理  false->普通业务处理
     * @return void
     * @author zhengqingya
     * @date 2022/3/9 11:04
     */
    @SneakyThrows(Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrderBase(OmsOrderCancelDTO params, boolean isMq) {
        log.info("[商城] 订单-取消-提交参数：[{}] ", params);
        String orderNo = params.getOrderNo();
        // 1、数据校验
        // 1.1、订单状态校验
        OmsOrder order = this.getOrder(orderNo);
        Byte orderStatusOld = order.getOrderStatus();
        OmsOrderStatusEnum orderStatusEnumOld = OmsOrderStatusEnum.getEnum(orderStatusOld);
        if (!OmsOrderStatusEnum.UN_PAY.getStatus().equals(orderStatusOld)) {
            String errorMsg = String.format("订单号：[%s] 订单状态：[%s] 无权限取消该订单！", orderNo, orderStatusEnumOld.getDesc());
            if (isMq) {
                log.warn(errorMsg);
                return;
            } else {
                throw new MyException(errorMsg);
            }
        }

        // 1.2、查询该订单是否进行微信支付
//        WxPayOrderQueryResult wxPayOrderQueryResult = this.payClient.queryOrder(
//                PayOrderQueryDTO.builder()
//                        .tenantId(TenantIdContext.getTenantId())
//                        .orderNo(orderNo)
//                        .build()
//        );
//        if (wxPayOrderQueryResult != null
//                && !Lists.newArrayList("NOTPAY", "CLOSED", "PAYERROR").contains(wxPayOrderQueryResult.getTradeState())) {
//            // 已支付处理
//            log.warn("[商城] 订单:[{}] 取消时查询微信支付异常：[{}]", orderNo, JSON.toJSONString(wxPayOrderQueryResult));
//            return;
//        }

        // 2、更新订单状态
        order.setOrderStatus(OmsOrderStatusEnum.CANC.getStatus());
        order.setOrderCloseTime(new Date());
        order.updateById();
        // 3、更新商品状态
        this.omsOrderItemService.updateBatchStatusByOrderNo(orderNo, OmsOrderItemStatusEnum.CANC);
        // 4、返回库存
        Byte stockCheckType = order.getStockCheckType();
        if (OmsOrderStockCheckTypeEnum.CREATE_ORDER.getType().equals(stockCheckType)) {
            // 查询该订单关联商品
            List<OmsOrderItemVO> orderReSpuList = this.omsOrderItemService.listByOrderNo(orderNo);
            List<PmsSkuStockBO> skuStockList = Lists.newLinkedList();
            orderReSpuList.forEach(item -> skuStockList.add(PmsSkuStockBO.builder()
                    .skuId(item.getSkuId())
                    .num(item.getNum())
                    .build()));
            this.pmsSpuService.updateSkuStock(skuStockList);
        }
    }

    @Override
    public void cancelOrderForBusiness(OmsOrderCancelDTO params) {
        this.cancelOrderBase(params, false);
    }

    @Override
    public void cancelOrderForMq(OmsOrderCancelDTO params) {
        this.cancelOrderBase(params, true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(OmsOrderDeleteDTO params) {
        log.info("[商城] 订单-删除-提交参数：[{}] ", params);
        List<String> orderNoList = params.getOrderNoList();
        this.omsOrderMapper.deleteBatchOrder(orderNoList);
    }

    @Override
    public void confirmReceipt(MiniOmsOrderConfirmReceiptDTO params) {
        log.info("[商城] 订单-确认收货-提交参数：[{}] ", params);
        String orderNo = params.getOrderNo();
        String shippingId = params.getShippingId();

        // 1、查询订单
        OmsOrder order = this.getOrder(orderNo);
        if (OmsOrderStatusEnum.FINISH.getStatus().equals(order.getOrderStatus())) {
            log.warn("[商城] 订单-确认收货-提交参数：[{}] 已完成，无需处理！", params);
            return;
        }
        // 获取确认收货后-售后处理可申请处理时间
        long buyerApplyAfterSaleMillisecond = MallAppConstant.BUYER_APPLY_AFTER_SALE_MILLISECOND;

        // 2、更新订单状态
        order.setOrderStatus(OmsOrderStatusEnum.FINISH.getStatus());
        order.setOrderEndTime(new Date());
        order.setAfterSaleEndTime(new Date(System.currentTimeMillis() + buyerApplyAfterSaleMillisecond));
        order.updateById();

        // 判断是否属于纯虚拟商品的订单
        if (StringUtils.isBlank(shippingId)) {
            // 3、该订单为纯虚拟商品 -- 更新订单下的所有商品状态
            this.omsOrderItemService.updateBatchStatusByOrderNo(orderNo, OmsOrderItemStatusEnum.FINISH);
        } else {
            // 3、更新商品状态
            // 先获取关联商品ids
            List<String> orderItemIdList = this.omsOrderShippingItemService.listForOrderItemId(orderNo, shippingId);
            this.omsOrderItemService.updateBatchStatus(orderItemIdList, OmsOrderItemStatusEnum.FINISH);

            // 4、更新配送信息
            this.omsOrderShippingService.updateData(OmsOrderShipping.builder()
                    .id(shippingId)
                    .receiptTime(new Date())
                    .build());
        }
    }

}
