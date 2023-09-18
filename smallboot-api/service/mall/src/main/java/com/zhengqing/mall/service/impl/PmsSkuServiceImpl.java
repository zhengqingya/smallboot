package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.mall.entity.PmsSku;
import com.zhengqing.mall.mapper.PmsSkuMapper;
import com.zhengqing.mall.model.bo.PmsSkuBO;
import com.zhengqing.mall.model.bo.PmsSkuStockBO;
import com.zhengqing.mall.model.dto.WebPmsSpuEditVirtualUseStockDTO;
import com.zhengqing.mall.service.IPmsSkuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p> 商城-商品规格 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PmsSkuServiceImpl extends ServiceImpl<PmsSkuMapper, PmsSku> implements IPmsSkuService {

    private final PmsSkuMapper pmsSkuMapper;

    @Override
    public PmsSku getSku(String skuId) {
        PmsSku pmsSku = this.pmsSkuMapper.selectById(skuId);
        Assert.notNull(pmsSku, "该商品sku不存在！");
        return pmsSku;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsertOrUpdate(List<PmsSku> skuList) {
        if (CollectionUtils.isEmpty(skuList)) {
            return;
        }
        this.saveOrUpdateBatch(skuList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSkuStock(List<PmsSkuStockBO> skuList) {
        if (CollectionUtils.isEmpty(skuList)) {
            return false;
        }
        try {
            return this.pmsSkuMapper.updateSkuStock(skuList) > 0;
        } catch (Exception e) {
            throw new MyException("库存不足！");
        }
    }

    @Override
    public List<PmsSkuBO> listBySpuId(String spuId) {
        return this.mapBySpuId(Lists.newArrayList(spuId)).get(spuId);
    }

    @Override
    public Map<String, List<PmsSkuBO>> mapBySpuId(List<String> spuIdList) {
        Map<String, List<PmsSkuBO>> resultMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(spuIdList)) {
            return resultMap;
        }
        List<PmsSkuBO> skuList = this.pmsSkuMapper.selectDataListBySpuIdList(spuIdList);
        for (PmsSkuBO item : skuList) {
            resultMap.computeIfAbsent(item.getSpuId(), k -> new LinkedList<>()).add(item);
        }
        return resultMap;
    }

    @Override
    public Map<String, List<PmsSku>> map(List<String> spuIdList) {
        Map<String, List<PmsSku>> resultMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(spuIdList)) {
            return resultMap;
        }
        List<PmsSku> skuList = this.pmsSkuMapper.selectList(
                new LambdaQueryWrapper<PmsSku>().in(PmsSku::getSpuId, spuIdList)
        );
        for (PmsSku item : skuList) {
            resultMap.computeIfAbsent(item.getSpuId(), k -> new LinkedList<>()).add(item);
        }
        return resultMap;
    }

    @Override
    public List<PmsSku> listByIdList(List<String> skuIdList) {
        if (CollectionUtils.isEmpty(skuIdList)) {
            return Lists.newLinkedList();
        }
        return this.pmsSkuMapper.selectBatchIds(skuIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(String skuId) {
        super.removeById(skuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDataBySpuIdList(List<String> spuIdList) {
        Assert.notNull(spuIdList, "删除规格时，商品id不能为空！");
        this.pmsSkuMapper.delete(new LambdaQueryWrapper<PmsSku>()
                .in(PmsSku::getSpuId, spuIdList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchVirtualUseStock(List<WebPmsSpuEditVirtualUseStockDTO> list) {
        long updateNum = this.pmsSkuMapper.updateBatchVirtualUseStock(list);
        Assert.isTrue(updateNum == list.size(), "数据不存在或存在更新数据超过可用库存，请检查后再提交！");
    }

}
