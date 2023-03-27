package com.zhengqing.mall.service.impl;

import com.google.common.collect.Lists;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.mall.entity.PmsSpuRate;
import com.zhengqing.mall.mapper.PmsSpuRateMapper;
import com.zhengqing.mall.mini.model.dto.MiniPmsSpuRateSaveDTO;
import com.zhengqing.mall.service.MiniOmsOrderItemService;
import com.zhengqing.mall.service.MiniPmsSpuRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> 商城-商品评价 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
@Slf4j
@Service
public class MiniPmsSpuRateServiceImpl extends PmsSpuRateServiceImpl<PmsSpuRateMapper, PmsSpuRate> implements MiniPmsSpuRateService {

    @Resource
    private PmsSpuRateMapper pmsSpuRateMapper;

    @Resource
    private MiniOmsOrderItemService miniOmsOrderItemService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatchData(MiniPmsSpuRateSaveDTO params) {
        Byte operatorType = params.getOperatorType();
        Long operatorId = params.getOperatorId();
        String operatorName = params.getOperatorName();
        String operatorIcon = params.getOperatorIcon();
        List<MiniPmsSpuRateSaveDTO.RateInfo> rateList = params.getRateList();

        // 1、保存评价数据
        List<PmsSpuRate> saveList = Lists.newLinkedList();
        rateList.forEach(item -> saveList.add(PmsSpuRate.builder()
                .id(IdGeneratorUtil.nextStrId())
                .orderItemId(item.getOrderItemId())
                .spuId(item.getSpuId())
                .skuId(item.getSkuId())
                .specList(item.getSpecList())
                .operatorType(operatorType)
                .operatorId(operatorId)
                .operatorName(operatorName)
                .operatorIcon(operatorIcon)
                .resourceJson(item.getResourceJson())
                .content(item.getContent())
                .isShow(true)
                .descLevel(item.getDescLevel())
                .logisticsLevel(item.getLogisticsLevel())
                .serviceLevel(item.getServiceLevel())
                .isImg(item.getIsImg())
                .isVideo(item.getIsVideo())
                .rateType(item.getRateType())
                .build()));
        super.saveBatch(saveList);

        // 2、订单关联商品是否评价值处理
        List<String> orderItemIdList = saveList.stream().map(PmsSpuRate::getOrderItemId).collect(Collectors.toList());
        this.miniOmsOrderItemService.updateBatchIsRate(orderItemIdList, true);
    }

}
