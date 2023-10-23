package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.context.JwtUserContext;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.system.entity.SysMenu;
import com.zhengqing.system.entity.SysTenantPackage;
import com.zhengqing.system.enums.SysMenuTypeEnum;
import com.zhengqing.system.mapper.SysMenuMapper;
import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.dto.SysMenuSaveDTO;
import com.zhengqing.system.model.dto.SysMenuTreeDTO;
import com.zhengqing.system.service.ISysMenuService;
import com.zhengqing.system.service.ISysRoleMenuService;
import com.zhengqing.system.service.ISysRoleService;
import com.zhengqing.system.service.ISysTenantPackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final ISysTenantPackageService iSysTenantPackageService;
    private final ISysRoleService iSysRoleService;
    private final ISysRoleMenuService iSysRoleMenuService;

    @Override
    public List<SysMenuTree> list(SysMenuTreeDTO params) {
        if (params.getIsOnlyShowPerm() == null) {
            params.setIsOnlyShowPerm(false);
        }
        return this.sysMenuMapper.selectMenuTree(params);
    }

    @Override
    public List<SysMenuTree> tree(SysMenuTreeDTO params) {
        // 1、查询租户权限 （排除超管）
        if (!JwtUserContext.hasSuperOrSystemAdmin()) {
            SysTenantPackage sysTenantPackage = this.iSysTenantPackageService.detailReTenantId(TenantIdContext.getTenantId());
            params.setMenuIdList(sysTenantPackage.getMenuIdList());
        }

        Boolean isOnlySystemAdminRePerm = params.getIsOnlySystemAdminRePerm();
        if ((isOnlySystemAdminRePerm != null && isOnlySystemAdminRePerm) || JwtUserContext.hasSystemAdmin()) {
            // 查询系统管理员有的权限
            params.setMenuIdList(this.iSysRoleMenuService.getMenuIdsByRoleId(AppConstant.SMALL_BOOT_SYSTEM_ADMIN_ROLE_ID));
        }


        // 2、拿到所有菜单
        List<SysMenuTree> allMenuList = this.list(params);

        if (StrUtil.isNotBlank(params.getName())) {
            return allMenuList;
        }

        // 3、看看是否需要通过子级菜单去拿到父级数据
        List<Integer> childMenuIdList = params.getChildMenuIdList();
        if (CollUtil.isNotEmpty(childMenuIdList)) {
            // 拿到子级第一次的父id
            List<Integer> firstParentIdList = allMenuList.stream().filter(e -> childMenuIdList.contains(e.getId())).map(SysMenuTree::getParentId).collect(Collectors.toList());
            // 拿到子级关联的所有父级菜单
            List<Integer> parentIdList = Lists.newArrayList();
            parentIdList.addAll(childMenuIdList);
            List<Integer> childReAllParentIdList = recurveAllParentIdList(firstParentIdList, allMenuList, parentIdList);
            if (CollUtil.isNotEmpty(childReAllParentIdList)) {
                allMenuList = allMenuList.stream().filter(e -> childReAllParentIdList.contains(e.getId())).collect(Collectors.toList());
            }
        }

        // 4、遍历出父菜单对应的子菜单 -- 递归
        return this.recurveMenu(AppConstant.PARENT_ID, "", allMenuList);
    }

    /**
     * 递归菜单 -- 拿到包含子菜单的所有父级菜单
     *
     * @param nodeReParentIdList 子级关联的父菜单ids
     * @param allList            所有菜单
     * @param parentIdList       最后返回的父id
     * @return 子菜单与关联的所有父级菜单ids
     * @author zhengqingya
     * @date 2020/9/10 20:56
     */
    public static List<Integer> recurveAllParentIdList(List<Integer> nodeReParentIdList, List<SysMenuTree> allList, List<Integer> parentIdList) {
        parentIdList.addAll(nodeReParentIdList);
        // 过滤掉顶级父id 0
        List<Integer> nodeReParentIdListNew = nodeReParentIdList.stream().filter(e -> !AppConstant.PARENT_ID.equals(e)).collect(Collectors.toList());
        if (CollUtil.isEmpty(nodeReParentIdListNew)) {
            // 升序 + 去重
            return parentIdList.stream().distinct().sorted().collect(Collectors.toList());
        }
        List<SysMenuTree> list = allList.stream().filter(item -> nodeReParentIdListNew.contains(item.getId())).collect(Collectors.toList());
        List<Integer> childReParentIdList = list.stream().map(SysMenuTree::getParentId).collect(Collectors.toList());
        return recurveAllParentIdList(childReParentIdList, allList, parentIdList);
    }

    /**
     * 递归菜单
     *
     * @param parentMenuId   父菜单id
     * @param parentMenuName 父菜单名
     * @param allMenuList    所有菜单
     * @return 菜单树列表
     * @author zhengqingya
     * @date 2020/9/10 20:56
     */
    private List<SysMenuTree> recurveMenu(Integer parentMenuId, String parentMenuName, List<SysMenuTree> allMenuList) {
        // 存放子菜单的集合
        List<SysMenuTree> childMenuList = allMenuList.stream().filter(e -> e.getParentId().equals(parentMenuId)).collect(Collectors.toList());
        // 递归
        childMenuList.forEach(item -> {
            Integer menuId = item.getId();
            String name = item.getName();
            item.setFullName(parentMenuName + "/" + name);
            // 子菜单
            item.setChildren(this.recurveMenu(menuId, item.getFullName(), allMenuList));
            item.handleData();
        });
        return childMenuList;
    }

    @Override
    public List<Integer> allMenuId() {
        return this.sysMenuMapper.selectAllMenuId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysMenuSaveDTO params) {
        Integer id = params.getId();
        Integer type = params.getType();
        if (SysMenuTypeEnum.按钮.getType().equals(type)) {
            params.setIsShow(false);
            params.setIsShowBreadcrumb(false);
        }
        SysMenu sysMenu = SysMenu.builder()
                .id(id)
                .parentId(params.getParentId())
                .name(params.getName())
                .icon(params.getIcon())
                .path(params.getPath())
                .btnPerm(params.getBtnPerm())
                .sort(params.getSort())
                .component(params.getComponent())
                .redirect(params.getRedirect())
                .isShow(params.getIsShow())
                .isShowBreadcrumb(params.getIsShowBreadcrumb())
                .type(type)
                .build();
        if (id == null) {
            sysMenu.insert();
        } else {
            sysMenu.updateById();
        }
        return sysMenu.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer id) {
        List<SysMenuTree> list = this.list(SysMenuTreeDTO.builder().parentId(id).build());
        Assert.isTrue(CollUtil.isEmpty(list), "请先删除子菜单后再删除当前菜单！");
        this.sysMenuMapper.deleteById(id);
    }
}
