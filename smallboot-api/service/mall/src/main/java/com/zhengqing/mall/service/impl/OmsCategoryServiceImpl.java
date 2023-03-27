package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.mall.entity.PmsCategory;
import com.zhengqing.mall.mapper.PmsCategoryMapper;
import com.zhengqing.mall.service.OmsCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 商城-分类 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 14:01
 */
@Slf4j
@Service
public class OmsCategoryServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<PmsCategoryMapper, PmsCategory> implements OmsCategoryService<PmsCategory> {

    @Resource
    private PmsCategoryMapper pmsCategoryMapper;

    @Override
    public PmsCategory detail(String id) {
        PmsCategory detailData = this.pmsCategoryMapper.selectById(id);
        Assert.notNull(detailData, "该分类数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<String> idList) {
        this.pmsCategoryMapper.delete(new LambdaQueryWrapper<PmsCategory>()
                .in(PmsCategory::getId, idList));
    }


}
