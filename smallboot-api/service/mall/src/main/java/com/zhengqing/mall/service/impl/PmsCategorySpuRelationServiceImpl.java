package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.mall.entity.PmsCategorySpuRelation;
import com.zhengqing.mall.entity.PmsSpu;
import com.zhengqing.mall.mapper.PmsCategorySpuRelationMapper;
import com.zhengqing.mall.model.bo.MiniPmsCategoryReSkuBO;
import com.zhengqing.mall.model.bo.PmsSkuBO;
import com.zhengqing.mall.model.dto.*;
import com.zhengqing.mall.model.vo.PmsCategoryReSpuListVO;
import com.zhengqing.mall.model.vo.WebPmsCategorySpuRelationListVO;
import com.zhengqing.mall.model.vo.WebPmsCategorySpuRelationPageVO;
import com.zhengqing.mall.service.IOmsCategoryService;
import com.zhengqing.mall.service.IPmsCategorySpuRelationService;
import com.zhengqing.mall.service.IPmsSkuService;
import com.zhengqing.mall.service.IPmsSpuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@RequiredArgsConstructor
public class PmsCategorySpuRelationServiceImpl extends ServiceImpl<PmsCategorySpuRelationMapper, PmsCategorySpuRelation> implements IPmsCategorySpuRelationService {

    private final PmsCategorySpuRelationMapper pmsCategorySpuRelationMapper;
    @Lazy
    @Resource
    private IPmsSpuService iPmsSpuService;
    @Lazy
    @Resource
    private IOmsCategoryService iOmsCategoryService;
    private final IPmsSkuService iPmsSkuService;

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
        Map<String, List<PmsSkuBO>> skuDataMap = this.iPmsSkuService.mapBySpuId(spuIdList);
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

    @Override
    public void checkExistReData(String categoryId, String spuId) {
        Assert.notBlank(categoryId, "分类id不能为空！");
        Assert.notBlank(spuId, "商品id不能为空！");
        int num = this.pmsCategorySpuRelationMapper.selectReData(categoryId, spuId);
        Assert.isTrue(num > 0, "找不到该商品了，请重试！");
    }

    @Override
    public IPage<WebPmsCategorySpuRelationPageVO> page(WebPmsCategorySpuRelationPageDTO params) {
        return this.pmsCategorySpuRelationMapper.selectDataPage(
                new Page<>(), params);
    }

    @Override
    public List<WebPmsCategorySpuRelationListVO> list(WebPmsCategorySpuRelationListDTO params) {
        return this.pmsCategorySpuRelationMapper.selectDataList(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addOrUpdateData(WebPmsCategorySpuRelationSaveDTO params) {
        String id = params.getId();
        String categoryId = params.getCategoryId();
        String spuId = params.getSpuId();
        Integer sort = params.getSort();
        Boolean isShow = params.getIsShow();
        Boolean isPut = params.getIsPut();
        // 1、校验分类数据是否存在
        this.iOmsCategoryService.detail(categoryId);
        // 2、校验商品数据是否存在 & 是否显示或上架
        PmsSpu spu = this.iPmsSpuService.getSpu(spuId);
        Assert.isTrue(spu.getIsShow(), "商品未显示，无法添加数据！");
        Assert.isTrue(spu.getIsPut(), "商品未上架，无法添加数据！");
        // 3、校验之前是否已经存在绑定关系 -- 一对一
        PmsCategorySpuRelation pmsCategorySpuRelationOld = this.pmsCategorySpuRelationMapper.selectOne(
                new LambdaQueryWrapper<PmsCategorySpuRelation>()
                        .eq(PmsCategorySpuRelation::getSpuId, spuId)
                        .last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(pmsCategorySpuRelationOld == null || pmsCategorySpuRelationOld.getId().equals(id), "已存在绑定关系，请重新选择！");
        // 4、新增分类和商品关联数据
        if (id == null) {
            id = IdGeneratorUtil.nextStrId();
        }
        PmsCategorySpuRelation.builder()
                .id(id)
                .categoryId(categoryId)
                .spuId(spuId)
                .sort(sort)
                .isShow(isShow)
                .isPut(isPut)
                .build().insertOrUpdate();
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<String> idList) {
        this.pmsCategorySpuRelationMapper.deleteBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDataByCategoryIds(List<String> categoryIdList) {
        this.pmsCategorySpuRelationMapper.delete(new LambdaQueryWrapper<PmsCategorySpuRelation>()
                .in(PmsCategorySpuRelation::getCategoryId, categoryIdList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDataBySpuId(List<String> spuIdList) {
        this.pmsCategorySpuRelationMapper.delete(new LambdaQueryWrapper<PmsCategorySpuRelation>()
                .in(PmsCategorySpuRelation::getSpuId, spuIdList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchShow(WebPmsCategorySpuRelationEditShowDTO params) {
        List<String> idList = params.getIdList();
        Boolean isShow = params.getIsShow();
        log.info("[商城] 批量更新分类关联商品显示状态 ids:{} 是否显示：{}", idList, isShow);
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        List<PmsCategorySpuRelation> categoryReSpuList = this.pmsCategorySpuRelationMapper.selectBatchIds(idList);
        // 校验商品数据
        categoryReSpuList.forEach(item -> {
            PmsSpu spu = this.iPmsSpuService.getSpu(item.getSpuId());
            Assert.isTrue(spu.getIsShow(), "商品未显示，无法修改数据！");
        });
        // 批量更新
        this.pmsCategorySpuRelationMapper.updateBatchShow(idList, isShow);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchPut(WebPmsCategorySpuRelationEditPutDTO params) {
        List<String> idList = params.getIdList();
        Boolean isPut = params.getIsPut();
        log.info("[商城] 批量更新分类关联商品上下架状态 ids:{} 是否上架：{}", idList, isPut);
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        List<PmsCategorySpuRelation> categoryReSpuList = this.pmsCategorySpuRelationMapper.selectBatchIds(idList);
        // 校验商品数据
        categoryReSpuList.forEach(item -> {
            PmsSpu spu = this.iPmsSpuService.getSpu(item.getSpuId());
            Assert.isTrue(spu.getIsPut(), "商品未上架，无法修改数据！");
        });
        // 批量更新
        this.pmsCategorySpuRelationMapper.updateBatchPut(idList, isPut);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchShowBySpuIds(List<String> spuIdList, boolean isShow) {
        log.info("[商城] 根据商品ids批量更新分类关联商品显示状态 ids:{} 是否显示：{}", spuIdList, isShow);
        if (CollectionUtils.isEmpty(spuIdList)) {
            return;
        }
        this.pmsCategorySpuRelationMapper.updateBatchShowBySpuIds(spuIdList, isShow);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchPutBySpuIds(List<String> spuIdList, boolean isPut) {
        log.info("[商城] 根据商品ids批量更新分类关联商品上下架状态 ids:{} 是否上架：{}", spuIdList, isPut);
        if (CollectionUtils.isEmpty(spuIdList)) {
            return;
        }
        this.pmsCategorySpuRelationMapper.updateBatchPutBySpuIds(spuIdList, isPut);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchSort(List<WebPmsCategorySpuRelationEditSortDTO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        this.pmsCategorySpuRelationMapper.updateBatchSort(list);
    }

}
