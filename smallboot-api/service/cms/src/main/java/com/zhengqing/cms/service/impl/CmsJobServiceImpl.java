package com.zhengqing.cms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.cms.entity.CmsJob;
import com.zhengqing.cms.mapper.CmsJobMapper;
import com.zhengqing.cms.model.dto.CmsJobPageDTO;
import com.zhengqing.cms.model.dto.CmsJobSaveDTO;
import com.zhengqing.cms.model.vo.CmsJobPageVO;
import com.zhengqing.cms.service.ICmsJobService;
import com.zhengqing.cms.service.ICmsJobTagService;
import com.zhengqing.common.db.constant.MybatisConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 内容管理-招聘岗位 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CmsJobServiceImpl extends ServiceImpl<CmsJobMapper, CmsJob> implements ICmsJobService {

    private final CmsJobMapper cmsJobMapper;
    private final ICmsJobTagService iCmsJobTagService;

    @Override
    public IPage<CmsJobPageVO> page(CmsJobPageDTO params) {
        IPage<CmsJobPageVO> result = this.cmsJobMapper.selectDataPage(new Page<>(), params);
        List<CmsJobPageVO> list = result.getRecords();
        if (CollUtil.isEmpty(list)) {
            return result;
        }
        // 拿到标签名称
        List<Integer> tagIdList = Lists.newArrayList();
        list.forEach(e -> tagIdList.addAll(e.getTagList()));
        Map<Integer, String> tagMap = this.iCmsJobTagService.map(tagIdList);
        list.forEach(e -> {
            e.setTagNameList(e.getTagList().stream().map(tagMap::get).collect(Collectors.toList()));
            e.handleData();
        });
        return result;
    }

    @Override
    public CmsJobPageVO detail(CmsJobPageDTO params) {
        List<CmsJobPageVO> list = this.page(params).getRecords();
        Assert.notEmpty(list, "数据不存在！");
        return list.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(CmsJobSaveDTO params) {
        // 校验名称是否重复
        CmsJob oldData = this.cmsJobMapper.selectOne(new LambdaQueryWrapper<CmsJob>().eq(CmsJob::getName, params.getName()).last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(oldData == null || oldData.getId().equals(params.getId()), "名称重复，请重新输入！");

        CmsJob.builder()
                .id(params.getId())
                .deptId(params.getDeptId())
                .name(params.getName())
                .postId(params.getPostId())
                .categoryId(params.getCategoryId())
                .sort(params.getSort())
                .status(params.getStatus())
                .contact(params.getContact())
                .contactPhone(params.getContactPhone())
                .provinceName(params.getProvinceName())
                .cityName(params.getCityName())
                .areaName(params.getAreaName())
                .address(params.getAddress())
                .tagList(params.getTagList())
                .intro(params.getIntro())
                .userNum(params.getUserNum())
                .wageStart(params.getWageStart())
                .wageEnd(params.getWageEnd())
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.cmsJobMapper.deleteById(id);
    }

}
