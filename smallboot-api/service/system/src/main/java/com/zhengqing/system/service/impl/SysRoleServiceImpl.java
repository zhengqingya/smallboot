package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.system.entity.SysRole;
import com.zhengqing.system.mapper.SysRoleMapper;
import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.dto.SysRoleListDTO;
import com.zhengqing.system.model.dto.SysRoleSaveDTO;
import com.zhengqing.system.model.vo.SysRoleAllPermissionDetailVO;
import com.zhengqing.system.model.vo.SysRoleListVO;
import com.zhengqing.system.service.*;
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

    private final ISysMenuService sysMenuService;

    private final ISysRoleMenuService sysRoleMenuService;

    private final ISysRolePermissionService sysRolePermissionService;

    private final ISysPermissionService sysPermissionService;

    @Override
    public IPage<SysRoleListVO> listPage(SysRoleListDTO params) {
        return this.sysRoleMapper.selectRoles(new Page(), params);
    }

    @Override
    public List<SysRoleListVO> list(SysRoleListDTO params) {
        return this.sysRoleMapper.selectRoles(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysRoleSaveDTO params) {
        SysRole sysRole = SysRole.builder()
                .roleId(params.getRoleId())
                .name(params.getName())
                .code(params.getCode())
                .status(params.getStatus())
                .build();
        sysRole.insertOrUpdate();
        return sysRole.getRoleId();
    }

    @Override
    public SysRoleAllPermissionDetailVO permissionDetail(Integer roleId) {
        // 1、角色基本信息
        SysRole sysRole = this.sysRoleMapper.selectById(roleId);

        // 2、菜单权限树
        List<SysMenuTree> menuTree = this.sysMenuService.tree(roleId);

        return SysRoleAllPermissionDetailVO.builder()
                .roleId(sysRole.getRoleId())
                .name(sysRole.getName())
                .code(sysRole.getCode())
                .status(sysRole.getStatus())
                .menuTree(menuTree)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleAndRoleMenu(Integer roleId) {
        // 1、删除该角色下关联的菜单
        this.sysRoleMenuService.deleteAllMenusByRoleId(roleId);
        // 2、删除该角色下关联的按钮
        this.sysRolePermissionService.deleteBtnsByRoleId(roleId);
        // 3、删除角色
        this.removeById(roleId);
    }

    @Override
    public Integer getRoleIdForSuperAdmin() {
        return this.sysRoleMapper.selectRoleIdForSuperAdmin();
    }

}
