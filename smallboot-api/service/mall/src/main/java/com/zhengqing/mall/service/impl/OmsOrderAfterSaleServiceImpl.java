package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.mall.common.model.bo.OmsOrderAfterSaleCloseBO;
import com.zhengqing.mall.common.model.dto.OmsOrderAfterSaleDeleteDTO;
import com.zhengqing.mall.common.model.enums.OmsOrderAfterSaleStatusEnum;
import com.zhengqing.mall.common.model.vo.OmsOrderAfterSaleItemVO;
import com.zhengqing.mall.common.model.vo.OmsOrderAfterSaleVO;
import com.zhengqing.mall.entity.OmsOrderAfterSale;
import com.zhengqing.mall.mapper.OmsOrderAfterSaleMapper;
import com.zhengqing.mall.service.OmsLogisticService;
import com.zhengqing.mall.service.OmsOrderAfterSaleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class OmsOrderAfterSaleServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<OmsOrderAfterSaleMapper, OmsOrderAfterSale> implements OmsOrderAfterSaleService<OmsOrderAfterSale> {

    @Resource
    private OmsOrderAfterSaleMapper omsOrderAfterSaleMapper;


    @Resource
    private OmsLogisticService omsLogisticService;

    @Override
    public OmsOrderAfterSale detail(String afterSaleNo) {
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
        this.omsLogisticService.saveLogistic(returnLogisticsCode, returnLogisticsNo, receiverPhone);
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

}
