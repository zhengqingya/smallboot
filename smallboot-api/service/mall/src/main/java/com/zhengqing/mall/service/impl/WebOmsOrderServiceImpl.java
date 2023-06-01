package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.BigDecimalUtil;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.excel.util.EasyExcelUtil;
import com.zhengqing.common.web.util.MyValidatorUtil;
import com.zhengqing.mall.constant.MallRabbitMqConstant;
import com.zhengqing.mall.entity.OmsOrder;
import com.zhengqing.mall.entity.OmsOrderShipping;
import com.zhengqing.mall.entity.OmsOrderShippingItem;
import com.zhengqing.mall.mapper.OmsOrderMapper;
import com.zhengqing.mall.model.bo.PmsSkuSpecBO;
import com.zhengqing.mall.model.bo.WebOmsOrderImportSendSpuBO;
import com.zhengqing.mall.model.bo.WebOmsOrderReShippingBO;
import com.zhengqing.mall.model.dto.MiniOmsOrderConfirmReceiptDTO;
import com.zhengqing.mall.model.dto.OmsOrderItemDTO;
import com.zhengqing.mall.model.dto.WebOmsOrderPageDTO;
import com.zhengqing.mall.model.dto.WebOmsOrderSendSpuDTO;
import com.zhengqing.mall.model.enums.MallResultCodeEnum;
import com.zhengqing.mall.model.enums.OmsOrderItemStatusEnum;
import com.zhengqing.mall.model.enums.OmsOrderStatusEnum;
import com.zhengqing.mall.model.enums.TpsLogisticsCodeEnum;
import com.zhengqing.mall.model.vo.*;
import com.zhengqing.mall.service.*;
import com.zhengqing.system.enums.SysDictTypeEnum;
import com.zhengqing.system.service.ISysDictService;
import com.zhengqing.system.service.ISysPropertyService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
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
public class WebOmsOrderServiceImpl extends OmsOrderServiceImpl<OmsOrderMapper, OmsOrder> implements WebOmsOrderService {

    @Resource
    private OmsOrderMapper omsOrderMapper;

    @Resource
    private MallCommonService mallCommonService;

    @Resource
    private WebOmsOrderItemService webOmsOrderItemService;

    @Resource
    private WebOmsOrderShippingService webOmsOrderShippingService;

    @Resource
    private WebOmsOrderAfterSaleService webOmsOrderAfterSaleService;

    @Resource
    private WebOmsOrderShippingItemService webOmsOrderShippingItemService;

    @Resource
    private ISysDictService sysDictService;

    @Resource
    private ISysPropertyService sysPropertyService;

    @Resource
    private RabbitTemplate rabbitTemplate;

//    @Override
//    public WebOmsOrderSetVO getOrderSet() {
//        // 1、订单-设置
//        List<String> sysPropertyKeyList = SysPropertyKeyEnum.LIST_MALL_ORDER_SET.stream().map(SysPropertyKeyEnum::getKey).collect(Collectors.toList());
//        List<SysPropertyVO> orderSetDataList = this.sysPropertyService.listByKey(sysPropertyKeyList);
//        Assert.notNull(orderSetDataList, "系統属性缓存丢失，请刷新重试或联系系统管理员！");
//
//        // 2、订单-发货微信消息通知
//        ApiResult<List<SysDictVO>> dictDataWrapper = this.sysDictService.listByOpenCode(SysDictTypeEnum.MALL_ORDER_DELIVER_WX_MSG_NOTICE.getCode());
//        dictDataWrapper.checkForRpc();
//        // 微信消息数据
//        List<SysDictVO> dictListByWxMsgList = dictDataWrapper.getData();
//        Assert.notNull(dictListByWxMsgList, "数据字典缓存丢失，请联系系统管理员！");
//
//        // 3、减库存设置
//        SysPropertyVO payTypeProperty = null;
//        for (SysPropertyVO sysPropertyVO : orderSetDataList) {
//            if (SysPropertyKeyEnum.MALL_ORDER_SET_STOCK_CHECK_TYPE.getKey().equals(sysPropertyVO.getKey())) {
//                payTypeProperty = sysPropertyVO;
//                break;
//            }
//        }
//        orderSetDataList.remove(payTypeProperty);
//
//        // 4、封装返回数据
//        return WebOmsOrderSetVO.builder()
//                .wxMsgList(dictListByWxMsgList)
//                .setList(orderSetDataList)
//                .payType(Byte.valueOf(payTypeProperty.getValue()))
//                .build();
//    }
//
//    @Override
//    public void updateOrderSet(WebOmsOrderSetDTO params) {
//        log.info("[商城] 保存订单设置数据：{}", params);
//        // 1、保存订单设置数据
//        ValidList<SysPropertySaveDTO> setList = params.getSetList();
//        ApiResult<Boolean> setDataWrapper = this.sysPropertyService.saveBatch(setList);
//        if (setDataWrapper.checkIsFail()) {
//            log.error("[商城] 订单设置数据保存失败:{}", setDataWrapper);
//            throw new MyException("订单设置数据保存失败，请联系系统管理员！");
//        }
//
//        // 2、保存微信消息通知数据
//        ValidList<SysDictSaveBatchDTO> wxMsgList = params.getWxMsgList();
//        ApiResult<Boolean> wxMsgDataWrapper = this.sysDictService.addOrUpdateBatch(new HashMap<String, ValidList<SysDictSaveBatchDTO>>(1) {
//            {
//                this.put(SysDictTypeEnum.MALL_ORDER_DELIVER_WX_MSG_NOTICE.getCode(), wxMsgList);
//            }
//        });
//        if (wxMsgDataWrapper.checkIsFail()) {
//            log.error("[商城] 微信消息数据保存失败:{}", setDataWrapper);
//            throw new MyException("微信消息数据保存失败，请联系系统管理员！");
//        }
//    }

    @Override
    public List<MallTabConditionListVO> getTabCondition(WebOmsOrderPageDTO params) {
        params.setOrderStatus(null);
        // 查询tab条件数量
        List<MallTabConditionListVO> tabDataList = this.omsOrderMapper.selectTabConditionByWeb(params);
        return this.mallCommonService.getTabDataList(tabDataList, SysDictTypeEnum.MALL_ORDER_TAB_CONDITION_WEB);
    }

    @Override
    public IPage<WebOmsOrderPageVO> page(WebOmsOrderPageDTO params) {
        IPage<WebOmsOrderPageVO> result = this.omsOrderMapper.selectDataListByWeb(
                new Page<>(), params);
        List<WebOmsOrderPageVO> list = result.getRecords();
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
    private List<WebOmsOrderPageVO> handleOrderListData(List<WebOmsOrderPageVO> list) {
        // 订单编号list
        List<String> orderNoList = list.stream().map(WebOmsOrderPageVO::getOrderNo).collect(Collectors.toList());
        // 查询订单关联商品数据
        Map<String, List<OmsOrderItemVO>> orderItemMap = this.webOmsOrderItemService.mapInfo(
                OmsOrderItemDTO.builder().orderNoList(orderNoList).build());
        // 查询订单关联配送数据
        Map<String, List<OmsOrderShippingVO>> shippingInfoMap = this.webOmsOrderShippingService.mapByOrderNoList(orderNoList);
        // 查询订单关联售后数据
        Map<String, Boolean> afterSaleInfoMap = this.webOmsOrderAfterSaleService.mapByOrderNoList(orderNoList);
        list.forEach(item -> {
            String orderNo = item.getOrderNo();
            // 装载关联商品数据
            item.setSpuList(orderItemMap.get(orderNo));
            // 装载关联配送数据
            List<OmsOrderShippingVO> omsOrderShippingList = shippingInfoMap.get(orderNo);
            if (!CollectionUtils.isEmpty(omsOrderShippingList)) {
                OmsOrderShippingVO omsOrderShippingVO = omsOrderShippingList.get(0);
                item.setShippingObj(WebOmsOrderReShippingBO.builder()
                        .logisticsCode(omsOrderShippingVO.getLogisticsCode())
                        .logisticsNo(omsOrderShippingVO.getLogisticsNo())
                        .build());
            }
            // 装载关联售后数据
            item.setIsAfterSale(afterSaleInfoMap.get(orderNo));
            // 处理数据
            item.handleData();
        });
        return list;
    }

    @Override
    public WebOmsOrderDetailVO detail(String orderNo) {
        log.info("[商城] 查询订单详情 订单编号：{}", orderNo);
        // 1、查询订单基本信息
        WebOmsOrderDetailVO orderDetail = this.omsOrderMapper.detailByWeb(orderNo);
        Assert.notNull(orderDetail, "该订单不存在！");
        // 2、查询关联物流信息
        List<OmsOrderShippingVO> orderShippingList = this.webOmsOrderShippingService.listByOrderNo(orderNo);
        if (!CollectionUtils.isEmpty(orderShippingList)) {
            OmsOrderShippingVO orderShipping = orderShippingList.get(0);
            orderDetail.setShippingObj(WebOmsOrderReShippingBO.builder()
                    .logisticsCode(orderShipping.getLogisticsCode())
                    .logisticsNo(orderShipping.getLogisticsNo())
                    .build());
        }
        // 3、查询订单关联是否售后
        Boolean isAfterSale = this.webOmsOrderAfterSaleService.mapByOrderNoList(Lists.newArrayList(orderNo)).get(orderNo);
        orderDetail.setIsAfterSale(isAfterSale);
        // 4、查询关联商品数据
        List<OmsOrderItemVO> orderReSpuList = this.webOmsOrderItemService.listByOrderNo(orderNo);
        orderDetail.setSpuList(orderReSpuList);
        // 5、处理数据
        orderDetail.handleData();
        return orderDetail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendSpu(WebOmsOrderSendSpuDTO params) {
        log.info("[商城] 订单-发货-提交参数：{}", params);
        String orderNo = params.getOrderNo();
        List<String> orderItemIdList = params.getOrderItemIdList();
        // 校验此批商品在之前是否发过货
        Assert.isFalse(this.webOmsOrderShippingItemService.checkSend(orderNo, orderItemIdList), MallResultCodeEnum.部分商品已发货.getDesc());
        // 查询自动收货时间
        long autoReceiptMillisecond = this.mallCommonService.getAutoReceiptMillisecond();
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
        order.setOrderStatus(OmsOrderStatusEnum.BILL.getStatus());
        order.setAutoReceiptTime(autoReceiptTime);
        order.updateById();

        // 2、订单关联商品状态 -> 发货
        this.webOmsOrderItemService.updateBatchStatus(orderItemIdList, OmsOrderItemStatusEnum.BILL);

        // 3、新增物流信息
        String shippingId = this.webOmsOrderShippingService.addData(OmsOrderShipping.builder()
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
        Map<String, OmsOrderItemVO> orderItemMap = this.webOmsOrderItemService.mapSkuBaseInfo(orderItemIdList);
        List<OmsOrderShippingItem> omsOrderShippingItemSaveList = Lists.newLinkedList();
        StringJoiner spuNameSj = new StringJoiner(",");
        for (OmsOrderItemVO orderItemBO : orderItemMap.values()) {
            omsOrderShippingItemSaveList.add(OmsOrderShippingItem.builder()
                    .orderNo(orderNo)
                    .shippingId(shippingId)
                    .orderItemId(orderItemBO.getId())
                    .build());
            spuNameSj.add(orderItemBO.getName());
        }
        this.webOmsOrderShippingItemService.addOrUpdateBatchData(omsOrderShippingItemSaveList);

        // 5、mq延时-自动确认收货
        this.rabbitTemplate.convertAndSend(MallRabbitMqConstant.MALL_EVENT_DELAY_EXCHANGE,
                MallRabbitMqConstant.OMS_ORDER_AUTO_CONFIRM_RECEIPT_ROUTING_KEY,
                MiniOmsOrderConfirmReceiptDTO.builder()
                        .orderNo(orderNo)
                        .tenantId(TenantIdContext.getTenantId())
                        .shippingId(shippingId)
                        .build(), message -> {
                    // 配置消息延时时间
                    message.getMessageProperties().setHeader("x-delay", autoReceiptMillisecond);
                    return message;
                });
    }

    @Override
    @SneakyThrows(Exception.class)
    public void export(HttpServletResponse response, WebOmsOrderPageDTO params) {
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
    private List<WebOmsOrderExportVO> getExportOrderData(WebOmsOrderPageDTO params) {
        List<WebOmsOrderExportVO> resultList = Lists.newLinkedList();
        List<WebOmsOrderExportVO> orderList = this.omsOrderMapper.selectExportDataListByWeb(params);
        // 订单编号list
        List<String> orderNoList = orderList.stream().map(WebOmsOrderExportVO::getOrderNo).collect(Collectors.toList());
        // 查询订单关联商品数据
        Map<String, List<OmsOrderItemVO>> orderItemMap = this.webOmsOrderItemService.mapInfo(
                OmsOrderItemDTO.builder().orderNoList(orderNoList).build());
        // 查询订单关联配送数据
        Map<String, List<OmsOrderShippingVO>> shippingInfoMap = this.webOmsOrderShippingService.mapByOrderNoList(orderNoList);
        for (WebOmsOrderExportVO item : orderList) {
            String orderNoItem = item.getOrderNo();
            item.setOrderStatusName(OmsOrderStatusEnum.getEnum(item.getOrderStatus()).getDesc());
            // 装载关联配送数据
            List<OmsOrderShippingVO> omsOrderShippingList = shippingInfoMap.get(orderNoItem);
            if (!CollectionUtils.isEmpty(omsOrderShippingList)) {
                OmsOrderShippingVO omsOrderShippingVO = omsOrderShippingList.get(0);
                item.setLogisticsCode(omsOrderShippingVO.getLogisticsCode());
                item.setLogisticsNo(omsOrderShippingVO.getLogisticsNo());
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
                Map<String, Boolean> afterSaleInfoMap = WebOmsOrderServiceImpl.this.webOmsOrderAfterSaleService.mapByOrderNoList(orderNoList);
                for (WebOmsOrderImportSendSpuBO item : this.cachedDataList) {
                    // 先循环处理，后期改为批量处理以提升执行效率
                    String orderNoItem = item.getOrderNo();
                    if (StringUtils.isBlank(orderNoItem)) {
                        continue;
                    }
                    try {
                        MyValidatorUtil.validate(item);
                    } catch (Exception e) {
                        throw new MyException(String.format("订单号：%s %s", orderNoItem, e.getMessage()));
                    }
                    OmsOrder orderItem = WebOmsOrderServiceImpl.this.getOrder(orderNoItem);
                    if (!OmsOrderStatusEnum.UN_BILL.getStatus().equals(orderItem.getOrderStatus())) {
                        continue;
                    }
                    // 是否售后处理中
                    Boolean isAfterSale = afterSaleInfoMap.get(orderNoItem);
                    if (isAfterSale != null && isAfterSale) {
                        continue;
                    }

                    // 查询自动收货时间
                    long autoReceiptMillisecond = WebOmsOrderServiceImpl.this.mallCommonService.getAutoReceiptMillisecond();
                    // 发货逻辑处理
                    WebOmsOrderServiceImpl.this.sendSpuBase(WebOmsOrderSendSpuDTO.builder()
                            .orderNo(orderNoItem)
                            .orderItemIdList(WebOmsOrderServiceImpl.this.webOmsOrderItemService.orderItemListByOrderNo(orderNoItem))
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
