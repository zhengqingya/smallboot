package com.zhengqing.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.mall.entity.PmsSpuRate;
import com.zhengqing.mall.mapper.PmsSpuRateMapper;
import com.zhengqing.mall.model.dto.MiniPmsSpuRatePageDTO;
import com.zhengqing.mall.model.dto.MiniPmsSpuRateSaveDTO;
import com.zhengqing.mall.model.vo.MiniPmsSpuRatePageVO;
import com.zhengqing.mall.service.IOmsOrderItemService;
import com.zhengqing.mall.service.IPmsSpuRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> 商城-商品评价 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 17:10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PmsSpuRateServiceImpl extends ServiceImpl<PmsSpuRateMapper, PmsSpuRate> implements IPmsSpuRateService {

    private final PmsSpuRateMapper pmsSpuRateMapper;
    private final IOmsOrderItemService iOmsOrderItemService;

    @Override
    public IPage<MiniPmsSpuRatePageVO> page(MiniPmsSpuRatePageDTO params) {
        IPage<MiniPmsSpuRatePageVO> result = this.pmsSpuRateMapper.selectDataList(
                new Page<>(), params
        );
        List<MiniPmsSpuRatePageVO> list = result.getRecords();
        list.forEach(MiniPmsSpuRatePageVO::handleData);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(String spuRateId) {
        this.pmsSpuRateMapper.deleteById(spuRateId);
    }


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
                .resourceList(item.getResourceList())
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
        this.iOmsOrderItemService.updateBatchIsRate(orderItemIdList, true);
    }

}
