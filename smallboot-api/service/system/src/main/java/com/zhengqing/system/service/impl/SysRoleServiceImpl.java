package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.base.util.MyBeanUtil;
import com.zhengqing.system.entity.SysRole;
import com.zhengqing.system.mapper.SysRoleMapper;
import com.zhengqing.system.model.dto.SysRoleListDTO;
import com.zhengqing.system.model.dto.SysRoleSaveDTO;
import com.zhengqing.system.model.vo.*;
import com.zhengqing.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private ISysMenuService sysMenuService;

    @Resource
    private ISysRoleMenuService sysRoleMenuService;

    @Resource
    private ISysRolePermissionService sysRolePermissionService;

    @Resource
    private ISysPermissionService sysPermissionService;

    @Override
    public IPage<SysRoleListVO> listPage(SysRoleListDTO params) {
        return this.sysRoleMapper.selectRoles(new Page(), params);
    }

    @Override
    public List<SysRoleListVO> list(SysRoleListDTO params) {
        return this.sysRoleMapper.selectRoles(params);
    }

    @Override
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
    public SysRolePermissionDetailVO detail(Integer roleId) {
        SysRole sysRole = this.sysRoleMapper.selectById(roleId);
        SysRolePermissionDetailVO result = SysRolePermissionDetailVO.builder()
                .roleId(sysRole.getRoleId())
                .name(sysRole.getName())
                .code(sysRole.getCode())
                .status(sysRole.getStatus())
                .build();
        List<Integer> menuIdList = this.sysRoleMenuService.getMenuIdsByRoleId(roleId);
        result.setMenuIdList(menuIdList);
        return result;
    }

    @Override
    public SysRoleAllPermissionDetailVO permissionDetail(Integer roleId) {
        SysRoleAllPermissionDetailVO permissionDetail =
                MyBeanUtil.copyProperties(this.detail(roleId), SysRoleAllPermissionDetailVO.class);
        List<SysMenuTreeVO> menTree = this.sysMenuService.tree();
        this.handleRecursionTree(menTree, roleId);
        permissionDetail.setMenuAndBtnPermissionTree(menTree);
        return permissionDetail;
    }

    /**
     * 递归树，填充角色菜单对应的菜单+按钮权限信息
     *
     * @param menTree 树数据
     * @return void
     * @author zhengqingya
     * @date 2020/9/11 17:26
     */
    public void handleRecursionTree(List<SysMenuTreeVO> menTree, Integer roleId) {
        if (!CollectionUtils.isEmpty(menTree)) {
            menTree.forEach(menu -> {
                Integer menuId = menu.getMenuId();
                List<SysMenuReBtnPermListVO> btnInfoList = this.sysPermissionService.getPermListByMenuId(menuId);
                List<Integer> permissionIdList = this.sysRolePermissionService.getPermissionBtnsByRoleIdAndMenuId(roleId, menuId);
                menu.setBtnInfoList(btnInfoList);
                menu.setPermissionIdList(permissionIdList);
                List<SysMenuTreeVO> menuChildren = menu.getChildren();
                this.handleRecursionTree(menuChildren, roleId);
            });
        }
    }

    @Override
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
