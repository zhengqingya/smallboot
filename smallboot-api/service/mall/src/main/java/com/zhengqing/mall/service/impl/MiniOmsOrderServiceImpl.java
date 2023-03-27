package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.context.UmsUserContext;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.mall.common.model.bo.OmsOrderAfterSaleCloseBO;
import com.zhengqing.mall.common.model.bo.PmsSpuBuyNumInfoBO;
import com.zhengqing.mall.common.model.dto.OmsOrderCancelDTO;
import com.zhengqing.mall.common.model.dto.OmsOrderItemDTO;
import com.zhengqing.mall.common.model.dto.PmsSkuDTO;
import com.zhengqing.mall.common.model.enums.*;
import com.zhengqing.mall.common.model.vo.MallTabConditionListVO;
import com.zhengqing.mall.common.model.vo.OmsOrderItemVO;
import com.zhengqing.mall.common.model.vo.OmsOrderShippingVO;
import com.zhengqing.mall.common.model.vo.PmsSkuVO;
import com.zhengqing.mall.constant.MallAppConstant;
import com.zhengqing.mall.constant.MallRabbitMqConstant;
import com.zhengqing.mall.entity.OmsOrder;
import com.zhengqing.mall.entity.OmsOrderAfterSale;
import com.zhengqing.mall.entity.OmsOrderAfterSaleItem;
import com.zhengqing.mall.entity.OmsOrderItem;
import com.zhengqing.mall.mapper.OmsOrderMapper;
import com.zhengqing.mall.mini.model.dto.*;
import com.zhengqing.mall.mini.model.vo.*;
import com.zhengqing.mall.service.*;
import com.zhengqing.system.enums.SysDictTypeEnum;
import com.zhengqing.ums.model.vo.UmsUserVO;
import com.zhengqing.ums.service.IUmsUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
public class MiniOmsOrderServiceImpl extends OmsOrderServiceImpl<OmsOrderMapper, OmsOrder> implements MiniOmsOrderService {

    @Resource
    private OmsOrderMapper omsOrderMapper;

    @Resource
    private MallCommonService mallCommonService;

    @Resource
    private MiniPmsSpuService miniPmsSpuService;

    @Resource
    private MiniOmsOrderItemService miniOmsOrderItemService;

//    @Resource
//    private IPayClient payClient;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MiniOmsOrderAfterSaleService miniOmsOrderAfterSaleService;

    @Resource
    private MiniOmsOrderAfterSaleItemService miniOmsOrderAfterSaleItemService;

    @Resource
    private MiniOmsOrderShippingService miniOmsOrderShippingService;


    @Resource
    private IUmsUserService umsUserService;

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
        List<PmsSkuVO> mysqlSkuList = this.miniPmsSpuService.listBySku(
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
        Map<String, Integer> mysqlSkuHistoryLimitMap = this.miniOmsOrderItemService.mapSkuLimit(userId, skuIdList);
        List<PmsSpuBuyNumInfoBO> skuBuyInfoList = Lists.newLinkedList();
        spuList.forEach(spuItem ->
                skuBuyInfoList.add(PmsSpuBuyNumInfoBO.builder()
                        .skuId(spuItem.getSkuId())
                        .num(spuItem.getNum())
                        .build()));
        this.mallCommonService.checkSkuLimit(skuBuyInfoList, mysqlSkuMap, mysqlSkuHistoryLimitMap);

        // ==================================== ↓↓↓↓↓↓ 2、正常下单逻辑 ↓↓↓↓↓↓ ====================================
        // 获取用户信息 -- FIXME 之后有用户的时候修复
        UmsUserVO userInfo = this.umsUserService.getUser(userId);
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
        super.addData(OmsOrder.builder()
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
        this.miniOmsOrderItemService.addBatchData(omsOrderItemList);

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

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void paySuccessCallback(PayOrderNotifyBO payOrderNotifyBO) {
//        log.info("[商城] 支付成功回调消息: [{}]", JSON.toJSONString(payOrderNotifyBO));
//        if (!payOrderNotifyBO.getResultStatus()) {
//            log.warn("[商城] 支付回调异常不处理 -- 回调参数: [{}]", JSON.toJSONString(payOrderNotifyBO));
//            return;
//        }
//        // 系统内部订单编号
//        String orderNo = payOrderNotifyBO.getOrderNo();
//        // 微信支付订单号
//        String payNo = payOrderNotifyBO.getTransactionId();
//        // 查询订单详情
//        OmsOrder order = this.getOrder(orderNo);
//        // 是否为普通商品
//        Byte orderStatus = order.getOrderStatus();
//        if (!OmsOrderStatusEnum.UN_PAY.getStatus().equals(orderStatus)
//                && !OmsOrderStatusEnum.CANC.getStatus().equals(orderStatus)
//        ) {
//            log.warn("[商城] 支付成功回调消息: [{}] 不处理业务 该订单状态已变更为[{}]", payOrderNotifyBO, orderStatus);
//            return;
//        }
//
//        boolean isVirtual = false;
//        List<OmsOrderItemVO> orderItemList = Lists.newLinkedList();
//        // 订单关联商品详情 => 如果商品都为虚拟商品-则需要将此订单变更为待收货状态
//        orderItemList = this.miniOmsOrderItemService.listByOrderNo(orderNo);
//        for (OmsOrderItemVO orderItem : orderItemList) {
//            isVirtual = PmsSpuTypeEnum.isVirtual(orderItem.getType());
//            if (!isVirtual) {
//                break;
//            }
//        }
//        if (isVirtual) {
//            order.setAutoReceiptTime(this.mallCommonService.getAutoReceiptTime());
//        }
//        // 订单状态更新
//        order.setOrderStatus(isVirtual ? OmsOrderStatusEnum.BILL.getStatus()
//                : OmsOrderStatusEnum.UN_BILL.getStatus());
//
//        // 1、订单更新
//        order.setPayNo(payNo);
//        order.setPayTime(new Date());
////        order.setUnPayEndTime(null);
//        order.updateById();
//
//        // 2、更新商品状态
//        this.miniOmsOrderItemService.updateBatchStatusByOrderNo(orderNo,
//                isVirtual ? OmsOrderItemStatusEnum.BILL : OmsOrderItemStatusEnum.UN_BILL);
//
//        // 3、mq延时-自动确认收货
//        if (isVirtual) {
//            // 查询自动收货时间
//            long autoReceiptMillisecond = this.mallCommonService.getAutoReceiptMillisecond();
//            this.rabbitTemplate.convertAndSend(MallRabbitMqConstant.MALL_EVENT_DELAY_EXCHANGE,
//                    MallRabbitMqConstant.OMS_ORDER_AUTO_CONFIRM_RECEIPT_ROUTING_KEY,
//                    MiniOmsOrderConfirmReceiptDTO.builder()
//                            .orderNo(orderNo)
//                            .tenantId(TenantIdContext.getTenantId())
//                            .shippingId(null)
//                            .build(), message -> {
//                        // 配置消息延时时间
//                        message.getMessageProperties().setHeader("x-delay", autoReceiptMillisecond);
//                        return message;
//                    });
//        }
//    }

    @Override
    public List<MallTabConditionListVO> getTabCondition(MiniOmsOrderPageDTO params) {
        params.setOrderStatus(null);
        // 查询tab条件数量
        List<MallTabConditionListVO> tabDataList = this.omsOrderMapper.selectTabConditionByMini(
                params);
        List<MallTabConditionListVO> result = this.mallCommonService.getTabDataList(tabDataList,
                SysDictTypeEnum.MALL_ORDER_TAB_CONDITION_MINI);
        for (MallTabConditionListVO item : result) {
            if (MiniOmsOrderTabEnum.AFTER_SALE.getValue().equals(item.getValue())) {
                item.setNum(this.miniOmsOrderAfterSaleService.getHandleIngNum(params.getUserId()));
                item.setIsAfterSale(true);
                break;
            }
        }
        return result;
    }

    @Override
    public IPage<MiniOmsOrderPageVO> page(MiniOmsOrderPageDTO params) {
        IPage<MiniOmsOrderPageVO> miniOmsOrderPage = this.omsOrderMapper.selectDataListByMini(
                new Page<>(), params);
        List<MiniOmsOrderPageVO> list = miniOmsOrderPage.getRecords();
        if (CollectionUtils.isEmpty(list)) {
            return miniOmsOrderPage;
        }
        // 订单编号list
        List<String> orderNoList = list.stream().map(MiniOmsOrderPageVO::getOrderNo)
                .collect(Collectors.toList());
        // 查询订单关联商品数据
        Map<String, List<OmsOrderItemVO>> orderItemMap = this.miniOmsOrderItemService.mapInfo(
                OmsOrderItemDTO.builder()
                        .orderNoList(orderNoList).build());
        list.forEach(item -> {
            String orderNo = item.getOrderNo();
            List<OmsOrderItemVO> miniOmsOrderItemList = orderItemMap.get(orderNo);
            item.setSpuList(miniOmsOrderItemList);
            item.handleData();
        });
        return miniOmsOrderPage;
    }

    @Override
    public MiniOmsOrderDetailVO detailByMini(String orderNo) {
        // 1、订单主体详情
        MiniOmsOrderDetailVO miniOmsOrderDetailVO = this.omsOrderMapper.detailByMini(orderNo);
        Assert.notNull(miniOmsOrderDetailVO, "该订单不存在！");

        // 2、订单关联商品详情
        List<OmsOrderItemVO> orderItemList = this.miniOmsOrderItemService.listByOrderNo(orderNo);
        miniOmsOrderDetailVO.setSpuList(orderItemList);

        // 3、订单关联配送详情
        List<OmsOrderShippingVO> shippingList = this.miniOmsOrderShippingService.listByOrderNo(
                orderNo);
        miniOmsOrderDetailVO.setShippingList(shippingList);

        // 4、处理数据
        miniOmsOrderDetailVO.handleData();
        return miniOmsOrderDetailVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unPayCallback(MiniOmsOrderUnPayDTO params) {
        String orderNo = params.getOrderNo();
        log.info("[商城] 订单号：[{}] 在有效时间内未支付，自动取消", orderNo);
        super.cancelOrderForMq(OmsOrderCancelDTO.builder()
                .orderNo(orderNo)
                .build());
    }

//    @Override
//    @SneakyThrows({Exception.class})
//    @Transactional(rollbackFor = Exception.class)
//    public WxPayUnifiedOrderResult payOrder(MiniOmsOrderPayDTO params) {
//        log.info("[商城] 订单-支付-提交参数：[{}] ", params);
//        String orderNo = params.getOrderNo();
//        OmsOrder order = this.getOrder(orderNo);
//        OmsOrderStatusEnum orderStatusEnum = OmsOrderStatusEnum.getEnum(order.getOrderStatus());
//        Assert.isTrue(OmsOrderStatusEnum.UN_PAY == orderStatusEnum,
//                "无法支付，该订单状态为：" + orderStatusEnum.getDesc());
//        // 1、支付是否校验库存
//        if (OmsOrderStockCheckTypeEnum.PAY.getType().equals(order.getStockCheckType())) {
//            // 1.1、查询该订单关联商品
//            List<OmsOrderItemVO> orderReSpuList = this.miniOmsOrderItemService.listByOrderNo(
//                    orderNo);
//            List<PmsSkuStockBO> skuStockList = Lists.newLinkedList();
//            orderReSpuList.forEach(item -> skuStockList.add(PmsSkuStockBO.builder()
//                    .skuId(item.getSkuId())
//                    .num(-item.getNum())
//                    .build()));
//            // 1.2、库存扣减
//            Assert.isTrue(this.miniPmsSpuService.updateSkuStock(skuStockList), "商品库存不足!");
//        }
//        // 2、创建微信支付订单
//        WxPayUnifiedOrderResult wxPayUnifiedOrderResult = this.payClient.unifiedOrder(
//                PayOrderCreateDTO.builder()
//                        .tenantId(TenantIdContext.getTenantId())
//                        .orderNo(orderNo)
//                        .totalPrice(order.getPayPrice())
//                        .orderDesc("商品")
//                        .openId(order.getWxOpenid())
//                        .build()
//        );
//        return wxPayUnifiedOrderResult;
//    }

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
        List<OmsOrderItemVO> orderReSpuList = this.miniOmsOrderItemService.listByOrderNo(
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
        List<OmsOrderItemVO> orderItemList = this.miniOmsOrderItemService.listByOrderNo(orderNo);
        // 3、不可申请售后的订单详情ids
        List<String> noApplyReOrderItemIdList = this.miniOmsOrderAfterSaleService.getNoApplyReOrderItemIdListByOrderNo(
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
        String certImgJson = params.getCertImgJson();

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
        Map<String, OmsOrderItemVO> orderItemMap = this.miniOmsOrderItemService.mapSkuBaseInfo(
                orderItemIdList);
        Integer payPrice = order.getPayPrice();
        Assert.isTrue(payPrice >= refundPrice,
                String.format("退款金额[%s]不能超过订单实付总金额[%s]", refundPrice, payPrice));

        // 售后自动关闭时间
        long autoCloseAfterMillisecond = MallAppConstant.AUTO_CLOSE_AFTER_MILLISECOND;

        // 3、保存售后数据
        String afterSaleNo = this.miniOmsOrderAfterSaleService.addData(OmsOrderAfterSale.builder()
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
                .certImgJson(certImgJson)
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
        this.miniOmsOrderAfterSaleItemService.addOrUpdateBatchData(omsOrderAfterSaleItemSaveList);

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

}
