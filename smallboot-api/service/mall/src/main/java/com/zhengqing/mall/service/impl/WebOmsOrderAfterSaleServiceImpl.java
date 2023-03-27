package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.mall.common.model.bo.OmsOrderAfterSaleCloseBO;
import com.zhengqing.mall.common.model.dto.OmsOrderItemDTO;
import com.zhengqing.mall.common.model.enums.MallResultCodeEnum;
import com.zhengqing.mall.common.model.enums.OmsOrderAfterSaleStatusEnum;
import com.zhengqing.mall.common.model.enums.OmsOrderSaleTypeEnum;
import com.zhengqing.mall.common.model.vo.MallTabConditionListVO;
import com.zhengqing.mall.common.model.vo.OmsOrderItemVO;
import com.zhengqing.mall.constant.MallAppConstant;
import com.zhengqing.mall.constant.MallRabbitMqConstant;
import com.zhengqing.mall.entity.OmsOrderAfterSale;
import com.zhengqing.mall.mapper.OmsOrderAfterSaleMapper;
import com.zhengqing.mall.service.MallCommonService;
import com.zhengqing.mall.service.WebOmsOrderAfterSaleService;
import com.zhengqing.mall.service.WebOmsOrderItemService;
import com.zhengqing.mall.service.WebPmsSpuService;
import com.zhengqing.mall.web.model.dto.WebOmsOrderAfterSalePageDTO;
import com.zhengqing.mall.web.model.dto.WebOmsOrderAfterSaleUpdateDTO;
import com.zhengqing.mall.web.model.vo.WebOmsOrderAfterSaleDetailVO;
import com.zhengqing.mall.web.model.vo.WebOmsOrderAfterSalePageVO;
import com.zhengqing.system.enums.SysDictTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
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
public class WebOmsOrderAfterSaleServiceImpl extends OmsOrderAfterSaleServiceImpl<OmsOrderAfterSaleMapper, OmsOrderAfterSale> implements WebOmsOrderAfterSaleService {

    @Resource
    private OmsOrderAfterSaleMapper omsOrderAfterSaleMapper;

    @Resource
    private WebOmsOrderItemService webOmsOrderItemService;

    @Resource
    private MallCommonService mallCommonService;

    @Resource
    private RabbitTemplate rabbitTemplate;

//    @Resource
//    private IPayClient payClient;

    @Lazy
    @Resource
    private WebPmsSpuService webPmsSpuService;

    @Override
    public List<MallTabConditionListVO> getTabCondition(WebOmsOrderAfterSalePageDTO params) {
        params.setAfterStatusList(null);
        // 查询tab条件数量
        List<MallTabConditionListVO> tabDataList = this.omsOrderAfterSaleMapper.selectTabCondition(params);
        List<MallTabConditionListVO> tabResultList = this.mallCommonService.getTabDataList(tabDataList, SysDictTypeEnum.MALL_ORDER_AFTER_SALE_TAB_CONDITION);
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
    public IPage<WebOmsOrderAfterSalePageVO> page(WebOmsOrderAfterSalePageDTO params) {
        IPage<WebOmsOrderAfterSalePageVO> afterSalePage = this.omsOrderAfterSaleMapper.selectDataListByWeb(new Page<>(), params);
        List<WebOmsOrderAfterSalePageVO> afterSaleList = afterSalePage.getRecords();
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
        List<OmsOrderItemVO> orderItemList = this.webOmsOrderItemService.listInfo(OmsOrderItemDTO.builder().orderItemIdList(orderItemIdList).build());
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
    public WebOmsOrderAfterSaleDetailVO detailByWeb(String afterSaleNo) {
        // 订单主体详情
        WebOmsOrderAfterSaleDetailVO orderAfterSaleDetailVO = this.omsOrderAfterSaleMapper.detailByWeb(afterSaleNo);
        Assert.notNull(orderAfterSaleDetailVO, "该售后数据不存在！");
        // 处理数据
        orderAfterSaleDetailVO.handleData();
        // 查询关联商品数据
        List<OmsOrderItemVO> orderItemList = this.webOmsOrderItemService.listInfo(
                OmsOrderItemDTO.builder().orderItemIdList(orderAfterSaleDetailVO.getOrderItemIdList()).build());
        orderAfterSaleDetailVO.setSpuList(orderItemList);
        return orderAfterSaleDetailVO;
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
        super.updateData(afterSaleSave);

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

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void refundSuccessCallback(PayOrderNotifyBO payOrderNotifyBO) {
//        log.info("[商城] 售后-退款成功-回调消息: [{}]", JSON.toJSONString(payOrderNotifyBO));
//        if (!payOrderNotifyBO.getResultStatus()) {
//            log.warn("[商城] 售后-退款回调异常不处理 -- 回调参数: [{}]", JSON.toJSONString(payOrderNotifyBO));
//            return;
//        }
//        // 系统内部订单编号
////        String orderNo = String.valueOf(payOrderNotify.getOrderNo());
//        // 售后订单号
//        String afterSaleNo = String.valueOf(payOrderNotifyBO.getOutRefundNo());
//        // 1、查询售后订单详情
//        OmsOrderAfterSale orderAfterSale = this.getOrderAfterSale(afterSaleNo);
//        Byte afterStatus = orderAfterSale.getAfterStatus();
//        if (!OmsOrderAfterSaleStatusEnum.REFUND_ING.getStatus().equals(afterStatus)) {
//            log.warn("[商城] 售后-退款成功-售后订单号: [{}] 售后状态值已变更为：[{}]", afterSaleNo, afterStatus);
//            return;
//        }
//        // 2、状态更新-退款成功
//        orderAfterSale.setAfterStatus(OmsOrderAfterSaleStatusEnum.FINISH.getStatus());
//        orderAfterSale.updateById();
//
//        // 4、普通商品库存返回
//        // 查询售后关联商品数据
//        List<PmsSkuStockBO> pmsSkuStockList = this.omsOrderAfterSaleMapper.selectSpuListByAfterSaleNo(afterSaleNo);
//        this.webPmsSpuService.updateSkuStock(pmsSkuStockList);
//    }

}
