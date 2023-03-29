package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.system.entity.SysMenu;
import com.zhengqing.system.mapper.SysMenuMapper;
import com.zhengqing.system.model.dto.SysMenuListDTO;
import com.zhengqing.system.model.dto.SysMenuSaveDTO;
import com.zhengqing.system.model.vo.SysMenuTreeVO;
import com.zhengqing.system.model.vo.SysRoleRePermVO;
import com.zhengqing.system.service.ISysMenuService;
import com.zhengqing.system.service.ISysPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统管理-菜单表 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:51
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    private final SysMenuMapper sysMenuMapper;
    private final ISysPermissionService sysPermissionService;

    @Override
    public IPage<SysMenu> listPage(SysMenuListDTO params) {
        return this.sysMenuMapper.selectMenus(new Page(), params);
    }

    @Override
    public List<SysMenu> list(SysMenuListDTO params) {
        return this.sysMenuMapper.selectMenus(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysMenuSaveDTO params) {
        Integer menuId = params.getMenuId();
        SysMenu sysMenu = SysMenu.builder()
                .menuId(menuId)
                .title(params.getTitle())
                .name(params.getName())
                .icon(params.getIcon())
                .path(params.getPath())
                .parentId(params.getParentId())
                .sort(params.getSort())
                .component(params.getComponent())
                .hidden(params.getHidden())
                .redirect(params.getRedirect())
                .breadcrumb(params.getBreadcrumb())
                .isShowChildren(params.getIsShowChildren())
                .build();
        if (menuId == null) {
            sysMenu.insert();
        } else {
            sysMenu.updateById();
        }
        return sysMenu.getMenuId();
    }

    @Override
    public List<SysMenuTreeVO> tree(Integer roleId) {
        // 1、拿到所有菜单
        List<SysMenuTreeVO> allMenuList = this.sysMenuMapper.selectMenuTree(roleId);

        // 2、全部url/btn权限
        Map<Integer, List<SysRoleRePermVO>> mapPerm = this.sysPermissionService.mapPermByRoleId(roleId);

        // 3、遍历出父菜单对应的子菜单 -- 递归
        return this.recurveMenu(AppConstant.PARENT_ID, allMenuList, mapPerm);
    }

    /**
     * 递归菜单
     *
     * @param parentMenuId 父菜单id
     * @param allMenuList  所有菜单
     * @param mapPerm      全部url/btn权限
     * @return 菜单树列表
     * @author zhengqingya
     * @date 2020/9/10 20:56
     */
    private List<SysMenuTreeVO> recurveMenu(Integer parentMenuId, List<SysMenuTreeVO> allMenuList, Map<Integer, List<SysRoleRePermVO>> mapPerm) {
        // 存放子菜单的集合
        List<SysMenuTreeVO> childMenuList = allMenuList.stream().filter(e -> e.getParentId().equals(parentMenuId)).collect(Collectors.toList());

        // 递归
        childMenuList.forEach(item -> {
            Integer menuId = item.getMenuId();
            List<SysRoleRePermVO> permList = mapPerm.get(menuId);
            // 权限
            item.setPermList(CollectionUtils.isEmpty(permList) ? Lists.newArrayList() : permList);
            // 子菜单
            item.setChildren(this.recurveMenu(menuId, allMenuList, mapPerm));
        });
        return childMenuList;
    }

}
