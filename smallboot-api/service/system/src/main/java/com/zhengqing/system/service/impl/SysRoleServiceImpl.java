package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.system.entity.SysRole;
import com.zhengqing.system.enums.SysRoleCodeEnum;
import com.zhengqing.system.mapper.SysRoleMapper;
import com.zhengqing.system.model.dto.SysRoleBaseDTO;
import com.zhengqing.system.model.dto.SysRoleSaveDTO;
import com.zhengqing.system.model.vo.SysRoleBaseVO;
import com.zhengqing.system.service.ISysRoleMenuService;
import com.zhengqing.system.service.ISysRoleScopeService;
import com.zhengqing.system.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统管理 - 角色管理 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 15:01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final ISysRoleMenuService iSysRoleMenuService;
    private final ISysRoleScopeService iSysRoleScopeService;

    @Override
    public IPage<SysRoleBaseVO> listPage(SysRoleBaseDTO params) {
        return this.sysRoleMapper.selectDataList(new Page<>(), params);
    }

    @Override
    public List<SysRoleBaseVO> list(SysRoleBaseDTO params) {
        return this.sysRoleMapper.selectDataList(params);
    }

    @Override
    public List<SysRoleBaseVO> tree(SysRoleBaseDTO params) {
        List<SysRoleBaseVO> list = this.list(params);
        if (CollUtil.isEmpty(list)) {
            return Lists.newArrayList();
        }
        Integer firstParentId = list.stream().map(SysRoleBaseVO::getParentId).min(Integer::compareTo).get();
        return this.recurveRole(firstParentId, list, params.getExcludeRoleId());
    }

    /**
     * 递归
     *
     * @param parentId      父id
     * @param allList       所有数据
     * @param excludeRoleId 排除指定角色id下级的数据
     * @return 菜单树列表
     * @author zhengqingya
     * @date 2020/9/10 20:56
     */
    private List<SysRoleBaseVO> recurveRole(Integer parentId, List<SysRoleBaseVO> allList, Integer excludeRoleId) {
        if (parentId.equals(excludeRoleId)) {
            return Lists.newArrayList();
        }
        // 存放子集合
        List<SysRoleBaseVO> childList = allList.stream().filter(e -> e.getParentId().equals(parentId)).collect(Collectors.toList());
        // 递归
        childList.forEach(item -> {
            item.setChildren(this.recurveRole(item.getRoleId(), allList, excludeRoleId));
            item.handleData();
        });
        return childList;
    }

    @Override
    public List<Integer> getChildRoleIdList(Integer roleId) {
        return this.recurveRoleId(roleId, this.list(), Lists.newArrayList());
    }

    /**
     * 递归
     *
     * @param parentId   父id
     * @param allList    所有数据
     * @param roleIdList 最终结果
     * @return 菜单树列表
     * @author zhengqingya
     * @date 2020/9/10 20:56
     */
    private List<Integer> recurveRoleId(Integer parentId, List<SysRole> allList, List<Integer> roleIdList) {
        roleIdList.add(parentId);
        List<SysRole> childList = allList.stream().filter(e -> e.getParentId().equals(parentId)).collect(Collectors.toList());
        if (CollUtil.isEmpty(childList)) {
            return roleIdList;
        }
        for (SysRole item : childList) {
            this.recurveRoleId(item.getRoleId(), allList, roleIdList);
        }
        return roleIdList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysRoleSaveDTO params) {
        // 校验名称是否重复
        SysRole sysRoleOld = this.sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getName, params.getName()).last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysRoleOld == null || !sysRoleOld.getIsFixed(), "您没有权限操作固定角色！");
        Assert.isTrue(sysRoleOld == null || sysRoleOld.getRoleId().equals(params.getRoleId()), "名称重复，请重新输入！");

        SysRole sysRole = SysRole.builder()
                .roleId(params.getRoleId())
                .parentId(params.getParentId())
                .name(params.getName())
                .code(params.getCode())
                .status(params.getStatus())
                .isFixed(params.getIsFixed())
                .sort(params.getSort())
                .build();
        sysRole.insertOrUpdate();
        return sysRole.getRoleId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleAndRoleMenu(Integer roleId) {
        SysRole sysRole = this.sysRoleMapper.selectById(roleId);
        Assert.isFalse(sysRole.getIsFixed(), "您没有权限删除固定角色！");
        // 1、删除该角色下关联的权限
        this.iSysRoleMenuService.delByRoleId(roleId);
        this.iSysRoleScopeService.delByRoleId(roleId);
        // 2、删除角色
        this.removeById(roleId);
    }

    @Override
    public Integer getRoleIdByCode(SysRoleCodeEnum sysRoleCodeEnum) {
        return this.sysRoleMapper.selectRoleIdByCode(sysRoleCodeEnum.getCode());
    }

}
