package com.zhengqing.cms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.cms.entity.CmsJobCategory;
import com.zhengqing.cms.mapper.CmsJobCategoryMapper;
import com.zhengqing.cms.model.dto.CmsJobCategoryListDTO;
import com.zhengqing.cms.model.dto.CmsJobCategoryPageDTO;
import com.zhengqing.cms.model.dto.CmsJobCategorySaveDTO;
import com.zhengqing.cms.model.vo.CmsJobCategoryListVO;
import com.zhengqing.cms.model.vo.CmsJobCategoryPageVO;
import com.zhengqing.cms.service.ICmsJobCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> 内容管理-招聘岗位分类 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CmsJobCategoryServiceImpl extends ServiceImpl<CmsJobCategoryMapper, CmsJobCategory> implements ICmsJobCategoryService {

    private final CmsJobCategoryMapper cmsJobCategoryMapper;

    @Override
    public IPage<CmsJobCategoryPageVO> page(CmsJobCategoryPageDTO params) {
        return this.cmsJobCategoryMapper.selectDataPage(new Page<>(), params);
    }

    @Override
    public List<CmsJobCategoryListVO> list(CmsJobCategoryListDTO params) {
        return this.cmsJobCategoryMapper.selectDataList(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(CmsJobCategorySaveDTO params) {
        // 校验名称是否重复
//        CmsJobCategory oldData = this.cmsJobCategoryMapper.selectOne(new LambdaQueryWrapper<CmsJobCategory>().eq(CmsJobCategory::getName, params.getName()).last(MybatisConstant.LIMIT_ONE));
//        Assert.isTrue(oldData == null || oldData.getId().equals(params.getId()), "名称重复，请重新输入！");

        CmsJobCategory.builder()
                .id(params.getId())
                .deptId(params.getDeptId())
                .name(params.getName())
                .sort(params.getSort())
                .status(params.getStatus())
                .remark(params.getRemark())
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.cmsJobCategoryMapper.deleteById(id);
    }

}
