package com.zhengqing.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zhengqing.mall.model.vo.PmsCategoryReSpuListVO;
import com.zhengqing.mall.entity.PmsCategory;
import com.zhengqing.mall.mapper.PmsCategoryMapper;
import com.zhengqing.mall.model.dto.MiniPmsCategoryListDTO;
import com.zhengqing.mall.model.dto.MiniPmsCategoryPageDTO;
import com.zhengqing.mall.model.dto.MiniPmsCategoryReSpuListDTO;
import com.zhengqing.mall.model.vo.MiniPmsCategoryListVO;
import com.zhengqing.mall.model.vo.MiniPmsCategoryPageVO;
import com.zhengqing.mall.model.vo.MiniPmsCategoryReSpuListVO;
import com.zhengqing.mall.service.MiniOmsCategoryService;
import com.zhengqing.mall.service.MiniPmsCategorySpuRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
 * @date 2021/08/17 18:22
 */
@Slf4j
@Service
public class MiniOmsCategoryServiceImpl extends OmsCategoryServiceImpl<PmsCategoryMapper, PmsCategory> implements MiniOmsCategoryService {

    @Resource
    private PmsCategoryMapper pmsCategoryMapper;

    @Resource
    private MiniPmsCategorySpuRelationService miniPmsCategorySpuRelationService;

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
        Map<String, List<PmsCategoryReSpuListVO>> categoryReSpuMap = this.miniPmsCategorySpuRelationService.mapByCategoryIdList(categoryIdList);
        categoryReSpuList.forEach(item -> {
            item.setSpuList(categoryReSpuMap.get(item.getId()));
            item.handleData();
        });
    }

}
