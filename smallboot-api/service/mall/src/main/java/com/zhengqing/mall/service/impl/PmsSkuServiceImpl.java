package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.mall.common.model.bo.PmsSkuBO;
import com.zhengqing.mall.common.model.bo.PmsSkuStockBO;
import com.zhengqing.mall.entity.PmsSku;
import com.zhengqing.mall.mapper.PmsSkuMapper;
import com.zhengqing.mall.service.PmsSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
@Transactional(rollbackFor = Exception.class)
public class PmsSkuServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<PmsSkuMapper, PmsSku> implements PmsSkuService<PmsSku> {

    @Resource
    private PmsSkuMapper pmsSkuMapper;

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

}
