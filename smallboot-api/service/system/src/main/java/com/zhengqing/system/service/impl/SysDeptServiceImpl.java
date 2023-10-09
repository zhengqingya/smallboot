package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.system.entity.SysDept;
import com.zhengqing.system.mapper.SysDeptMapper;
import com.zhengqing.system.model.dto.SysDeptSaveDTO;
import com.zhengqing.system.model.dto.SysDeptTreeDTO;
import com.zhengqing.system.model.vo.SysDeptTreeVO;
import com.zhengqing.system.service.ISysDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> 系统管理-部门 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 18:10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    private final SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDeptTreeVO> tree(SysDeptTreeDTO params) {
        List<SysDeptTreeVO> list = this.sysDeptMapper.selectDataList(params);
        return this.recurveDept(AppConstant.PARENT_ID, list);
    }

    /**
     * 递归部门
     *
     * @param parentId 父id
     * @param allList  所有部门
     * @return 菜单树列表
     * @author zhengqingya
     * @date 2020/9/10 20:56
     */
    private List<SysDeptTreeVO> recurveDept(Integer parentId, List<SysDeptTreeVO> allList) {
        // 存放子集合
        List<SysDeptTreeVO> childList = allList.stream().filter(e -> e.getParentId().equals(parentId)).collect(Collectors.toList());
        // 递归
        childList.forEach(item -> {
            item.setChildren(this.recurveDept(item.getId(), allList));
            item.handleData();
        });
        return childList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SysDeptSaveDTO params) {
        Integer id = params.getId();
        String name = params.getName();
        // 校验名称是否重复
        SysDept sysDeptOld = this.sysDeptMapper.selectOne(new LambdaQueryWrapper<SysDept>().eq(SysDept::getName, name).last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysDeptOld == null || sysDeptOld.getId().equals(id), "名称重复，请重新输入！");


        SysDept.builder()
                .id(id)
                .parentId(params.getParentId())
                .name(name)
                .sort(params.getSort())
                .leaderUserId(params.getLeaderUserId())
                .phone(params.getPhone())
                .email(params.getEmail())
                .status(params.getStatus())
                .expireTime(params.getExpireTime())
                .provinceName(params.getProvinceName())
                .cityName(params.getCityName())
                .areaName(params.getAreaName())
                .address(params.getAddress())
                .remark(params.getRemark())
                .userNum(params.getUserNum())
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        this.sysDeptMapper.deleteById(id);
    }

}
