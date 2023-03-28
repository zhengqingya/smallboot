package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.mall.model.dto.OmsOrderItemDTO;
import com.zhengqing.mall.model.enums.OmsOrderItemStatusEnum;
import com.zhengqing.mall.model.vo.OmsOrderItemVO;
import com.zhengqing.mall.entity.OmsOrderAfterSale;
import com.zhengqing.mall.entity.OmsOrderItem;
import com.zhengqing.mall.mapper.OmsOrderItemMapper;
import com.zhengqing.mall.service.OmsOrderAfterSaleService;
import com.zhengqing.mall.service.OmsOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class OmsOrderItemServiceImpl<M extends BaseMapper<T>, T> extends
        ServiceImpl<OmsOrderItemMapper, OmsOrderItem> implements OmsOrderItemService<OmsOrderItem> {

    @Resource
    private OmsOrderItemMapper omsOrderItemMapper;


    @Resource
    @Qualifier("omsOrderAfterSaleServiceImpl")
    private OmsOrderAfterSaleService<OmsOrderAfterSale> omsOrderAfterSaleService;

    @Override
    public List<String> orderItemListByOrderNo(String orderNo) {
        return this.listByOrderNo(orderNo).stream().map(OmsOrderItemVO::getId).collect(Collectors.toList());
    }

    @Override
    public List<OmsOrderItemVO> listByOrderNo(String orderNo) {
        return this.mapInfo(OmsOrderItemDTO.builder().orderNoList(Lists.newArrayList(orderNo)).build()).get(orderNo);
    }

    @Override
    public List<OmsOrderItemVO> listInfo(OmsOrderItemDTO params) {
        List<OmsOrderItemVO> list = this.omsOrderItemMapper.selectItemList(params);
        if (!CollectionUtils.isEmpty(list)) {
            // ------- 装载售后数据 -------
            // 订单编号list
            List<String> orderNoList = list.stream().map(OmsOrderItemVO::getOrderNo).collect(Collectors.toList());
            // 订单号 -> 查询属于售后的订单商品ids
            Map<String, List<String>> mapOrderItemIdsForAfterSale = this.omsOrderAfterSaleService.mapOrderItemIdsForAfterSale(orderNoList);
            list.forEach(item -> {
                List<String> afterSaleReOrderItemIdList = mapOrderItemIdsForAfterSale.get(item.getOrderNo());
                if (!CollectionUtils.isEmpty(afterSaleReOrderItemIdList)) {
                    item.setIsAfterSale(afterSaleReOrderItemIdList.contains(item.getId()));
                }
                // 处理数据
                item.handleData();
            });
        }
        return list;
    }

    @Override
    public Map<String, List<OmsOrderItemVO>> mapInfo(OmsOrderItemDTO params) {
        Map<String, List<OmsOrderItemVO>> dataMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(params.getOrderNoList())) {
            return dataMap;
        }
        List<OmsOrderItemVO> list = this.listInfo(params);
        if (CollectionUtils.isEmpty(list)) {
            return dataMap;
        }
        for (OmsOrderItemVO item : list) {
            dataMap.computeIfAbsent(item.getOrderNo(), k -> new ArrayList<>()).add(item);
        }
        return dataMap;
    }

    @Override
    public Map<String, OmsOrderItemVO> mapSkuBaseInfo(List<String> orderItemIdList) {
        List<OmsOrderItemVO> orderItemList = this.listInfo(OmsOrderItemDTO.builder()
                .orderItemIdList(orderItemIdList).build());
        return orderItemList.stream().collect(Collectors.toMap(
                OmsOrderItemVO::getId,
                e -> e, (k1, k2) -> k1));
    }

    @Override
    public OmsOrderItem detail(String orderItemId) {
        OmsOrderItem detailData = this.omsOrderItemMapper.selectById(orderItemId);
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(String orderItemId) {
        this.omsOrderItemMapper.deleteById(orderItemId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatchData(List<OmsOrderItem> omsOrderItemList) {
        if (CollectionUtils.isEmpty(omsOrderItemList)) {
            return;
        }
        omsOrderItemList.forEach(item -> {
            if (StringUtils.isBlank(item.getId())) {
                item.setId(IdGeneratorUtil.nextStrId());
            }
        });
        super.saveBatch(omsOrderItemList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchStatus(List<String> orderItemIdList,
                                  OmsOrderItemStatusEnum omsOrderItemStatusEnum) {
        log.info("[商城] 更新订单关联商品:[{}] 状态:[{}]", orderItemIdList, omsOrderItemStatusEnum);
        if (CollectionUtils.isEmpty(orderItemIdList)) {
            return;
        }
        this.omsOrderItemMapper.updateBatchStatus(orderItemIdList,
                omsOrderItemStatusEnum.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchStatusForSendCoupon(List<String> orderItemIdList) {
        log.info("[商城] 发放订单关联优惠券商品:[{}]", orderItemIdList);
        if (CollectionUtils.isEmpty(orderItemIdList)) {
            return;
        }
        this.omsOrderItemMapper.updateBatchStatusForSendCoupon(orderItemIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchStatusByOrderNo(String orderNo,
                                           OmsOrderItemStatusEnum omsOrderStatusEnum) {
        log.info("[商城] 更新订单:[{}] 关联所有商品 状态:[{}]", orderNo, omsOrderStatusEnum);
        this.omsOrderItemMapper.updateBatchStatusByOrderNo(orderNo, omsOrderStatusEnum.getStatus());
    }

}
