package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.system.entity.SysPost;
import com.zhengqing.system.mapper.SysPostMapper;
import com.zhengqing.system.model.dto.SysPostListDTO;
import com.zhengqing.system.model.dto.SysPostPageDTO;
import com.zhengqing.system.model.dto.SysPostSaveDTO;
import com.zhengqing.system.model.vo.SysPostListVO;
import com.zhengqing.system.model.vo.SysPostPageVO;
import com.zhengqing.system.service.ISysPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> 系统管理-岗位 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 17:49
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

    private final SysPostMapper sysPostMapper;

    @Override
    public IPage<SysPostPageVO> page(SysPostPageDTO params) {
        return this.sysPostMapper.selectDataPage(new Page<>(), params);
    }

    @Override
    public List<SysPostListVO> list(SysPostListDTO params) {
        return this.sysPostMapper.selectDataList(params);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SysPostSaveDTO params) {
        Integer id = params.getId();
        String name = params.getName();
        // 校验名称是否重复
        SysPost sysPostOld = this.sysPostMapper.selectOne(new LambdaQueryWrapper<SysPost>().eq(SysPost::getName, name).last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysPostOld == null || sysPostOld.getId().equals(id), "名称重复，请重新输入！");

        SysPost.builder()
                .id(id)
                .deptId(params.getDeptId())
                .name(name)
                .code(params.getCode())
                .status(params.getStatus())
                .sort(params.getSort())
                .remark(params.getRemark())
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.sysPostMapper.deleteById(id);
    }

}
