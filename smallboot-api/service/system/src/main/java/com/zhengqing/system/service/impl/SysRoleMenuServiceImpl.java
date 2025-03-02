package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.system.entity.SysMenu;
import com.zhengqing.system.entity.SysRoleMenu;
import com.zhengqing.system.mapper.SysMenuMapper;
import com.zhengqing.system.mapper.SysRoleMenuMapper;
import com.zhengqing.system.model.dto.SysRoleReMenuSaveDTO;
import com.zhengqing.system.model.vo.SysRoleReBtnPermListVO;
import com.zhengqing.system.service.ISysRoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统管理 - 角色菜单关联服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final SysMenuMapper sysMenuMapper;

    @Override
    public List<Integer> getMenuIdsByRoleId(Integer roleId) {
        return this.sysRoleMenuMapper.selectMenuIdsByRoleId(roleId);
    }

    @Override
    public List<Integer> getMenuIdsByRoleIds(List<Integer> roleIdList) {
        return this.sysRoleMenuMapper.selectMenuIdsByRoleIds(roleIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleMenuIds(SysRoleReMenuSaveDTO params) {
        Integer roleId = params.getRoleId();
        if (CollUtil.isEmpty(params.getMenuIdList())) {
            // 直接删除角色关联的所有菜单权限
            this.delByRoleId(roleId);
            return;
        }

        // 添加父级菜单id
        List<Integer> menuIdList = addParentMenuId(params.getMenuIdList());

        // 1、查询角色关联的旧菜单权限信息
        List<SysRoleMenu> roleReMenuListOld = this.sysRoleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        // 菜单id -> 主键id
        Map<Integer, Integer> menuReIdMapOld = roleReMenuListOld.stream().collect(Collectors.toMap(SysRoleMenu::getMenuId, SysRoleMenu::getId, (oldData, newData) -> newData));
        // 旧菜单id
        List<Integer> roleReMenuIdListOld = roleReMenuListOld.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());

        // 2、筛选出需删除的旧数据
        List<Integer> removeMenuIdList = roleReMenuIdListOld.stream().filter(oldMenuId -> !menuIdList.contains(oldMenuId)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(removeMenuIdList)) {
            // 删除角色关联的菜单权限信息
            this.sysRoleMenuMapper.delete(
                    new LambdaQueryWrapper<SysRoleMenu>()
                            .eq(SysRoleMenu::getRoleId, roleId)
                            .in(SysRoleMenu::getMenuId, removeMenuIdList)
            );
        }

        // 3、再保存角色关联的菜单权限信息
        List<SysRoleMenu> roleMenuList = Lists.newArrayList();
        menuIdList.forEach(menuId -> {
            if (menuReIdMapOld.get(menuId) == null) {
                roleMenuList.add(SysRoleMenu.builder()
                        .tenantId(TenantIdContext.getTenantId())
                        .id(null)
                        .roleId(roleId)
                        .menuId(menuId)
                        .build());
            }
        });
        if (CollUtil.isNotEmpty(roleMenuList)) {
            this.sysRoleMenuMapper.insertBatchSomeColumn(roleMenuList);
        }
    }

    /**
     * 添加父级菜单id
     *
     * @param menuIdList 菜单id
     * @return 菜单id & 关联的父级菜单id
     * @author zhengqingya
     * @date 2025/3/2 19:08
     */
    private List<Integer> addParentMenuId(List<Integer> menuIdList) {
        // 菜单id -> 菜单信息
        Map<Integer, SysMenu> menuIdReObjMap = sysMenuMapper.selectList(new LambdaQueryWrapper<>())
                .stream().collect(Collectors.toMap(SysMenu::getId, t -> t, (oldData, newData) -> newData));
        List<Integer> list = Lists.newArrayList();
        for (Integer menuId : menuIdList) {
            list.add(menuId);
            if (menuId == null || CollUtil.isEmpty(menuIdReObjMap)) {
                continue;
            }
            SysMenu sysMenu = menuIdReObjMap.get(menuId);
            if (sysMenu == null) {
                continue;
            }
            // 防止无限循环：使用 visited 集合来记录已经访问过的菜单 ID，防止循环引用。
            Set<Integer> visited = new HashSet<>();
            while (!sysMenu.isFirstParentId()) {
                Integer parentId = sysMenu.getParentId();
                if (parentId == null || visited.contains(parentId)) {
                    break;
                }
                list.add(parentId);
                visited.add(parentId);
                sysMenu = menuIdReObjMap.get(parentId);
                if (sysMenu == null) {
                    break;
                }
            }
        }
        return list.stream().distinct().collect(Collectors.toList());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delByRoleId(Integer roleId) {
        this.sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
    }

    @Override
    public List<Integer> getMenuIdList(Integer tenantId) {
        return this.sysRoleMenuMapper.selectMenuIdList(tenantId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delReMenuIdList(List<Integer> delMenuIdList) {
        if (CollUtil.isEmpty(delMenuIdList)) {
            return;
        }
        this.sysRoleMenuMapper.delete(
                new LambdaQueryWrapper<SysRoleMenu>()
                        .in(SysRoleMenu::getMenuId, delMenuIdList)
                        .notIn(SysRoleMenu::getRoleId, AppConstant.NOT_DEL_MENU_EXCLUDE_ROLE_ID_LIST)
        );
    }

    @Override
    public List<SysRoleReBtnPermListVO> listRoleReBtnPerm() {
        return this.sysRoleMenuMapper.selectBtnPerm();
    }

}
