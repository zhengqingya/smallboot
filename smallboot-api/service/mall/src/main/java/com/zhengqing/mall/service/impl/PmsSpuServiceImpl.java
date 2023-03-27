package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.mall.common.model.bo.MallDictBO;
import com.zhengqing.mall.common.model.bo.PmsSkuStockBO;
import com.zhengqing.mall.common.model.dto.PmsSkuDTO;
import com.zhengqing.mall.common.model.vo.PmsSkuVO;
import com.zhengqing.mall.common.model.vo.PmsSpuDetailVO;
import com.zhengqing.mall.entity.PmsSku;
import com.zhengqing.mall.entity.PmsSpu;
import com.zhengqing.mall.mapper.PmsSpuMapper;
import com.zhengqing.mall.service.PmsSkuService;
import com.zhengqing.mall.service.PmsSpuService;
import com.zhengqing.system.enums.SysDictTypeEnum;
import com.zhengqing.system.model.vo.SysDictVO;
import com.zhengqing.system.service.ISysDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 商城-商品 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@Slf4j
@Service
public class PmsSpuServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<PmsSpuMapper, PmsSpu> implements PmsSpuService<PmsSpu> {

    @Resource
    private PmsSpuMapper pmsSpuMapper;

    @Resource
    @Qualifier("pmsSkuServiceImpl")
    private PmsSkuService<PmsSku> pmsSkuService;

    @Resource
    private ISysDictService sysDictService;

    @Override
    public PmsSpu getSpu(String id) {
        PmsSpu pmsSpu = this.pmsSpuMapper.selectById(id);
        Assert.notNull(pmsSpu, "该商品不存在！");
        return pmsSpu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSkuStock(List<PmsSkuStockBO> skuList) {
        // 加锁
//        RLock redisLock = RedisUtil.lock(MallRedisConstant.SPU_SKU_STOCK_LOCK, 5, TimeUnit.SECONDS);
        try {
            return this.pmsSkuService.updateSkuStock(skuList);
        } finally {
            // 释放锁
//            redisLock.unlock();
        }
    }

    @Override
    public void handleSpuData(PmsSpuDetailVO spuDetail) {
        List<MallDictBO> serviceList = new ObjectMapper().convertValue(spuDetail.getServiceList(), new TypeReference<List<MallDictBO>>() {
        });
        List<MallDictBO> explainList = new ObjectMapper().convertValue(spuDetail.getExplainList(), new TypeReference<List<MallDictBO>>() {
        });
        List<String> serviceValueList = serviceList.stream().map(e -> e.getValue()).collect(Collectors.toList());
        List<String> explainValueList = explainList.stream().map(e -> e.getValue()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(serviceValueList) && CollectionUtils.isEmpty(explainValueList)) {
            return;
        }
        // 查询服务和说明相关数据字典缓存
        List<String> codeList = Lists.newArrayList(SysDictTypeEnum.MALL_SPU_SERVICE.getCode(), SysDictTypeEnum.MALL_SPU_EXPLAIN.getCode());
        Map<String, List<SysDictVO>> dictDataMap = this.sysDictService.listByOpenCode(codeList);
        // 服务
        if (!CollectionUtils.isEmpty(serviceValueList)) {
            List<SysDictVO> dictListByService = dictDataMap.get(SysDictTypeEnum.MALL_SPU_SERVICE.getCode());
            List<SysDictVO> serviceDictList = dictListByService.stream().filter(e -> serviceValueList.contains(e.getValue())).collect(Collectors.toList());
            spuDetail.setServiceList(this.toDictBO(serviceDictList));
        }
        // 说明
        List<SysDictVO> dictListByExplain = dictDataMap.get(SysDictTypeEnum.MALL_SPU_EXPLAIN.getCode());
        if (!CollectionUtils.isEmpty(explainValueList)) {
            List<SysDictVO> explainDictList = dictListByExplain.stream().filter(e -> explainValueList.contains(e.getValue())).collect(Collectors.toList());
            spuDetail.setExplainList(this.toDictBO(explainDictList));
        }
    }

    /**
     * 数据字典转换
     *
     * @param dictVOList 数据字典值
     * @return 转换后的值
     * @author zhengqingya
     * @date 2021/12/22 10:35 下午
     */
    private List<MallDictBO> toDictBO(List<SysDictVO> dictVOList) {
        List<MallDictBO> resultList = Lists.newLinkedList();
        if (CollectionUtils.isEmpty(dictVOList)) {
            return resultList;
        }
        dictVOList.forEach(item -> resultList.add(MallDictBO.builder()
                .code(item.getCode())
                .name(item.getName())
                .value(item.getValue())
                .sort(item.getSort())
                .remark(item.getRemark())
                .build()));
        return resultList;
    }

    @Override
    public List<PmsSkuVO> listBySku(PmsSkuDTO params) {
        List<String> skuIdList = params.getSkuIdList();
        if (CollectionUtils.isEmpty(skuIdList)) {
            return Lists.newArrayList();
        }
        List<PmsSkuVO> pmsSkuList = this.pmsSpuMapper.selectSkuList(params);
        if (!CollectionUtils.isEmpty(pmsSkuList)) {
            pmsSkuList.forEach(item -> item.handleData());
        }
        return pmsSkuList;
    }

    @Override
    public Map<String, PmsSkuVO> mapBySku(PmsSkuDTO params) {
        List<String> skuIdList = params.getSkuIdList();
        if (CollectionUtils.isEmpty(skuIdList)) {
            return Maps.newHashMap();
        }
        return this.listBySku(params).stream().collect(Collectors.toMap(PmsSkuVO::getSkuId, t -> t, (k1, k2) -> k1));
    }

    @Override
    public PmsSkuVO sku(String skuId) {
        return this.mapBySku(PmsSkuDTO.builder().skuIdList(Lists.newArrayList(skuId)).build()).get(skuId);
    }

}
