package com.zhengqing.mall.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.mall.model.bo.PmsSkuBO;
import com.zhengqing.mall.model.dto.PmsCategoryReSpuListDTO;
import com.zhengqing.mall.model.vo.PmsCategoryReSpuListVO;
import com.zhengqing.mall.entity.PmsCategorySpuRelation;
import com.zhengqing.mall.entity.PmsSku;
import com.zhengqing.mall.mapper.PmsCategorySpuRelationMapper;
import com.zhengqing.mall.model.bo.MiniPmsCategoryReSkuBO;
import com.zhengqing.mall.service.PmsCategorySpuRelationService;
import com.zhengqing.mall.service.PmsSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 商城-分类关联商品 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 16:04
 */
@Slf4j
@Service
public class PmsCategorySpuRelationServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<PmsCategorySpuRelationMapper, PmsCategorySpuRelation> implements PmsCategorySpuRelationService<PmsCategorySpuRelation> {

    @Resource
    private PmsCategorySpuRelationMapper pmsCategorySpuRelationMapper;

    @Resource
    @Qualifier("pmsSkuServiceImpl")
    private PmsSkuService<PmsSku> pmsSkuService;

    @Override
    public List<PmsCategoryReSpuListVO> listByCategoryIdList(List<String> categoryIdList) {
        if (CollectionUtils.isEmpty(categoryIdList)) {
            return Lists.newLinkedList();
        }
        List<PmsCategoryReSpuListVO> categoryReSpuList = this.pmsCategorySpuRelationMapper.selectCategoryReSpu(
                PmsCategoryReSpuListDTO.builder()
                        .categoryIdList(categoryIdList)
                        .build());
        // 商品ids
        List<String> spuIdList = categoryReSpuList.stream().map(PmsCategoryReSpuListVO::getId).collect(Collectors.toList());
        // 查询关联规格数据
        Map<String, List<PmsSkuBO>> skuDataMap = this.pmsSkuService.mapBySpuId(spuIdList);
        // 封装数据
        categoryReSpuList.forEach(item -> {
            String id = item.getId();
            List<PmsSkuBO> miniPmsSkuList = skuDataMap.get(id);
            item.setSkuList(this.categorySku(miniPmsSkuList));
            // 处理数据
            item.handleData();
        });
        return categoryReSpuList;
    }

    /**
     * sku转换
     *
     * @param skuList 原始sku数据
     * @return 转换后sku
     * @author zhengqingya
     * @date 2022/3/15 16:46
     */
    private List<MiniPmsCategoryReSkuBO> categorySku(List<PmsSkuBO> skuList) {
        List<MiniPmsCategoryReSkuBO> resultSkuList = Lists.newLinkedList();
        skuList.forEach(item ->
                resultSkuList.add(MiniPmsCategoryReSkuBO.builder()
                        .id(item.getId())
                        .sort(item.getSort())
                        .sellPrice(item.getSellPrice())
                        .usableStock(item.getUsableStock())
                        .build()));
        return resultSkuList;
    }

    @Override
    public Map<String, List<PmsCategoryReSpuListVO>> mapByCategoryIdList(List<String> categoryIdList) {
        Map<String, List<PmsCategoryReSpuListVO>> resultMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(categoryIdList)) {
            return resultMap;
        }
        List<PmsCategoryReSpuListVO> categoryReSpuList = this.listByCategoryIdList(categoryIdList);
        for (PmsCategoryReSpuListVO item : categoryReSpuList) {
            resultMap.computeIfAbsent(item.getCategoryId(), k -> new LinkedList<>()).add(item);
        }
        return resultMap;
    }

}
