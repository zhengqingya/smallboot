package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.system.entity.SysRoleMenu;
import com.zhengqing.system.mapper.SysRoleMenuMapper;
import com.zhengqing.system.model.dto.SysRoleReMenuSaveDTO;
import com.zhengqing.system.model.vo.SysRoleReBtnPermListVO;
import com.zhengqing.system.service.ISysRoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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
        List<Integer> menuIdList = params.getMenuIdList();

        if (CollUtil.isEmpty(menuIdList)) {
            // 直接删除角色关联的所有菜单权限
            this.sysRoleMenuMapper.deleteAllMenusByRoleId(roleId);
            return;
        }

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
        menuIdList.forEach(menuId ->
                roleMenuList.add(SysRoleMenu.builder()
                        .id(menuReIdMapOld.get(menuId))
                        .roleId(roleId)
                        .menuId(menuId)
                        .build())
        );
        this.saveOrUpdateBatch(roleMenuList);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllMenusByRoleId(Integer roleId) {
        this.sysRoleMenuMapper.deleteAllMenusByRoleId(roleId);
    }

    @Override
    public List<Integer> getMenuIdList(Integer tenantId) {
        return this.sysRoleMenuMapper.selectMenuIdList(tenantId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delReMenuId(Integer tenantId, List<Integer> delMenuIdList) {
        TenantIdContext.setTenantId(tenantId);
        if (CollUtil.isEmpty(delMenuIdList)) {
            return;
        }
        TenantIdContext.setTenantId(tenantId);
        this.sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getMenuId, delMenuIdList));
    }

    @Override
    public List<SysRoleReBtnPermListVO> listRoleReBtnPerm() {
        return this.sysRoleMenuMapper.selectBtnPerm();
    }
    
}
