package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.mall.constant.MallAppConstant;
import com.zhengqing.mall.constant.MallRabbitMqConstant;
import com.zhengqing.mall.entity.OmsOrderAfterSale;
import com.zhengqing.mall.enums.MallResultCodeEnum;
import com.zhengqing.mall.enums.MallTabEnum;
import com.zhengqing.mall.enums.OmsOrderAfterSaleStatusEnum;
import com.zhengqing.mall.enums.OmsOrderSaleTypeEnum;
import com.zhengqing.mall.mapper.OmsOrderAfterSaleMapper;
import com.zhengqing.mall.model.bo.OmsOrderAfterSaleCloseBO;
import com.zhengqing.mall.model.bo.PmsSkuStockBO;
import com.zhengqing.mall.model.dto.*;
import com.zhengqing.mall.model.vo.*;
import com.zhengqing.mall.service.*;
import com.zhengqing.pay.model.bo.PayOrderNotifyBO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 商城-售后表 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OmsOrderAfterSaleServiceImpl extends ServiceImpl<OmsOrderAfterSaleMapper, OmsOrderAfterSale> implements IOmsOrderAfterSaleService {

    private final OmsOrderAfterSaleMapper omsOrderAfterSaleMapper;
    private final IOmsLogisticService iOmsLogisticService;
    @Lazy
    @Resource
    private IOmsOrderItemService iOmsOrderItemService;
    private final IMallCommonService iMallCommonService;
    private final RabbitTemplate rabbitTemplate;

//    private final IPayClient payClient;

    private final IPmsSpuService iPmsSpuService;


    @Override
    public OmsOrderAfterSale detailBase(String afterSaleNo) {
        OmsOrderAfterSale detailData = this.omsOrderAfterSaleMapper.selectById(afterSaleNo);
        Assert.notNull(detailData, "该售后数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addData(OmsOrderAfterSale omsOrderAfterSale) {
        String afterSaleNo = IdGeneratorUtil.nextStrId();
        omsOrderAfterSale.setAfterSaleNo(afterSaleNo);
        omsOrderAfterSale.insert();
        this.handleLogisticsData(omsOrderAfterSale);
        return afterSaleNo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateData(OmsOrderAfterSale omsOrderAfterSale) {
        Assert.isTrue(omsOrderAfterSale.updateById(), "该售后数据不存在！");
        this.handleLogisticsData(omsOrderAfterSale);
        return omsOrderAfterSale.getAfterSaleNo();
    }

    /**
     * 处理物流数据
     *
     * @param omsOrderAfterSale 售后数据
     * @return void
     * @author zhengqingya
     * @date 2021/10/26 18:15
     */
    private void handleLogisticsData(OmsOrderAfterSale omsOrderAfterSale) {
        String returnLogisticsCode = omsOrderAfterSale.getReturnLogisticsCode();
        String returnLogisticsNo = omsOrderAfterSale.getReturnLogisticsNo();
        String receiverPhone = omsOrderAfterSale.getReceiverPhone();
        if (StringUtils.isBlank(returnLogisticsNo)) {
            // 如果没有物流号直接退出，不处理
            return;
        }
        // 如果没有收货人电话号码则查下库数据
        if (StringUtils.isBlank(receiverPhone)) {
            OmsOrderAfterSale afterSale = this.getById(omsOrderAfterSale.getAfterSaleNo());
            receiverPhone = afterSale.getReceiverPhone();
        }
        this.iOmsLogisticService.saveLogistic(returnLogisticsCode, returnLogisticsNo, receiverPhone);
    }

    @Override
    public OmsOrderAfterSale getOrderAfterSale(String afterSaleNo) {
        OmsOrderAfterSale detailData = this.omsOrderAfterSaleMapper.selectById(afterSaleNo);
        Assert.notNull(detailData, "该售后数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(OmsOrderAfterSaleDeleteDTO params) {
        log.info("[商城] 售后-删除-提交参数：[{}] ", params);
        List<String> afterSaleNoList = params.getAfterSaleNoList();
        this.omsOrderAfterSaleMapper.deleteBatchIds(afterSaleNoList);
    }

    @Override
    public Map<String, Boolean> mapByOrderNoList(List<String> orderNoList) {
        if (CollectionUtils.isEmpty(orderNoList)) {
            return Maps.newHashMap();
        }
        List<OmsOrderAfterSaleVO> afterSaleList = this.omsOrderAfterSaleMapper.selectListByOrderNoList(orderNoList);
        return afterSaleList.stream().collect(Collectors.toMap(OmsOrderAfterSaleVO::getOrderNo, OmsOrderAfterSaleVO::getIsAfterSale, (k1, k2) -> k1));
    }

    @Override
    public List<String> orderItemIdsForAfterSale(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return Lists.newLinkedList();
        }
        return this.mapOrderItemIdsForAfterSale(Lists.newArrayList(orderNo)).get(orderNo);
    }

    @Override
    public Map<String, List<String>> mapOrderItemIdsForAfterSale(List<String> orderNoList) {
        Map<String, List<String>> resultMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(orderNoList)) {
            return resultMap;
        }
        List<OmsOrderAfterSaleItemVO> afterSaleList = this.omsOrderAfterSaleMapper.selectOrderItemIdsReAfterSaleStatus(orderNoList);
        if (CollectionUtils.isEmpty(afterSaleList)) {
            return resultMap;
        }
        for (OmsOrderAfterSaleItemVO item : afterSaleList) {
            resultMap.computeIfAbsent(item.getOrderNo(), k -> new LinkedList<>()).add(item.getOrderItemId());
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unHandleAutoClose(OmsOrderAfterSaleCloseBO params) {
        log.info("[商城] 买家/卖家未处理-自动关闭售后-回调消息: [{}]", params);
        String afterSaleNo = params.getAfterSaleNo();
        Boolean isBuyer = params.getIsBuyer();
        OmsOrderAfterSale orderAfterSale = this.getOrderAfterSale(afterSaleNo);
        if (isBuyer) {
            if (!OmsOrderAfterSaleStatusEnum.AGREE_APPLY.getStatus().equals(orderAfterSale.getAfterStatus())) {
                log.info("[商城] 买家未处理-自动关闭售后-回调消息: [{}] 订单状态已变更 无需自动变更", params);
                return;
            }
        } else {
            if (!(OmsOrderAfterSaleStatusEnum.APPLY.getStatus().equals(orderAfterSale.getAfterStatus())
                    || OmsOrderAfterSaleStatusEnum.APPLY_REFUND.getStatus().equals(orderAfterSale.getAfterStatus()))) {
                log.info("[商城] 卖家未处理-自动关闭售后-回调消息: [{}] 订单状态已变更 无需自动变更", params);
                return;
            }
        }
        orderAfterSale.setAfterStatus(OmsOrderAfterSaleStatusEnum.CLOSE.getStatus());
        orderAfterSale.setCloseTime(new Date());
        orderAfterSale.setCloseRemark(String.format("%s未及时处理，系统自动关闭！", isBuyer ? "买家" : "卖家"));
        orderAfterSale.updateById();
    }

    @Override
    public int getHandleIngNum(Long userId) {
        return this.omsOrderAfterSaleMapper.selectHandleIngNum(userId);
    }

    @Override
    public List<String> getNoApplyReOrderItemIdListByOrderNo(String orderNo) {
        return this.omsOrderAfterSaleMapper.selectNoApplyReOrderItemIdListByOrderNo(orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void repeal(MiniOmsOrderRepealAfterSaleDTO params) {
        log.info("[商城] 售后-撤销-提交参数：[{}] ", params);
        String afterSaleNo = params.getAfterSaleNo();
        // 1、查询之前售后状态并进行校验
        OmsOrderAfterSale orderAfterSale = this.getOrderAfterSale(afterSaleNo);
        OmsOrderAfterSaleStatusEnum afterStatusOldEnum = OmsOrderAfterSaleStatusEnum.getEnum(orderAfterSale.getAfterStatus());

        // 校验数据
        OmsOrderSaleTypeEnum orderSaleTypeEnum = OmsOrderSaleTypeEnum.getEnum(orderAfterSale.getAfterType());
        switch (orderSaleTypeEnum) {
            case REFUND:
                Assert.isTrue(OmsOrderAfterSaleStatusEnum.APPLY_REFUND == afterStatusOldEnum,
                        String.format("不支持撤销操作，当前订单状态为：%s", afterStatusOldEnum.getDesc()));
                break;
            case SALE_RETURN_AND_REFUND:
                Assert.isTrue(OmsOrderAfterSaleStatusEnum.APPLY == afterStatusOldEnum
                                || OmsOrderAfterSaleStatusEnum.AGREE_APPLY == afterStatusOldEnum
                                || OmsOrderAfterSaleStatusEnum.APPLY_REFUND == afterStatusOldEnum,
                        String.format("不支持撤销操作，当前订单状态为：%s", afterStatusOldEnum.getDesc()));
                break;
            default:
                break;
        }

        // 2、更新状态
        this.updateData(OmsOrderAfterSale.builder()
                .afterSaleNo(afterSaleNo)
                .afterStatus(OmsOrderAfterSaleStatusEnum.REVOKE.getStatus())
                .build());
    }

    @Override
    public List<MallTabConditionListVO> getTabCondition(OmsOrderAfterSalePageDTO params) {
        params.setAfterStatusList(null);
        // 查询tab条件数量
        List<MallTabConditionListVO> tabDataList = this.omsOrderAfterSaleMapper.selectTabCondition(params);
        List<MallTabConditionListVO> tabResultList = this.iMallCommonService.getTabDataList(tabDataList, MallTabEnum.MALL_ORDER_AFTER_SALE_TAB_CONDITION);
        // 售后订单tab值需单独计算，因为一个tab对应多个状态值
        Map<Byte, Integer> tabDataMap = tabDataList.stream().collect(Collectors.toMap(MallTabConditionListVO::getValue, MallTabConditionListVO::getNum, (k1, k2) -> k1));
        for (MallTabConditionListVO item : tabResultList) {
            Byte value = item.getValue();
            // -1：全部
            if (value == -1) {
                continue;
            }
            Integer num = 0;
            // 根据tab值获取售后订单状态
            List<Byte> afterStatusList = params.getAfterStatusList(value);
            for (Byte afterStatusItem : afterStatusList) {
                Integer numItem = tabDataMap.get(afterStatusItem);
                if (numItem == null) {
                    numItem = 0;
                }
                num += numItem;
            }
            item.setNum(num);
        }
        return tabResultList;
    }


    @Override
    public IPage<OmsOrderAfterSaleBaseVO> page(OmsOrderAfterSalePageDTO params) {
        // 订单主体详情
        IPage<OmsOrderAfterSaleBaseVO> afterSalePage = this.omsOrderAfterSaleMapper.selectDataList(new Page<>(), params);
        List<OmsOrderAfterSaleBaseVO> afterSaleList = afterSalePage.getRecords();
        if (CollectionUtils.isEmpty(afterSaleList)) {
            return afterSalePage;
        }
        // 售后关联商品ids
        List<String> orderItemIdList = Lists.newLinkedList();
        afterSaleList.forEach(item -> {
            item.handleOrderItemIdList();
            orderItemIdList.addAll(item.getOrderItemIdList());
        });
        // 查询关联商品数据
        List<OmsOrderItemVO> orderItemList = this.iOmsOrderItemService.listInfo(OmsOrderItemDTO.builder().orderItemIdList(orderItemIdList).build());
        Map<String, OmsOrderItemVO> orderItemMap = orderItemList.stream().collect(Collectors.toMap(OmsOrderItemVO::getId, t -> t, (k1, k2) -> k1));
        afterSaleList.forEach(item -> {
            List<OmsOrderItemVO> spuList = Lists.newLinkedList();
            List<String> orderItemIdListItem = item.getOrderItemIdList();
            orderItemIdListItem.forEach(orderItemId -> spuList.add(orderItemMap.get(orderItemId)));
            item.setSpuList(spuList);
            item.handleData();
        });
        return afterSalePage;
    }

    @Override
    public OmsOrderAfterSaleBaseVO detail(String afterSaleNo) {
        return this.page(OmsOrderAfterSalePageDTO.builder().afterSaleNo(afterSaleNo).build()).getRecords().get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateDataByWeb(WebOmsOrderAfterSaleUpdateDTO params) {
        log.info("[商城] 售后更新-提交参数: [{}]", params);
        Byte afterStatus = params.getAfterStatus();
        OmsOrderAfterSaleStatusEnum orderAfterSaleStatusEnum = OmsOrderAfterSaleStatusEnum.getEnum(afterStatus);
        String afterSaleNo = params.getAfterSaleNo();
        OmsOrderAfterSale omsOrderAfterSale = this.getOrderAfterSale(afterSaleNo);
        Byte afterStatusOld = omsOrderAfterSale.getAfterStatus();
        Assert.isTrue(OmsOrderAfterSaleStatusEnum.APPLY.getStatus().equals(afterStatusOld)
                || OmsOrderAfterSaleStatusEnum.APPLY_REFUND.getStatus().equals(afterStatusOld), MallResultCodeEnum.售后状态已变更.getDesc());

        // 1、售后自动关闭时间
        long autoCloseAfterMillisecond = MallAppConstant.AUTO_CLOSE_AFTER_MILLISECOND;

        // 2、退款处理
        if (OmsOrderAfterSaleStatusEnum.AGREE_REFUND == orderAfterSaleStatusEnum) {
//            ApiResult<Boolean> refundWrapper = this.payClient.refund(
//                    PayOrderRefundDTO.builder()
//                            .tenantId(omsOrderAfterSale.getTenantId())
//                            .orderNo(omsOrderAfterSale.getOrderNo())
//                            .totalPrice(omsOrderAfterSale.getPayPrice())
//                            .refundPrice(omsOrderAfterSale.getRefundPrice())
//                            .refundDesc(omsOrderAfterSale.getAfterReason())
//                            .build()
//            );
//            if (refundWrapper.checkIsFail()) {
//                log.error("[商城] 退款失败:{}", refundWrapper.getMsg());
//                throw new MyException("退款失败：" + refundWrapper.getMsg());
//            }
        }

        // 3、更新售后数据
        Date nowTime = new Date();
        OmsOrderAfterSale afterSaleSave = OmsOrderAfterSale.builder()
                .afterSaleNo(afterSaleNo)
                .afterStatus(OmsOrderAfterSaleStatusEnum.AGREE_REFUND == orderAfterSaleStatusEnum
                        ? OmsOrderAfterSaleStatusEnum.REFUND_ING.getStatus() : orderAfterSaleStatusEnum.getStatus())
                .handlerId(params.getHandlerId())
                .handlerName(params.getHandlerName())
                .buyerAutoCloseTime(new Date(System.currentTimeMillis() + autoCloseAfterMillisecond))
                .build();

        switch (orderAfterSaleStatusEnum) {
            case AGREE_APPLY:
            case REJECT_APPLY:
                afterSaleSave.setHandlerResultForApply(params.getHandlerResultForApply());
                afterSaleSave.setHandlerTimeForApply(nowTime);
                afterSaleSave.setHandlerRemarkForApply(params.getHandlerRemarkForApply());
                break;
            case AGREE_REFUND:
            case REJECT_REFUND:
                afterSaleSave.setHandlerResultForRefund(params.getHandlerResultForRefund());
                afterSaleSave.setHandlerTimeForRefund(nowTime);
                afterSaleSave.setHandlerRemarkForRefund(params.getHandlerRemarkForRefund());
                break;
            default:
                break;
        }
        this.updateData(afterSaleSave);

        // 是否为退货退款
        boolean isReturnAndRefund = OmsOrderSaleTypeEnum.SALE_RETURN_AND_REFUND.getType().equals(params.getAfterType());
        if (OmsOrderAfterSaleStatusEnum.AGREE_APPLY == orderAfterSaleStatusEnum && isReturnAndRefund) {
            // 4、mq延时-待买家发货(买家申请售后，卖家同意后，买家未填写退货返回物流单号)？毫秒后自动关闭
            this.rabbitTemplate.convertAndSend(MallRabbitMqConstant.MALL_EVENT_DELAY_EXCHANGE,
                    MallRabbitMqConstant.OMS_ORDER_AFTER_SALE_BUYER_DELIVER_ROUTING_KEY,
                    OmsOrderAfterSaleCloseBO.builder()
                            .afterSaleNo(afterSaleNo)
                            .tenantId(TenantIdContext.getTenantId())
                            .isBuyer(true)
                            .build(), message -> {
                        // 配置消息延时时间
                        message.getMessageProperties().setHeader("x-delay", autoCloseAfterMillisecond);
                        return message;
                    });
        }
        return afterSaleNo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refundSuccessCallback(PayOrderNotifyBO payOrderNotifyBO) {
        log.info("[商城] 售后-退款成功-回调消息: [{}]", JSONUtil.toJsonStr(payOrderNotifyBO));
        if (!payOrderNotifyBO.getResultStatus()) {
            log.warn("[商城] 售后-退款回调异常不处理 -- 回调参数: [{}]", JSON.toJSONString(payOrderNotifyBO));
            return;
        }
        // 系统内部订单编号
//        String orderNo = String.valueOf(payOrderNotify.getOrderNo());
        // 售后订单号
        String afterSaleNo = String.valueOf(payOrderNotifyBO.getOutRefundNo());
        // 1、查询售后订单详情
        OmsOrderAfterSale orderAfterSale = this.getOrderAfterSale(afterSaleNo);
        Byte afterStatus = orderAfterSale.getAfterStatus();
        if (!OmsOrderAfterSaleStatusEnum.REFUND_ING.getStatus().equals(afterStatus)) {
            log.warn("[商城] 售后-退款成功-售后订单号: [{}] 售后状态值已变更为：[{}]", afterSaleNo, afterStatus);
            return;
        }
        // 2、状态更新-退款成功
        orderAfterSale.setAfterStatus(OmsOrderAfterSaleStatusEnum.FINISH.getStatus());
        orderAfterSale.updateById();

        // 4、普通商品库存返回
        // 查询售后关联商品数据
        List<PmsSkuStockBO> pmsSkuStockList = this.omsOrderAfterSaleMapper.selectSpuListByAfterSaleNo(afterSaleNo);
        this.iPmsSpuService.updateSkuStock(pmsSkuStockList);
    }


}
