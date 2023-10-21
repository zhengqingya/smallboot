package com.zhengqing.cms.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.zhengqing.cms.entity.CmsJobTag;
import com.zhengqing.cms.mapper.CmsJobTagMapper;
import com.zhengqing.cms.model.dto.CmsJobTagListDTO;
import com.zhengqing.cms.model.dto.CmsJobTagPageDTO;
import com.zhengqing.cms.model.dto.CmsJobTagSaveDTO;
import com.zhengqing.cms.model.vo.CmsJobTagListVO;
import com.zhengqing.cms.model.vo.CmsJobTagPageVO;
import com.zhengqing.cms.service.ICmsJobTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 内容管理-招聘岗位标签 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CmsJobTagServiceImpl extends ServiceImpl<CmsJobTagMapper, CmsJobTag> implements ICmsJobTagService {

    private final CmsJobTagMapper cmsJobTagMapper;

    @Override
    public IPage<CmsJobTagPageVO> page(CmsJobTagPageDTO params) {
        return this.cmsJobTagMapper.selectDataPage(new Page<>(), params);
    }

    @Override
    public List<CmsJobTagListVO> list(CmsJobTagListDTO params) {
        return this.cmsJobTagMapper.selectDataList(params);
    }

    @Override
    public Map<Integer, String> map(List<Integer> idList) {
        idList = idList.stream().distinct().collect(Collectors.toList());
        if (CollUtil.isEmpty(idList)) {
            return Maps.newHashMap();
        }
        List<CmsJobTag> list = this.cmsJobTagMapper.selectList(new LambdaQueryWrapper<CmsJobTag>().in(CmsJobTag::getId, idList));
        return list.stream().collect(Collectors.toMap(CmsJobTag::getId, CmsJobTag::getName, (oldData, newData) -> newData));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(CmsJobTagSaveDTO params) {
        // 校验名称是否重复
//        CmsJobTag oldData = this.cmsJobTagMapper.selectOne(new LambdaQueryWrapper<CmsJobTag>().eq(CmsJobTag::getName, params.getName()).last(MybatisConstant.LIMIT_ONE));
//        Assert.isTrue(oldData == null || oldData.getId().equals(params.getId()), "名称重复，请重新输入！");

        CmsJobTag.builder()
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
        this.cmsJobTagMapper.deleteById(id);
    }

}
