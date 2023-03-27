package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.zhengqing.mall.entity.PmsCategorySpuRelation;
import com.zhengqing.mall.mapper.PmsCategorySpuRelationMapper;
import com.zhengqing.mall.service.MiniPmsCategorySpuRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> 商城-分类关联商品 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
@Slf4j
@Service
public class MiniPmsCategorySpuRelationServiceImpl extends PmsCategorySpuRelationServiceImpl<PmsCategorySpuRelationMapper, PmsCategorySpuRelation> implements MiniPmsCategorySpuRelationService {


    @Resource
    private PmsCategorySpuRelationMapper pmsCategorySpuRelationMapper;

    @Override
    public void checkExistReData(String categoryId, String spuId) {
        Assert.notBlank(categoryId, "分类id不能为空！");
        Assert.notBlank(spuId, "商品id不能为空！");
        int num = this.pmsCategorySpuRelationMapper.selectReData(categoryId, spuId);
        Assert.isTrue(num > 0, "找不到该商品了，请重试！");
    }

}
