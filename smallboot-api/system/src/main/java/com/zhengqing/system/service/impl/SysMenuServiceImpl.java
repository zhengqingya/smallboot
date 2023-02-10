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
import com.zhengqing.system.service.ISysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
@Transactional(rollbackFor = Exception.class)
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public IPage<SysMenu> listPage(SysMenuListDTO params) {
        return this.sysMenuMapper.selectMenus(new Page(), params);
    }

    @Override
    public List<SysMenu> list(SysMenuListDTO params) {
        return this.sysMenuMapper.selectMenus(params);
    }

    @Override
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
                .status(params.getStatus())
                .type(params.getType())
                .alwaysShow(params.getAlwaysShow())
                .breadcrumb(params.getBreadcrumb())
                .build();
        if (menuId == null) {
            sysMenu.insert();
        } else {
            sysMenu.updateById();
        }
        return sysMenu.getMenuId();
    }

    @Override
    public List<SysMenuTreeVO> tree() {
        // 1、拿到所有菜单
        List<SysMenuTreeVO> allMenuList = this.sysMenuMapper.selectMenuTree();
        // 2、准备一个空的父菜单集合
        List<SysMenuTreeVO> parentMenuList = Lists.newArrayList();
        // 3、遍历子菜单 -> 进行对父菜单的设置
        for (SysMenuTreeVO parentMenu : allMenuList) {
            if (parentMenu.getParentId().equals(AppConstant.PARENT_ID)) {
                parentMenuList.add(parentMenu);
            }
        }
        // 4、遍历出父菜单对应的子菜单
        for (SysMenuTreeVO parent : parentMenuList) {
            List<SysMenuTreeVO> child = this.getChildMenu(parent.getMenuId(), allMenuList);
            parent.setChildren(child);
        }
        return parentMenuList;
    }

    /**
     * 递归子菜单
     *
     * @param parentMenuId 父菜单id
     * @param allMenuList  所有菜单
     * @return 菜单树列表
     * @author zhengqingya
     * @date 2020/9/10 20:56
     */
    private List<SysMenuTreeVO> getChildMenu(Integer parentMenuId, List<SysMenuTreeVO> allMenuList) {
        // 5、存放子菜单的集合
        List<SysMenuTreeVO> childMenuList = Lists.newArrayList();
        for (SysMenuTreeVO menu : allMenuList) {
            if (menu.getParentId().equals(parentMenuId)) {
                childMenuList.add(menu);
            }
        }
        // 6、递归
        for (SysMenuTreeVO treeVO : childMenuList) {
            treeVO.setChildren(this.getChildMenu(treeVO.getMenuId(), allMenuList));
        }
        return childMenuList;
    }

}
