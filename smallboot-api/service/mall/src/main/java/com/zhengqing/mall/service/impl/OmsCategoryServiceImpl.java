package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.mall.entity.PmsCategory;
import com.zhengqing.mall.mapper.PmsCategoryMapper;
import com.zhengqing.mall.model.dto.*;
import com.zhengqing.mall.model.vo.*;
import com.zhengqing.mall.service.IOmsCategoryService;
import com.zhengqing.mall.service.IPmsCategorySpuRelationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 商城-分类 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 14:01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OmsCategoryServiceImpl extends ServiceImpl<PmsCategoryMapper, PmsCategory> implements IOmsCategoryService {

    private final PmsCategoryMapper pmsCategoryMapper;
    @Lazy
    @Resource
    private IPmsCategorySpuRelationService iPmsCategorySpuRelationService;

    @Override
    public PmsCategory detail(String id) {
        PmsCategory detailData = this.pmsCategoryMapper.selectById(id);
        Assert.notNull(detailData, "该分类数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<String> idList) {
        this.pmsCategoryMapper.delete(
                new LambdaQueryWrapper<PmsCategory>().in(PmsCategory::getId, idList)
        );
    }

    @Override
    public IPage<MiniPmsCategoryPageVO> page(MiniPmsCategoryPageDTO params) {
        return this.pmsCategoryMapper.selectPageForMini(new Page<>(), params);
    }

    @Override
    public List<MiniPmsCategoryListVO> list(MiniPmsCategoryListDTO params) {
        return this.pmsCategoryMapper.selectListForMini(params);
    }

    @Override
    public List<MiniPmsCategoryReSpuListVO> reSpuList(MiniPmsCategoryReSpuListDTO params) {
        List<MiniPmsCategoryReSpuListVO> categoryReSpuList = this.pmsCategoryMapper.selectReSpuDataListForMini(params);
        if (CollectionUtils.isEmpty(categoryReSpuList)) {
            return Lists.newArrayList();
        }
        this.handleReSpuData(categoryReSpuList);
        return categoryReSpuList;
    }

    @Override
    public IPage<MiniPmsCategoryReSpuListVO> reSpuPage(MiniPmsCategoryReSpuListDTO params) {
        IPage<MiniPmsCategoryReSpuListVO> categoryReSpuPage = this.pmsCategoryMapper.selectReSpuDataListForMini(new Page<>(), params);
        List<MiniPmsCategoryReSpuListVO> categoryReSpuList = categoryReSpuPage.getRecords();
        if (CollectionUtils.isEmpty(categoryReSpuList)) {
            return categoryReSpuPage;
        }
        this.handleReSpuData(categoryReSpuList);
        return categoryReSpuPage;
    }

    private void handleReSpuData(List<MiniPmsCategoryReSpuListVO> categoryReSpuList) {
        // 分类ids
        List<String> categoryIdList = categoryReSpuList.stream().map(MiniPmsCategoryReSpuListVO::getId).collect(Collectors.toList());
        Map<String, List<PmsCategoryReSpuListVO>> categoryReSpuMap = this.iPmsCategorySpuRelationService.mapByCategoryIdList(categoryIdList);
        categoryReSpuList.forEach(item -> {
            item.setSpuList(categoryReSpuMap.get(item.getId()));
            item.handleData();
        });
    }

    @Override
    public IPage<WebPmsCategoryPageVO> page(WebPmsCategoryPageDTO params) {
        IPage<WebPmsCategoryPageVO> result = this.pmsCategoryMapper.selectPageForWeb(
                new Page<>(), params);
        List<WebPmsCategoryPageVO> list = result.getRecords();
        this.handleResultData(list);
        return result;
    }

    @Override
    public List<WebPmsCategoryListVO> list(WebPmsCategoryListDTO params) {
        return this.pmsCategoryMapper.selectDataList(params);
    }

    /**
     * 处理数据
     *
     * @param list 数据
     * @return void
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    private void handleResultData(List<WebPmsCategoryPageVO> list) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addOrUpdateData(WebPmsCategorySaveDTO params) {
        String id = params.getId();
        String parentId = params.getParentId();
        String name = params.getName();
        Integer sort = params.getSort();
        Boolean isShow = params.getIsShow();

        // 校验名称是否重复
        PmsCategory pmsCategoryOld = this.pmsCategoryMapper.selectOne(new LambdaQueryWrapper<PmsCategory>()
                .eq(PmsCategory::getName, name)
                .last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(pmsCategoryOld == null || pmsCategoryOld.getId().equals(id), "名称重复，请重新输入！");

        // 保存数据
        PmsCategory pmsCategory = PmsCategory.builder()
                .id(id)
                .parentId(parentId)
                .name(name)
                .sort(sort)
                .isShow(isShow)
                .build();

        if (id == null) {
            // 新增
            id = IdGeneratorUtil.nextStrId();
            pmsCategory.setId(id);
            pmsCategory.insert();
        } else {
            // 更新
            pmsCategory.updateById();
        }
        return id;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatchForBusiness(List<String> idList) {
        // 1、删除绑定关联商品数据
        this.iPmsCategorySpuRelationService.deleteDataByCategoryIds(idList);
        // 2、删除自身分类数据
        this.deleteBatch(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchShow(WebPmsCategoryEditShowDTO params) {
        List<String> idList = params.getIdList();
        Boolean isShow = params.getIsShow();
        log.info("[商城] 批量更新分类显示状态 分类ids:{} 是否上架：{}", idList, isShow);
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        this.pmsCategoryMapper.updateBatchShow(idList, isShow);
    }

}
