package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.system.entity.SysPermission;
import com.zhengqing.system.mapper.SysPermissionMapper;
import com.zhengqing.system.model.dto.SysMenuReBtnPermSaveDTO;
import com.zhengqing.system.model.vo.SysMenuReBtnPermListVO;
import com.zhengqing.system.model.vo.SysMenuRePermListVO;
import com.zhengqing.system.model.vo.SysRoleRePermListVO;
import com.zhengqing.system.model.vo.SysRoleRePermVO;
import com.zhengqing.system.service.ISysPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统管理-权限 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    private final SysPermissionMapper sysPermissionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SysMenuReBtnPermSaveDTO params) {
        SysPermission.builder()
                .id(params.getId())
                .name(params.getName())
                .menuId(params.getMenuId())
                .btnPerm(params.getBtnPerm())
                .urlPerm(params.getUrlPerm())
                .build()
                .insertOrUpdate();
    }

    @Override
    public List<SysMenuReBtnPermListVO> getPermListByMenuId(Integer menuId) {
        return this.sysPermissionMapper.selectBtnInfoListByMenuId(menuId);
    }

    @Override
    public List<SysRoleRePermListVO> listRoleRePerm() {
        return this.sysPermissionMapper.selectListRoleRePerm();
    }

    @Override
    public Map<Integer, List<SysMenuRePermListVO>> mapMenuRePerm() {
        // 菜单关联的权限
        List<SysMenuRePermListVO> menuRePermList = this.sysPermissionMapper.selectListMenuRePerm();
        // 根据菜单ID分组
        return menuRePermList.stream().collect(Collectors.groupingBy(SysMenuRePermListVO::getMenuId));
    }

    @Override
    public Map<Integer, List<SysRoleRePermVO>> mapPermByRole(List<Integer> roleIdList, boolean isOnlyShowPerm, List<Integer> permIdList) {
        List<SysRoleRePermVO> list = this.sysPermissionMapper.selectListPermByRole(roleIdList, isOnlyShowPerm, permIdList);
        return list.stream().collect(Collectors.groupingBy(SysRoleRePermVO::getMenuId));
    }

    @Override
    public List<Integer> listPermissionId() {
        return super.list().stream().map(SysPermission::getId).collect(Collectors.toList());
    }

}
