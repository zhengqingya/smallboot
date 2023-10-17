package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.system.entity.SysMenu;
import com.zhengqing.system.enums.SysMenuTypeEnum;
import com.zhengqing.system.mapper.SysMenuMapper;
import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.dto.SysMenuSaveDTO;
import com.zhengqing.system.model.dto.SysMenuTreeDTO;
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
    public List<SysMenuTree> selectMenuTree(SysMenuTreeDTO params) {
        return this.sysMenuMapper.selectMenuTree(params);
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

}
