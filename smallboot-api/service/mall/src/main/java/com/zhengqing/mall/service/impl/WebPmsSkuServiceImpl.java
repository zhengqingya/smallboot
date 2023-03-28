package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.zhengqing.mall.entity.PmsSku;
import com.zhengqing.mall.mapper.PmsSkuMapper;
import com.zhengqing.mall.model.dto.WebPmsSpuEditVirtualUseStockDTO;
import com.zhengqing.mall.service.WebPmsSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 商城-商品规格 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
@Slf4j
@Service
public class WebPmsSkuServiceImpl extends PmsSkuServiceImpl<PmsSkuMapper, PmsSku> implements WebPmsSkuService {

    @Resource
    private PmsSkuMapper pmsSkuMapper;


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
