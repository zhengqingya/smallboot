package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.system.entity.SysRolePermission;
import com.zhengqing.system.mapper.SysRolePermissionMapper;
import com.zhengqing.system.model.dto.SysRoleMenuBtnSaveDTO;
import com.zhengqing.system.model.vo.SysRoleMenuBtnListVO;
import com.zhengqing.system.service.ISysRolePermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 系统管理-角色关联权限 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    private final SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysRoleMenuBtnListVO> listRoleReMenuBtn() {
        return this.sysRolePermissionMapper.selectAllRoleReMenuBtns();
    }

    @Override
    public List<Integer> getPermissionBtnsByRoleIdAndMenuId(Integer roleId, Integer menuId) {
        return this.sysRolePermissionMapper.selectBtnsByRoleIdAndMenuId(roleId, menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBtnsByRoleId(Integer roleId) {
        this.sysRolePermissionMapper.deleteBtnsByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBtnsByRoleIdAndMenuId(Integer roleId, Integer menuId) {
        this.sysRolePermissionMapper.deleteBtnsByRoleIdAndMenuId(roleId, menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleReMenuBtnIds(SysRoleMenuBtnSaveDTO params) {
        Integer roleId = params.getRoleId();
        Integer menuId = params.getMenuId();
        List<Integer> permissionIdList = params.getPermissionIdList();

        // 1、先删除
        this.deleteBtnsByRoleIdAndMenuId(roleId, menuId);

        if (CollectionUtils.isEmpty(permissionIdList)) {
            return;
        }

        // 2、再保存
        this.savePerm(roleId, permissionIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePerm(Integer roleId, List<Integer> permissionIdList) {
        if (CollectionUtils.isEmpty(permissionIdList)) {
            return;
        }
        List<SysRolePermission> saveList = Lists.newArrayList();
        permissionIdList.forEach(btnId ->
                saveList.add(SysRolePermission.builder()
                        .roleId(roleId)
                        .permissionId(btnId)
                        .build())
        );
        this.saveBatch(saveList);
    }
}
