package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.system.entity.SysRole;
import com.zhengqing.system.enums.SysRoleCodeEnum;
import com.zhengqing.system.mapper.SysRoleMapper;
import com.zhengqing.system.model.dto.SysRoleListDTO;
import com.zhengqing.system.model.dto.SysRoleSaveDTO;
import com.zhengqing.system.model.vo.SysRoleListVO;
import com.zhengqing.system.service.ISysRoleMenuService;
import com.zhengqing.system.service.ISysRolePermissionService;
import com.zhengqing.system.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final ISysRolePermissionService iSysRolePermissionService;

    @Override
    public IPage<SysRoleListVO> listPage(SysRoleListDTO params) {
        return this.sysRoleMapper.selectDataList(new Page<>(), params);
    }

    @Override
    public List<SysRoleListVO> list(SysRoleListDTO params) {
        return this.sysRoleMapper.selectDataList(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysRoleSaveDTO params) {
        // 校验名称是否重复
        SysRole sysRoleOld = this.sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getName, params.getName()).last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysRoleOld == null || sysRoleOld.getRoleId().equals(params.getRoleId()), "名称重复，请重新输入！");

        SysRole sysRole = SysRole.builder()
                .roleId(params.getRoleId())
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
        // 1、删除该角色下关联的菜单
        this.iSysRoleMenuService.deleteAllMenusByRoleId(roleId);
        // 2、删除该角色下关联的按钮
        this.iSysRolePermissionService.delByRoleId(roleId);
        // 3、删除角色
        this.removeById(roleId);
    }

    @Override
    public Integer getRoleIdByCode(SysRoleCodeEnum sysRoleCodeEnum) {
        return this.sysRoleMapper.selectRoleIdByCode(sysRoleCodeEnum.getCode());
    }

}
