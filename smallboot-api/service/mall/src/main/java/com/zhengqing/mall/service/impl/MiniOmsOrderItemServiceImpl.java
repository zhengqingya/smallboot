package com.zhengqing.mall.service.impl;

import com.google.common.collect.Lists;
import com.zhengqing.mall.model.enums.OmsOrderStatusEnum;
import com.zhengqing.mall.entity.OmsOrderItem;
import com.zhengqing.mall.mapper.OmsOrderItemMapper;
import com.zhengqing.mall.model.bo.MiniOmsOrderItemBuyLimitBO;
import com.zhengqing.mall.model.bo.MiniOmsOrderItemStatusBO;
import com.zhengqing.mall.service.MiniOmsOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 商城-订单详情 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
@Slf4j
@Service
public class MiniOmsOrderItemServiceImpl extends OmsOrderItemServiceImpl<OmsOrderItemMapper, OmsOrderItem> implements MiniOmsOrderItemService {

    @Resource
    private OmsOrderItemMapper omsOrderItemMapper;

    @Override
    public Map<String, Integer> mapSkuLimit(Long userId, List<String> skuIdList) {
        return this.listForSkuLimit(userId, skuIdList).stream().collect(Collectors.toMap(
                MiniOmsOrderItemBuyLimitBO::getSkuId,
                MiniOmsOrderItemBuyLimitBO::getNum, (k1, k2) -> k1));
    }

    private List<MiniOmsOrderItemBuyLimitBO> listForSkuLimit(Long userId, List<String> skuIdList) {
        if (CollectionUtils.isEmpty(skuIdList)) {
            return Lists.newArrayList();
        }
        List<MiniOmsOrderItemBuyLimitBO> list = this.omsOrderItemMapper.selectListForSkuLimit(userId, skuIdList);
        List<String> skuIdListNew = list.stream().map(MiniOmsOrderItemBuyLimitBO::getSkuId).collect(Collectors.toList());
        // 如果之前没买过此sku商品，则为0
        List<String> removeSkuIdList = skuIdList.stream().filter(
                skuIdOld -> !skuIdListNew.contains(skuIdOld)).collect(Collectors.toList());
        removeSkuIdList.forEach(skuId -> list.add(MiniOmsOrderItemBuyLimitBO.builder()
                .skuId(skuId)
                .num(0)
                .build()));
        return list;
    }

    @Override
    public Map<String, OmsOrderStatusEnum> mapStatusByIdList(List<String> orderItemIdList) {
        List<MiniOmsOrderItemStatusBO> orderItemStatusList = this.omsOrderItemMapper.selectListStatusByIdList(orderItemIdList);
        return orderItemStatusList.stream().collect(Collectors.toMap(
                MiniOmsOrderItemStatusBO::getOrderItemId,
                e -> OmsOrderStatusEnum.getEnum(e.getOrderStatus()), (k1, k2) -> k1));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchIsRate(List<String> orderItemIdList, Boolean isRate) {
        this.omsOrderItemMapper.updateBatchIsRate(orderItemIdList, isRate);
    }

}
