package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zhengqing.mall.model.dto.OmsOrderItemDTO;
import com.zhengqing.mall.model.enums.OmsOrderAfterSaleStatusEnum;
import com.zhengqing.mall.model.enums.OmsOrderSaleTypeEnum;
import com.zhengqing.mall.model.vo.OmsOrderItemVO;
import com.zhengqing.mall.entity.OmsOrderAfterSale;
import com.zhengqing.mall.mapper.OmsOrderAfterSaleMapper;
import com.zhengqing.mall.model.dto.MiniOmsOrderAfterSalePageDTO;
import com.zhengqing.mall.model.dto.MiniOmsOrderRepealAfterSaleDTO;
import com.zhengqing.mall.model.vo.MiniOmsOrderAfterSaleDetailVO;
import com.zhengqing.mall.model.vo.MiniOmsOrderAfterSalePageVO;
import com.zhengqing.mall.service.MiniOmsOrderAfterSaleService;
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
 * <p> 商城-订单售后 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
@Slf4j
@Service
public class MiniOmsOrderAfterSaleServiceImpl extends OmsOrderAfterSaleServiceImpl<OmsOrderAfterSaleMapper, OmsOrderAfterSale> implements MiniOmsOrderAfterSaleService {

    @Resource
    private OmsOrderAfterSaleMapper omsOrderAfterSaleMapper;

    @Resource
    private MiniOmsOrderItemService miniOmsOrderItemService;

    @Override
    public IPage<MiniOmsOrderAfterSalePageVO> page(MiniOmsOrderAfterSalePageDTO params) {
        IPage<MiniOmsOrderAfterSalePageVO> miniOmsAfterSalePage = this.omsOrderAfterSaleMapper.selectDataListByMini(
                new Page<>(), params
        );
        List<MiniOmsOrderAfterSalePageVO> afterSaleList = miniOmsAfterSalePage.getRecords();
        if (CollectionUtils.isEmpty(afterSaleList)) {
            return miniOmsAfterSalePage;
        }
        // 售后关联商品ids
        List<String> orderItemIdList = Lists.newLinkedList();
        afterSaleList.forEach(item -> {
            item.handleOrderItemIdList();
            orderItemIdList.addAll(item.getOrderItemIdList());
        });
        // 查询关联商品数据
        List<OmsOrderItemVO> orderItemList = this.miniOmsOrderItemService.listInfo(OmsOrderItemDTO.builder().orderItemIdList(orderItemIdList).build());
        Map<String, OmsOrderItemVO> orderItemMap = orderItemList.stream().collect(Collectors.toMap(OmsOrderItemVO::getId, t -> t, (k1, k2) -> k1));
        afterSaleList.forEach(item -> {
            List<OmsOrderItemVO> spuList = Lists.newLinkedList();
            List<String> orderItemIdListItem = item.getOrderItemIdList();
            orderItemIdListItem.forEach(orderItemId -> spuList.add(orderItemMap.get(orderItemId)));
            item.setSpuList(spuList);
            item.handleData();
        });
        return miniOmsAfterSalePage;
    }

    @Override
    public MiniOmsOrderAfterSaleDetailVO detailByMini(String afterSaleNo) {
        // 订单主体详情
        MiniOmsOrderAfterSaleDetailVO orderAfterSaleDetailVO = this.omsOrderAfterSaleMapper.detailByMini(afterSaleNo);
        Assert.notNull(orderAfterSaleDetailVO, "该售后数据不存在！");
        // 处理数据
        orderAfterSaleDetailVO.handleData();
        // 查询关联商品数据
        List<OmsOrderItemVO> orderItemList = this.miniOmsOrderItemService.listInfo(
                OmsOrderItemDTO.builder().orderItemIdList(orderAfterSaleDetailVO.getOrderItemIdList()).build());
        orderAfterSaleDetailVO.setSpuList(orderItemList);
        return orderAfterSaleDetailVO;
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
        super.updateData(OmsOrderAfterSale.builder()
                .afterSaleNo(afterSaleNo)
                .afterStatus(OmsOrderAfterSaleStatusEnum.REVOKE.getStatus())
                .build());
    }

}
