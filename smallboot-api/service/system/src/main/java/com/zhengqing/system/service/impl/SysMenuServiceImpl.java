package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.system.entity.SysMenu;
import com.zhengqing.system.mapper.SysMenuMapper;
import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.dto.SysMenuListDTO;
import com.zhengqing.system.model.dto.SysMenuSaveDTO;
import com.zhengqing.system.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    @Override
    public IPage<SysMenu> listPage(SysMenuListDTO params) {
        return this.sysMenuMapper.selectMenus(new Page(), params);
    }

    @Override
    public List<SysMenu> list(SysMenuListDTO params) {
        return this.sysMenuMapper.selectMenus(params);
    }

    @Override
    public List<SysMenuTree> selectMenuTree(List<Integer> roleIdList, boolean isOnlyShowPerm, List<Integer> menuIdList) {
        return this.sysMenuMapper.selectMenuTree(roleIdList, isOnlyShowPerm, menuIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysMenuSaveDTO params) {
        Integer menuId = params.getMenuId();
        SysMenu sysMenu = SysMenu.builder()
                .menuId(menuId)
                .parentId(params.getParentId())
                .title(params.getTitle())
                .icon(params.getIcon())
                .path(params.getPath())
                .sort(params.getSort())
                .component(params.getComponent())
                .redirect(params.getRedirect())
                .isShow(params.getIsShow())
                .isShowBreadcrumb(params.getIsShowBreadcrumb())
                .build();
        if (menuId == null) {
            sysMenu.insert();
        } else {
            sysMenu.updateById();
        }
        return sysMenu.getMenuId();
    }

}
