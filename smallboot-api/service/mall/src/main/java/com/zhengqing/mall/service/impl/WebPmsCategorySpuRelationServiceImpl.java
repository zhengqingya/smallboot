package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.mall.entity.PmsCategorySpuRelation;
import com.zhengqing.mall.entity.PmsSpu;
import com.zhengqing.mall.mapper.PmsCategorySpuRelationMapper;
import com.zhengqing.mall.model.dto.*;
import com.zhengqing.mall.model.vo.WebPmsCategorySpuRelationListVO;
import com.zhengqing.mall.model.vo.WebPmsCategorySpuRelationPageVO;
import com.zhengqing.mall.service.WebOmsCategoryService;
import com.zhengqing.mall.service.WebPmsCategorySpuRelationService;
import com.zhengqing.mall.service.WebPmsSpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 商城-分类关联商品 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
@Slf4j
@Service
public class WebPmsCategorySpuRelationServiceImpl extends PmsCategorySpuRelationServiceImpl<PmsCategorySpuRelationMapper, PmsCategorySpuRelation> implements WebPmsCategorySpuRelationService {

    @Resource
    private PmsCategorySpuRelationMapper pmsCategorySpuRelationMapper;

    @Lazy
    @Resource
    private WebPmsSpuService webPmsSpuService;

    @Resource
    private WebOmsCategoryService webPmsCategoryService;

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
        this.webPmsCategoryService.detail(categoryId);
        // 2、校验商品数据是否存在 & 是否显示或上架
        PmsSpu spu = this.webPmsSpuService.getSpu(spuId);
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
            PmsSpu spu = this.webPmsSpuService.getSpu(item.getSpuId());
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
            PmsSpu spu = this.webPmsSpuService.getSpu(item.getSpuId());
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
