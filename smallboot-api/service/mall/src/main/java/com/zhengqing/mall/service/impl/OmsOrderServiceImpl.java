package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.context.UmsUserContext;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.BigDecimalUtil;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.common.excel.util.EasyExcelUtil;
import com.zhengqing.common.web.util.MyValidatorUtil;
import com.zhengqing.mall.constant.MallAppConstant;
import com.zhengqing.mall.constant.MallRabbitMqConstant;
import com.zhengqing.mall.entity.*;
import com.zhengqing.mall.mapper.OmsOrderMapper;
import com.zhengqing.mall.model.bo.*;
import com.zhengqing.mall.model.dto.*;
import com.zhengqing.mall.model.enums.*;
import com.zhengqing.mall.model.vo.*;
import com.zhengqing.mall.service.*;
import com.zhengqing.pay.model.bo.PayOrderNotifyBO;
import com.zhengqing.pay.model.dto.PayOrderCreateDTO;
import com.zhengqing.pay.model.vo.PayOrderCreateVO;
import com.zhengqing.pay.service.IPayService;
import com.zhengqing.system.enums.SysDictTypeEnum;
import com.zhengqing.ums.model.vo.UmsUserVO;
import com.zhengqing.ums.service.IUmsUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p> 商城-订单信息 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:40
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements IOmsOrderService {

    private final OmsOrderMapper omsOrderMapper;
    private final IPmsSpuService iPmsSpuService;

    //    private final IPayClient payClient;
    private final IMallCommonService iMallCommonService;
    private final IOmsOrderItemService iOmsOrderItemService;
    private final IPayService iPayService;
    private final RabbitTemplate rabbitTemplate;
    private final IOmsOrderAfterSaleService iOmsOrderAfterSaleService;
    private final IOmsOrderAfterSaleItemService iOmsOrderAfterSaleItemService;
    private final IOmsOrderDeliverService iOmsOrderDeliverService;
    private final IUmsUserService iUmsUserService;

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
        this.iOmsOrderItemService.updateBatchStatusByOrderNo(orderNo, OmsOrderItemStatusEnum.CANC);
        // 4、返回库存
        Byte stockCheckType = order.getStockCheckType();
        if (OmsOrderStockCheckTypeEnum.CREATE_ORDER.getType().equals(stockCheckType)) {
            // 查询该订单关联商品
            List<OmsOrderItemVO> orderReSpuList = this.iOmsOrderItemService.listByOrderNo(orderNo);
            List<PmsSkuStockBO> skuStockList = Lists.newLinkedList();
            orderReSpuList.forEach(item -> skuStockList.add(PmsSkuStockBO.builder()
                    .skuId(item.getSkuId())
                    .num(item.getNum())
                    .build()));
            this.iPmsSpuService.updateSkuStock(skuStockList);
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
        String deliverId = params.getDeliverId();

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

        // 3、更新商品状态
        this.iOmsOrderItemService.updateBatchStatusByOrderNo(orderNo, OmsOrderItemStatusEnum.FINISH);

        // 判断是否属于纯虚拟商品的订单
        if (StrUtil.isNotBlank(deliverId)) {
            // 4、更新配送信息
            this.iOmsOrderDeliverService.updateData(OmsOrderDeliver.builder()
                    .id(deliverId)
                    .receiptTime(new Date())
                    .build());
        }
    }

    @Override
    @SneakyThrows(Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public MiniOmsSpuBuyVO createOrder(MiniOmsSpuBuyDTO params) {
        log.info("[商城] 订单购买提交参数：{}", JSONUtil.toJsonStr(params));
        List<MiniPmsSpuBuySkuDTO> spuList = params.getSkuList();
        Integer freight = params.getFreight();
        Integer totalPrice = params.getTotalPrice();
        Integer payPrice = params.getPayPrice();
        Long userId = UmsUserContext.getUserId();
//        String wxOpenid = params.getWxOpenid();
        String orderRemark = params.getOrderRemark();
        String receiverName = params.getReceiverName();
        String receiverPhone = params.getReceiverPhone();
        String receiverAddress = params.getReceiverAddress();
        Integer tenantId = TenantIdContext.getTenantId();

        // ==================================== ↓↓↓↓↓↓ 1、校验数据 ↓↓↓↓↓↓ ====================================
        // 商品规格ids
        List<String> skuIdList = spuList.stream()
                .map(MiniPmsSpuBuySkuDTO::getSkuId).collect(Collectors.toList());
        // 查询商品sku数据
        List<PmsSkuVO> mysqlSkuList = this.iPmsSpuService.listBySku(
                PmsSkuDTO.builder()
                        .skuIdList(skuIdList)
                        .build());
        Assert.isTrue(!CollectionUtils.isEmpty(mysqlSkuList), "购买商品已失效！");
        Map<String, PmsSkuVO> mysqlSkuMap = mysqlSkuList.stream()
                .collect(Collectors.toMap(PmsSkuVO::getSkuId, t -> t, (k1, k2) -> k1));
        // 求出这些商品中的最大运费，然后与提交运费值对比校验
        Integer mysqlMaxFreight = mysqlSkuList.stream().map(PmsSkuVO::getFreight)
                .max(Integer::compareTo).get();
        Assert.isTrue(mysqlMaxFreight.equals(freight),
                String.format("商品运费变更 程序计算值：%s 提交值：%s", mysqlMaxFreight, freight));
        // 商品总价 TODO 暂未考虑使用优惠券的情况！！！
        Integer mysqlSumPrice = 0;
        // 校验商品是否可以购买
        Date nowTime = new Date();
        for (MiniPmsSpuBuySkuDTO spuItem : spuList) {
            String skuId = spuItem.getSkuId();
            Integer num = spuItem.getNum();
            Integer price = spuItem.getPrice();
            PmsSkuVO mysqlSkuVO = mysqlSkuMap.get(skuId);
            String mysqlSkuName = mysqlSkuVO.getName();
            Integer mysqlSkuPrice = mysqlSkuVO.getPrice();
            Assert.notNull(mysqlSkuVO, String.format("商品[%s]失效", mysqlSkuName));
            Assert.isTrue(mysqlSkuPrice.equals(price),
                    String.format("商品[%s]价格变更 程序计算值：%s 提交值：%s", mysqlSkuName, mysqlSkuPrice, price));
            Assert.isTrue(mysqlSkuVO.getLimitCount() == 0
                    || mysqlSkuVO.getLimitCount() >= num, MallResultCodeEnum.商品限购.getDesc());
            Assert.isTrue(mysqlSkuVO.getUsableStock() - num >= 0,
                    MallResultCodeEnum.商品库存不足.getDesc());
            if (mysqlSkuVO.getIsPresell()) {
                // 预售时间之后才能购买
                Assert.isTrue(nowTime.after(mysqlSkuVO.getPresellStartTime()), MallResultCodeEnum.商品预售之前无法购买.getDesc());
            }
            // 计算商品总价
            mysqlSumPrice += mysqlSkuPrice * num;
        }
        Assert.isTrue(mysqlSumPrice.equals(totalPrice),
                String.format("商品总价变更 程序计算值：%s 提交值：%s", mysqlSumPrice, totalPrice));
        Assert.isTrue(mysqlSumPrice + mysqlMaxFreight == payPrice,
                String.format("总支付价变更 程序计算值：%s 提交值：%s", mysqlSumPrice + mysqlMaxFreight, payPrice));

        // 校验此人历史是否限购
        Map<String, Integer> mysqlSkuHistoryLimitMap = this.iOmsOrderItemService.mapSkuLimit(userId, skuIdList);
        List<PmsSpuBuyNumInfoBO> skuBuyInfoList = Lists.newLinkedList();
        spuList.forEach(spuItem ->
                skuBuyInfoList.add(PmsSpuBuyNumInfoBO.builder()
                        .skuId(spuItem.getSkuId())
                        .num(spuItem.getNum())
                        .build()));
        this.iMallCommonService.checkSkuLimit(skuBuyInfoList, mysqlSkuMap, mysqlSkuHistoryLimitMap);

        // ==================================== ↓↓↓↓↓↓ 2、正常下单逻辑 ↓↓↓↓↓↓ ====================================
        // 获取用户信息
        UmsUserVO userInfo = this.iUmsUserService.getUser(userId);
        String wxOpenid = userInfo.getOpenid();

        // 计算配送方式
        OmsOrderDeliverTypeEnum deliverTypeEnum = OmsOrderDeliverTypeEnum.NULL;
        for (PmsSkuVO item : mysqlSkuList) {
            if (OmsOrderDeliverTypeEnum.EXPRESS == item.getDeliverTypeEnum()) {
                deliverTypeEnum = OmsOrderDeliverTypeEnum.EXPRESS;
                break;
            }
        }

        // 订单待支付时间倒计时 5分钟
        Integer unpayMillisecond = 60 * 1000 * 5;

        // 生成订单号
        final String orderNo = IdGeneratorUtil.nextStrId();

        // 创建订单
        this.addData(OmsOrder.builder()
                .orderNo(orderNo)
                .wxOpenid(wxOpenid)
                .userId(userId)
                .username(userInfo.getNickname())
                .userPhone(userInfo.getPhone())
                .userSex(userInfo.getSex())
                .totalPrice(totalPrice)
                .payPrice(payPrice)
                .freight(freight)
                .orderStatus(OmsOrderStatusEnum.UN_PAY.getStatus())
                .orderSource(OmsOrderSourceEnum.WX.getSource())
                .orderRemark(orderRemark)
                .deliverType(deliverTypeEnum.getType())
                .receiverName(receiverName)
                .receiverPhone(receiverPhone)
                .receiverAddress(receiverAddress)
                .unPayEndTime(MyDateUtil.addTime(TimeUnit.MINUTES, unpayMillisecond / (1000 * 60)))
                .stockCheckType(OmsOrderStockCheckTypeEnum.PAY.getType())
                .build());

        // 保存订单商品详情
        List<OmsOrderItem> omsOrderItemList = Lists.newLinkedList();
        spuList.forEach(spuItem -> {
            String skuId = spuItem.getSkuId();
            Integer num = spuItem.getNum();
            PmsSkuVO mysqlSkuVO = mysqlSkuMap.get(skuId);
            Integer unitPrice = mysqlSkuVO.getPrice();
            omsOrderItemList.add(OmsOrderItem.builder()
                    .id(IdGeneratorUtil.nextStrId())
                    .orderNo(orderNo)
                    .userId(userId)
                    .spuId(mysqlSkuVO.getSpuId())
                    .skuId(mysqlSkuVO.getSkuId())
                    .name(mysqlSkuVO.getName())
                    .coverImg(mysqlSkuVO.getCoverImg())
                    .specList(mysqlSkuVO.getSpecList())
                    .num(num)
                    .price(unitPrice)
                    .totalPrice(unitPrice * num)
                    .type(mysqlSkuVO.getType())
                    .status(OmsOrderItemStatusEnum.UN_PAY.getStatus())
                    .build());
        });
        this.iOmsOrderItemService.addBatchData(omsOrderItemList);

        // ==================================== ↓↓↓↓↓↓ 3、其它操作 ↓↓↓↓↓↓ ====================================
        this.rabbitTemplate.convertAndSend(MallRabbitMqConstant.MALL_EVENT_DELAY_EXCHANGE,
                MallRabbitMqConstant.OMS_ORDER_UN_PAY_AUTO_CLOSE_ROUTING_KEY,
                MiniOmsOrderUnPayDTO.builder().orderNo(orderNo).tenantId(tenantId).build(),
                message -> {
                    // 配置消息延时时间
                    message.getMessageProperties().setHeader("x-delay", unpayMillisecond);
                    return message;
                });
        return MiniOmsSpuBuyVO.builder()
                .orderNo(orderNo)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void paySuccessCallback(PayOrderNotifyBO payOrderNotifyBO) {
        log.info("[商城] 支付成功回调消息: [{}]", JSON.toJSONString(payOrderNotifyBO));
        if (!payOrderNotifyBO.getResultStatus()) {
            log.warn("[商城] 支付回调异常不处理 -- 回调参数: [{}]", JSON.toJSONString(payOrderNotifyBO));
            return;
        }
        // 系统内部订单编号
        String orderNo = payOrderNotifyBO.getOrderNo();
        // 微信支付订单号
        String payNo = payOrderNotifyBO.getTransactionId();
        // 查询订单详情
        OmsOrder order = this.getOrder(orderNo);
        // 是否为普通商品
        Byte orderStatus = order.getOrderStatus();
        if (!OmsOrderStatusEnum.UN_PAY.getStatus().equals(orderStatus)
                && !OmsOrderStatusEnum.CANC.getStatus().equals(orderStatus)
        ) {
            log.warn("[商城] 支付成功回调消息: [{}] 不处理业务 该订单状态已变更为[{}]", payOrderNotifyBO, orderStatus);
            return;
        }

        boolean isVirtual = false;
        List<OmsOrderItemVO> orderItemList = Lists.newLinkedList();
        // 订单关联商品详情 => 如果商品都为虚拟商品-则需要将此订单变更为待收货状态
        orderItemList = this.iOmsOrderItemService.listByOrderNo(orderNo);
        for (OmsOrderItemVO orderItem : orderItemList) {
            isVirtual = PmsSpuTypeEnum.isVirtual(orderItem.getType());
            if (!isVirtual) {
                break;
            }
        }
        if (isVirtual) {
            order.setAutoReceiptTime(this.iMallCommonService.getAutoReceiptTime());
        }
        // 订单状态更新
        order.setOrderStatus(isVirtual ? OmsOrderStatusEnum.BILL.getStatus()
                : OmsOrderStatusEnum.UN_BILL.getStatus());

        // 1、订单更新
        order.setPayNo(payNo);
        order.setPayTime(new Date());
//        order.setUnPayEndTime(null);
        order.updateById();

        // 2、更新商品状态
        this.iOmsOrderItemService.updateBatchStatusByOrderNo(orderNo,
                isVirtual ? OmsOrderItemStatusEnum.BILL : OmsOrderItemStatusEnum.UN_BILL);

        // 3、mq延时-自动确认收货
        if (isVirtual) {
            // 查询自动收货时间
            long autoReceiptMillisecond = this.iMallCommonService.getAutoReceiptMillisecond();
            this.rabbitTemplate.convertAndSend(MallRabbitMqConstant.MALL_EVENT_DELAY_EXCHANGE,
                    MallRabbitMqConstant.OMS_ORDER_AUTO_CONFIRM_RECEIPT_ROUTING_KEY,
                    MiniOmsOrderConfirmReceiptDTO.builder()
                            .orderNo(orderNo)
                            .tenantId(TenantIdContext.getTenantId())
                            .build(), message -> {
                        // 配置消息延时时间
                        message.getMessageProperties().setHeader("x-delay", autoReceiptMillisecond);
                        return message;
                    });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unPayCallback(MiniOmsOrderUnPayDTO params) {
        String orderNo = params.getOrderNo();
        log.info("[商城] 订单号：[{}] 在有效时间内未支付，自动取消", orderNo);
        this.cancelOrderForMq(OmsOrderCancelDTO.builder()
                .orderNo(orderNo)
                .build());
    }

    @Override
    @SneakyThrows({Exception.class})
    @Transactional(rollbackFor = Exception.class)
    public PayOrderCreateVO payOrder(MiniOmsOrderPayDTO params) {
        log.info("[商城] 订单-支付-提交参数：[{}] ", params);
        String orderNo = params.getOrderNo();
        OmsOrder order = this.getOrder(orderNo);
        OmsOrderStatusEnum orderStatusEnum = OmsOrderStatusEnum.getEnum(order.getOrderStatus());
        Assert.isTrue(OmsOrderStatusEnum.UN_PAY == orderStatusEnum,
                "无法支付，该订单状态为：" + orderStatusEnum.getDesc());
        // 1、支付是否校验库存
        if (OmsOrderStockCheckTypeEnum.PAY.getType().equals(order.getStockCheckType())) {
            // 1.1、查询该订单关联商品
            List<OmsOrderItemVO> orderReSpuList = this.iOmsOrderItemService.listByOrderNo(
                    orderNo);
            List<PmsSkuStockBO> skuStockList = Lists.newLinkedList();
            orderReSpuList.forEach(item -> skuStockList.add(PmsSkuStockBO.builder()
                    .skuId(item.getSkuId())
                    .num(-item.getNum())
                    .build()));
            // 1.2、库存扣减
            Assert.isTrue(this.iPmsSpuService.updateSkuStock(skuStockList), "商品库存不足!");
        }

        // 2、创建微信支付订单
        PayOrderCreateVO payOrderCreateVO = this.iPayService.unifiedOrder(
                PayOrderCreateDTO.builder()
                        .tenantId(TenantIdContext.getTenantId())
                        .orderNo(orderNo)
                        .totalPrice(order.getPayPrice())
                        .orderDesc("商品")
                        .openId(order.getWxOpenid())
                        .build()
        );
        return payOrderCreateVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrderTest(MiniOmsOrderPayDTO params) {
        log.info("[商城] 订单-支付（仅测试使用）-提交参数：[{}] ", params);
        String orderNo = params.getOrderNo();
        OmsOrder order = this.getOrder(orderNo);
        OmsOrderStatusEnum orderStatusEnum = OmsOrderStatusEnum.getEnum(order.getOrderStatus());
        Assert.isTrue(OmsOrderStatusEnum.UN_PAY == orderStatusEnum,
                "无法支付，该订单状态为：" + orderStatusEnum.getDesc());
        // 1、支付是否校验库存
        if (OmsOrderStockCheckTypeEnum.PAY.getType().equals(order.getStockCheckType())) {
            // 1.1、查询该订单关联商品
            List<OmsOrderItemVO> orderReSpuList = this.iOmsOrderItemService.listByOrderNo(
                    orderNo);
            List<PmsSkuStockBO> skuStockList = Lists.newLinkedList();
            orderReSpuList.forEach(item -> skuStockList.add(PmsSkuStockBO.builder()
                    .skuId(item.getSkuId())
                    .num(-item.getNum())
                    .build()));
            // 1.2、库存扣减
            Assert.isTrue(this.iPmsSpuService.updateSkuStock(skuStockList), "商品库存不足!");
        }

        // 2、状态变更
        this.paySuccessCallback(
                PayOrderNotifyBO.builder()
                        .tenantId(TenantIdContext.getTenantId())
                        .transactionId(IdGeneratorUtil.nextStrId())
                        .orderNo(orderNo)
                        .outRefundNo(null)
                        .resultStatus(true)
                        .build()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReceiverAddress(MiniOmsOrderUpdateReceiverAddressDTO params) {
        log.info("[商城] 订单-修改收货人地址-提交参数：[{}] ", params);
        String orderNo = params.getOrderNo();
        String receiverName = params.getReceiverName();
        String receiverPhone = params.getReceiverPhone();
        String receiverAddress = params.getReceiverAddress();
        OmsOrder order = this.getOrder(orderNo);
        Byte orderStatus = order.getOrderStatus();
        // 1、查询订单关联商品信息
        List<OmsOrderItemVO> orderReSpuList = this.iOmsOrderItemService.listByOrderNo(
                orderNo);
        int orderReSpuSize = orderReSpuList.size();
        Map<Byte, List<OmsOrderItemVO>> groupOrderReSpuListByStatus = orderReSpuList.stream()
                .collect(Collectors.groupingBy(OmsOrderItemVO::getStatus));
        // 未发货商品信息
        List<OmsOrderItemVO> omsOrderItemList = groupOrderReSpuListByStatus.get(
                OmsOrderStatusEnum.UN_BILL.getStatus());
        // 2、未支付或全部商品未发货才能修改地址
        if (OmsOrderStatusEnum.UN_PAY.getStatus().equals(orderStatus)
                || (orderReSpuSize > 0 && !CollectionUtils.isEmpty(omsOrderItemList)
                && omsOrderItemList.size() == orderReSpuSize)
        ) {
            order.setReceiverName(receiverName);
            order.setReceiverPhone(receiverPhone);
            order.setReceiverAddress(receiverAddress);
            order.updateById();
        } else {
            throw new MyException("您暂无权限修改收货人地址(只有未支付状态或全部商品未发货状态才能修改地址!)");
        }
    }

    @Override
    public MiniPmsOrderReAfterSaleStatusVO getAfterSaleStatus(String orderNo) {
        // 1、订单-售后可处理截止时间
        OmsOrder order = this.getOrder(orderNo);
        Date afterSaleEndTime = order.getAfterSaleEndTime();
        // 2、订单关联商品详情
        List<OmsOrderItemVO> orderItemList = this.iOmsOrderItemService.listByOrderNo(orderNo);
        // 3、不可申请售后的订单详情ids
        List<String> noApplyReOrderItemIdList = this.iOmsOrderAfterSaleService.getNoApplyReOrderItemIdListByOrderNo(
                orderNo);
        // 4、封装每一个商品时候可售后状态
        List<MiniOmsReSpuAfterSaleStatusVO> spuList = Lists.newArrayList();
        orderItemList.forEach(orderItem -> spuList.add(
                MiniOmsReSpuAfterSaleStatusVO.builder()
                        .orderItemId(orderItem.getId())
                        .name(orderItem.getName())
                        .isAfterSale(!noApplyReOrderItemIdList.contains(orderItem.getId()))
                        .build()));
        // 5、返回结果
        return MiniPmsOrderReAfterSaleStatusVO.builder()
                .afterSaleEndTime(afterSaleEndTime)
                .spuList(spuList)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applyAfterSale(MiniOmsOrderApplyAfterSaleDTO params) {
        log.info("[商城] 订单-申请售后-提交参数：[{}] ", params);
        Long userId = params.getUserId();
        Byte afterType = params.getAfterType();
        Byte afterStatus = params.getAfterStatus();
        String orderNo = params.getOrderNo();
        List<String> orderItemIdList = params.getOrderItemIdList();
        String refundReason = params.getReason();
        String explain = params.getExplain();
        Integer refundPrice = params.getRefundPrice();

        // 1、校验是否可申请售后
        OmsOrder order = this.getOrder(orderNo);
        Byte orderStatus = order.getOrderStatus();
        if (!(OmsOrderStatusEnum.UN_BILL.getStatus().equals(orderStatus)
                || OmsOrderStatusEnum.BILL.getStatus().equals(orderStatus)
                || OmsOrderStatusEnum.FINISH.getStatus().equals(orderStatus)
        )) {
            log.error("[商城] 申请售后 订单编号：{} 订单状态：{} 不满足申请售后条件！", orderNo, orderStatus);
            throw new MyException(MallResultCodeEnum.不满足申请售后条件.getDesc());
        }
        Date afterSaleEndTime = order.getAfterSaleEndTime();
        if (afterSaleEndTime != null) {
            Assert.isTrue(afterSaleEndTime.after(new Date()), String.format("申请售后结束时间：%s",
                    MyDateUtil.dateToStr(afterSaleEndTime, MyDateUtil.DATE_TIME_FORMAT)));
        }

        // 2、校验退款金额是否合法
        Map<String, OmsOrderItemVO> orderItemMap = this.iOmsOrderItemService.mapSkuBaseInfo(
                orderItemIdList);
        Integer payPrice = order.getPayPrice();
        Assert.isTrue(payPrice >= refundPrice,
                String.format("退款金额[%s]不能超过订单实付总金额[%s]", refundPrice, payPrice));

        // 售后自动关闭时间
        long autoCloseAfterMillisecond = MallAppConstant.AUTO_CLOSE_AFTER_MILLISECOND;

        // 3、保存售后数据
        String afterSaleNo = this.iOmsOrderAfterSaleService.addData(OmsOrderAfterSale.builder()
                .orderNo(orderNo)
                .userId(userId)
                .username(order.getUsername())
                .userPhone(order.getUserPhone())
                .afterType(afterType)
                .afterStatus(afterStatus)
                .afterReason(refundReason)
                .afterExplain(explain)
                .payPrice(payPrice)
                .freight(order.getFreight())
                .applyRefundPrice(refundPrice)
                .refundPrice(refundPrice)
                .certImgList(params.getCertImgList())
                .applyTime(new Date())
                .receiverName(order.getReceiverName())
                .receiverPhone(order.getReceiverPhone())
                .receiverAddress(order.getReceiverAddress())
                .sellerAutoCloseTime(
                        new Date(System.currentTimeMillis() + autoCloseAfterMillisecond))
                .build());

        // 4、保存售后详情数据
        List<OmsOrderAfterSaleItem> omsOrderAfterSaleItemSaveList = Lists.newArrayList();
        for (OmsOrderItemVO orderItemBO : orderItemMap.values()) {
            omsOrderAfterSaleItemSaveList.add(OmsOrderAfterSaleItem.builder()
                    .afterSaleNo(afterSaleNo)
                    .orderNo(orderNo)
                    .orderItemId(orderItemBO.getId())
                    .build());
        }
        this.iOmsOrderAfterSaleItemService.addOrUpdateBatchData(omsOrderAfterSaleItemSaveList);

        // 5、mq延时-买家发起售后申请？毫秒后，卖家未处理，自动关闭
        this.rabbitTemplate.convertAndSend(MallRabbitMqConstant.MALL_EVENT_DELAY_EXCHANGE,
                MallRabbitMqConstant.OMS_ORDER_APPLY_AFTER_SALE_HANDLE_ROUTING_KEY,
                OmsOrderAfterSaleCloseBO.builder()
                        .afterSaleNo(afterSaleNo)
                        .tenantId(TenantIdContext.getTenantId())
                        .isBuyer(false)
                        .build(), message -> {
                    // 配置消息延时时间
                    message.getMessageProperties().setHeader("x-delay", autoCloseAfterMillisecond);
                    return message;
                });
    }

    @Override
    public List<MallTabConditionListVO> getTabCondition(OmsOrderPageDTO params) {
        params.setOrderStatus(null);

        // 查询tab条件数量
        List<MallTabConditionListVO> tabDataList = this.omsOrderMapper.selectTabConditionByWeb(params);

        List<MallTabConditionListVO> result = this.iMallCommonService.getTabDataList(tabDataList, SysDictTypeEnum.MALL_ORDER_TAB_CONDITION_WEB);
        for (MallTabConditionListVO item : result) {
            if (WebOmsOrderTabEnum.AFTER_SALE.getValue().equals(item.getValue())) {
                item.setNum(this.iOmsOrderAfterSaleService.getHandleIngNum(UmsUserContext.getUserId()));
                break;
            }
        }

        return this.iMallCommonService.getTabDataList(tabDataList, SysDictTypeEnum.MALL_ORDER_TAB_CONDITION_WEB);
    }

    @Override
    public IPage<OmsOrderBaseVO> page(OmsOrderPageDTO params) {
        IPage<OmsOrderBaseVO> result = this.omsOrderMapper.selectDataList(new Page<>(), params);
        List<OmsOrderBaseVO> list = result.getRecords();
        this.handleOrderListData(list);
        return result;
    }

    /**
     * 处理订单数据
     *
     * @param list 订单列表
     * @return 处理过后的订单数据
     * @author zhengqingya
     * @date 2021/10/25 9:52
     */
    private List<OmsOrderBaseVO> handleOrderListData(List<OmsOrderBaseVO> list) {
        // 订单编号list
        List<String> orderNoList = list.stream().map(OmsOrderBaseVO::getOrderNo).collect(Collectors.toList());
        // 查询订单关联商品数据
        Map<String, List<OmsOrderItemVO>> orderItemMap = this.iOmsOrderItemService.mapInfo(OmsOrderItemDTO.builder().orderNoList(orderNoList).build());
        // 查询订单关联配送数据
        Map<String, List<OmsOrderDeliverVO>> deliverInfoMap = this.iOmsOrderDeliverService.mapByOrderNoList(orderNoList);
        // 查询订单关联售后数据
        Map<String, Boolean> afterSaleInfoMap = this.iOmsOrderAfterSaleService.mapByOrderNoList(orderNoList);
        list.forEach(item -> {
            String orderNo = item.getOrderNo();
            // 装载关联商品数据
            item.setSpuList(orderItemMap.get(orderNo));
            // 装载关联配送数据
            item.setDeliverList(deliverInfoMap.get(orderNo));
            // 装载关联售后数据
            item.setIsAfterSale(afterSaleInfoMap.get(orderNo));
            // 处理数据
            item.handleData();
        });
        return list;
    }

    @Override
    public OmsOrderBaseVO detail(String orderNo) {
        log.info("[商城] 查询订单详情 订单编号：{}", orderNo);
        return this.page(OmsOrderPageDTO.builder().orderNo(orderNo).build()).getRecords().get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendSpu(WebOmsOrderSendSpuDTO params) {
        log.info("[商城] 订单-发货-提交参数：{}", params);
        String orderNo = params.getOrderNo();
        List<String> orderItemIdList = params.getOrderItemIdList();
        // 查询自动收货时间
        long autoReceiptMillisecond = this.iMallCommonService.getAutoReceiptMillisecond();
        // 发货逻辑处理
        this.sendSpuBase(params, autoReceiptMillisecond);
    }

    /**
     * 发货 -- base
     *
     * @param params                 发货参数
     * @param autoReceiptMillisecond 自动收货时间
     * @return void
     * @author zhengqingya
     * @date 2022/1/25 10:54
     */
    private void sendSpuBase(WebOmsOrderSendSpuDTO params, long autoReceiptMillisecond) {
        String orderNo = params.getOrderNo();
        String receiverName = params.getReceiverName();
        List<String> orderItemIdList = params.getOrderItemIdList();
        String logisticsCode = params.getLogisticsCode();
        String logisticsNo = params.getLogisticsNo();
        String logisticsCompany = TpsLogisticsCodeEnum.getEnumByCode(logisticsCode).getName();
        String delivererAddress = params.getDelivererAddress();
        String receiverPhone = params.getReceiverPhone();
        String receiverAddress = params.getReceiverAddress();
        String delivererName = params.getDelivererName();
        String delivererPhone = params.getDelivererPhone();
        String wxNoticeMsg = params.getWxNoticeMsg();
        Date autoReceiptTime = new Date(System.currentTimeMillis() + autoReceiptMillisecond);

        // 1、订单状态 -> 发货
        OmsOrder order = this.getOrder(orderNo);
        Byte orderStatus = order.getOrderStatus();
        Assert.isTrue(OmsOrderStatusEnum.UN_BILL.getStatus().equals(orderStatus), "无法发货：" + OmsOrderStatusEnum.getEnum(orderStatus).getDesc());
        order.setOrderStatus(OmsOrderStatusEnum.BILL.getStatus());
        order.setAutoReceiptTime(autoReceiptTime);
        order.updateById();

        // 2、订单关联商品状态 -> 发货
        this.iOmsOrderItemService.updateBatchStatus(orderItemIdList, OmsOrderItemStatusEnum.BILL);

        // 3、新增物流信息
        this.iOmsOrderDeliverService.addData(OmsOrderDeliver.builder()
                .orderNo(orderNo)
                .receiverName(receiverName)
                .receiverPhone(receiverPhone)
                .receiverAddress(receiverAddress)
                .delivererName(delivererName)
                .delivererPhone(delivererPhone)
                .delivererAddress(delivererAddress)
                .deliverTime(new Date())
                .logisticsCompany(logisticsCompany)
                .logisticsCode(logisticsCode)
                .logisticsNo(logisticsNo)
                .wxNoticeMsg(wxNoticeMsg)
                .autoReceiptTime(autoReceiptTime)
                .build());

        // 4、新增物流详情信息
        Map<String, OmsOrderItemVO> orderItemMap = this.iOmsOrderItemService.mapSkuBaseInfo(orderItemIdList);
        StringJoiner spuNameSj = new StringJoiner(",");
        for (OmsOrderItemVO orderItemBO : orderItemMap.values()) {
            spuNameSj.add(orderItemBO.getName());
        }

        // 5、mq延时-自动确认收货
        this.rabbitTemplate.convertAndSend(MallRabbitMqConstant.MALL_EVENT_DELAY_EXCHANGE,
                MallRabbitMqConstant.OMS_ORDER_AUTO_CONFIRM_RECEIPT_ROUTING_KEY,
                MiniOmsOrderConfirmReceiptDTO.builder()
                        .orderNo(orderNo)
                        .tenantId(TenantIdContext.getTenantId())
                        .build(), message -> {
                    // 配置消息延时时间
                    message.getMessageProperties().setHeader("x-delay", autoReceiptMillisecond);
                    return message;
                });
    }

    @Override
    @SneakyThrows(Exception.class)
    public void export(HttpServletResponse response, OmsOrderPageDTO params) {
        // 1、查询订单数据
        List<WebOmsOrderExportVO> exportOrderDataList = this.getExportOrderData(params);
        if (!CollectionUtils.isEmpty(exportOrderDataList)) {
            int exportOrderDataListSize = exportOrderDataList.size();
            for (int i = 0; i < exportOrderDataListSize; i++) {
                exportOrderDataList.get(i).setId(i + 1);
            }
        }
        // 2、导出
        String fileName = "订单-" + MyDateUtil.nowStr();
        //需要合并的列
        int[] mergeColIndex = {1, 2, 3, 4, 5, 6, 7, 16, 17, 18, 19, 20, 21, 22};
        //从第二行后开始合并
        int mergeRowIndex = 2;
        EasyExcelUtil.dynamicMergeData(response, fileName, "订单明细表", WebOmsOrderExportVO.class, exportOrderDataList, mergeColIndex, mergeRowIndex);
    }

    /**
     * 获取需要导出的订单数据
     *
     * @param params 查询参数
     * @return 需要导出的订单数据
     * @author zhengqingya
     * @date 2021/7/6 18:00
     */
    private List<WebOmsOrderExportVO> getExportOrderData(OmsOrderPageDTO params) {
        List<WebOmsOrderExportVO> resultList = Lists.newLinkedList();
        List<WebOmsOrderExportVO> orderList = this.omsOrderMapper.selectExportDataListByWeb(params);
        // 订单编号list
        List<String> orderNoList = orderList.stream().map(WebOmsOrderExportVO::getOrderNo).collect(Collectors.toList());
        // 查询订单关联商品数据
        Map<String, List<OmsOrderItemVO>> orderItemMap = this.iOmsOrderItemService.mapInfo(OmsOrderItemDTO.builder().orderNoList(orderNoList).build());
        // 查询订单关联配送数据
        Map<String, List<OmsOrderDeliverVO>> deliverInfoMap = this.iOmsOrderDeliverService.mapByOrderNoList(orderNoList);
        for (WebOmsOrderExportVO item : orderList) {
            String orderNoItem = item.getOrderNo();
            item.setOrderStatusName(OmsOrderStatusEnum.getEnum(item.getOrderStatus()).getDesc());
            // 装载关联配送数据
            List<OmsOrderDeliverVO> omsOrderDeliverList = deliverInfoMap.get(orderNoItem);
            if (!CollectionUtils.isEmpty(omsOrderDeliverList)) {
                OmsOrderDeliverVO omsOrderDeliverVO = omsOrderDeliverList.get(0);
                item.setLogisticsCode(omsOrderDeliverVO.getLogisticsCode());
                item.setLogisticsNo(omsOrderDeliverVO.getLogisticsNo());
            }

            // 订单关联的商品数据
            List<OmsOrderItemVO> orderItemList = orderItemMap.get(orderNoItem);
            // 每一个订单下的商品总数
            int spuItemSumNum = orderItemList.stream().mapToInt(OmsOrderItemVO::getNum).sum();
            item.setSpuSumNum(spuItemSumNum);

            // 循环装载总数据
            orderItemList.forEach(orderReSpuItemInfo -> {
                item.setSpuName(orderReSpuItemInfo.getName());
                item.setSpuTypeName(orderReSpuItemInfo.getTypeName());
                StringJoiner spuAttrNameItemSj = new StringJoiner(",");
                List<PmsSkuSpecBO> attrListItem = orderReSpuItemInfo.getSpecList();
                attrListItem.forEach(attrItem -> spuAttrNameItemSj.add(String.format("[%s:%s]", attrItem.getAttrKeyName(), attrItem.getAttrValueName())));
                item.setSpuAttrName(spuAttrNameItemSj.toString());
                item.setSpuPrice(BigDecimalUtil.fenToYuan(orderReSpuItemInfo.getPrice()));
                item.setSpuNum(orderReSpuItemInfo.getNum());
                item.setSpuStatus(orderReSpuItemInfo.getStatusName());
                item.setSpuItemTotalPrice(BigDecimalUtil.fenToYuan(orderReSpuItemInfo.getTotalPrice()));
                item.setSpuIsAfterSale(orderReSpuItemInfo.getIsAfterSale() ? "是" : "否");
                item.setFreight(BigDecimalUtil.fenToYuan(item.getFreight()));
                item.setPayPrice(BigDecimalUtil.fenToYuan(item.getPayPrice()));
                item.setOrderTotalPrice(BigDecimalUtil.fenToYuan(item.getOrderTotalPrice()));
                item.setSpuTotalPrice(BigDecimalUtil.fenToYuan(item.getSpuTotalPrice()));
                resultList.add(item);
            });
        }
        return resultList;
    }

    @Override
    @SneakyThrows(Exception.class)
    public void importBatchSendSpu(MultipartFile file) {
        EasyExcel.read(file.getInputStream(), WebOmsOrderImportSendSpuBO.class, new ReadListener<WebOmsOrderImportSendSpuBO>() {
            /**
             * 单次缓存的数据量
             */
            public static final int BATCH_COUNT = 100;
            /**
             *临时存储
             */
            private List<WebOmsOrderImportSendSpuBO> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            @Override
            public void invoke(WebOmsOrderImportSendSpuBO data, AnalysisContext context) {
                this.cachedDataList.add(data);
                if (this.cachedDataList.size() >= BATCH_COUNT) {
                    this.saveData();
                    // 存储完成清理 list
                    this.cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                this.saveData();
            }

            /**
             * 加上存储数据库
             */
            private void saveData() {
                log.info("[商城] {}条数据，开始进行订单发货业务处理！", this.cachedDataList.size());
                // 订单编号list
                List<String> orderNoList = this.cachedDataList.stream().map(WebOmsOrderImportSendSpuBO::getOrderNo).collect(Collectors.toList());
                // 查询订单关联售后数据
                Map<String, Boolean> afterSaleInfoMap = OmsOrderServiceImpl.this.iOmsOrderAfterSaleService.mapByOrderNoList(orderNoList);
                for (WebOmsOrderImportSendSpuBO item : this.cachedDataList) {
                    // 先循环处理，后期改为批量处理以提升执行效率
                    String orderNoItem = item.getOrderNo();
                    if (com.alibaba.excel.util.StringUtils.isBlank(orderNoItem)) {
                        continue;
                    }
                    try {
                        MyValidatorUtil.validate(item);
                    } catch (Exception e) {
                        throw new MyException(String.format("订单号：%s %s", orderNoItem, e.getMessage()));
                    }
                    OmsOrder orderItem = OmsOrderServiceImpl.this.getOrder(orderNoItem);
                    if (!OmsOrderStatusEnum.UN_BILL.getStatus().equals(orderItem.getOrderStatus())) {
                        continue;
                    }
                    // 是否售后处理中
                    Boolean isAfterSale = afterSaleInfoMap.get(orderNoItem);
                    if (isAfterSale != null && isAfterSale) {
                        continue;
                    }

                    // 查询自动收货时间
                    long autoReceiptMillisecond = OmsOrderServiceImpl.this.iMallCommonService.getAutoReceiptMillisecond();
                    // 发货逻辑处理
                    OmsOrderServiceImpl.this.sendSpuBase(WebOmsOrderSendSpuDTO.builder()
                            .orderNo(orderNoItem)
                            .orderItemIdList(OmsOrderServiceImpl.this.iOmsOrderItemService.orderItemListByOrderNo(orderNoItem))
                            .logisticsCode(item.getLogisticsCode())
                            .logisticsNo(item.getLogisticsNo())
                            .wxNoticeMsg(item.getWxNoticeMsg())
                            .receiverName(orderItem.getReceiverName())
                            .receiverPhone(orderItem.getReceiverPhone())
                            .receiverAddress(orderItem.getReceiverAddress())
                            .build(), autoReceiptMillisecond);
                }
                log.info("[商城] 订单发货处理成功！");
            }
        }).sheet().headRowNumber(2).doRead();
    }


}
