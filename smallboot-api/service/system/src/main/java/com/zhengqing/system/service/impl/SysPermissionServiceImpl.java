package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.system.entity.SysPermission;
import com.zhengqing.system.mapper.SysPermissionMapper;
import com.zhengqing.system.model.dto.SysMenuReBtnPermSaveDTO;
import com.zhengqing.system.model.vo.SysMenuReBtnPermListVO;
import com.zhengqing.system.model.vo.SysRoleRePermListVO;
import com.zhengqing.system.service.ISysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Resource
    private SysPermissionMapper sysPermissionMapper;

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
        return this.sysPermissionMapper.listRoleRePerm();
    }

}
