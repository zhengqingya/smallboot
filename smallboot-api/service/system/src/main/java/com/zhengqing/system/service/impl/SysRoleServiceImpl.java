package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.context.JwtUserContext;
import com.zhengqing.common.base.enums.SysRoleCodeEnum;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.system.entity.SysRole;
import com.zhengqing.system.mapper.SysRoleMapper;
import com.zhengqing.system.model.dto.SysRoleBaseDTO;
import com.zhengqing.system.model.dto.SysRoleSaveDTO;
import com.zhengqing.system.model.vo.SysRoleBaseVO;
import com.zhengqing.system.service.ISysRoleMenuService;
import com.zhengqing.system.service.ISysRoleScopeService;
import com.zhengqing.system.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统管理 - 角色管理 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 15:01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final ISysRoleMenuService iSysRoleMenuService;
    private final ISysRoleScopeService iSysRoleScopeService;

    @Override
    public IPage<SysRoleBaseVO> listPage(SysRoleBaseDTO params) {
        return this.sysRoleMapper.selectDataList(new Page<>(), params);
    }

    @Override
    public List<SysRoleBaseVO> list(SysRoleBaseDTO params) {
        return this.sysRoleMapper.selectDataList(params);
    }

    @Override
    public SysRole detail(Integer roleId) {
        SysRole sysRole = this.sysRoleMapper.selectById(roleId);
        Assert.notNull(sysRole, "角色不存在！");
        return sysRole;
    }

    @Override
    public Map<Integer, String> mapByRoleIdList(List<Integer> roleIdList) {
        List<SysRole> list = this.sysRoleMapper.selectList(new LambdaQueryWrapper<SysRole>().in(SysRole::getRoleId, roleIdList));
        return list.stream().collect(Collectors.toMap(SysRole::getRoleId, SysRole::getName, (oldData, newData) -> newData));
    }

    @Override
    public List<SysRoleBaseVO> tree(SysRoleBaseDTO params) {
        List<SysRoleBaseVO> list = this.list(params);
        if (CollUtil.isEmpty(list)) {
            return Lists.newArrayList();
        }
        if (StrUtil.isNotBlank(params.getName())) {
            return list;
        }
        return this.recurveRole(AppConstant.PARENT_ID, list, params.getExcludeRoleIdList());
    }

    /**
     * 递归
     *
     * @param parentId          父id
     * @param allList           所有数据
     * @param excludeRoleIdList 排除指定角色id下级的数据
     * @return 菜单树列表
     * @author zhengqingya
     * @date 2020/9/10 20:56
     */
    private List<SysRoleBaseVO> recurveRole(Integer parentId, List<SysRoleBaseVO> allList, List<Integer> excludeRoleIdList) {
        if (CollUtil.isNotEmpty(excludeRoleIdList) && excludeRoleIdList.contains(parentId)) {
            return Lists.newArrayList();
        }
        // 存放子集合
        List<SysRoleBaseVO> childList = allList.stream().filter(e -> e.getParentId().equals(parentId)).collect(Collectors.toList());
        // 递归
        childList.forEach(item -> {
            item.setChildren(this.recurveRole(item.getRoleId(), allList, excludeRoleIdList));
            item.handleData();
        });
        return childList;
    }

    @Override
    public List<Integer> getChildRoleIdList(Integer roleId) {
        return this.recurveRoleId(roleId, this.list(), Lists.newArrayList());
    }

    /**
     * 递归
     *
     * @param parentId   父id
     * @param allList    所有数据
     * @param roleIdList 最终结果
     * @return 菜单树列表
     * @author zhengqingya
     * @date 2020/9/10 20:56
     */
    private List<Integer> recurveRoleId(Integer parentId, List<SysRole> allList, List<Integer> roleIdList) {
        roleIdList.add(parentId);
        List<SysRole> childList = allList.stream().filter(e -> e.getParentId().equals(parentId)).collect(Collectors.toList());
        if (CollUtil.isEmpty(childList)) {
            return roleIdList;
        }
        for (SysRole item : childList) {
            this.recurveRoleId(item.getRoleId(), allList, roleIdList);
        }
        return roleIdList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysRoleSaveDTO params) {
        Integer parentId = params.getParentId();
        Integer roleId = params.getRoleId();
        String code = params.getCode();
        Boolean isFixed = params.getIsFixed();
        Boolean isRefreshAllTenant = params.getIsRefreshAllTenant();
        if (!AppConstant.PARENT_ID.equals(parentId)) {
            // 只有第一级才有固定角色 和 同步刷新所有租户数据
            isFixed = false;
            isRefreshAllTenant = false;
        }
        if ((isFixed || isRefreshAllTenant) && !JwtUserContext.hasSuperOrSystemAdmin()) {
            throw new MyException("您没有权限操作特殊角色！");
        }

        if (SysRoleCodeEnum.CODE_LIST.contains(code)) {
            // 二次保护
            Assert.isTrue(JwtUserContext.hasSuperOrSystemAdmin(), "您没有权限操作特殊角色！");
        }

        SysRole sysRoleOld = this.sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getCode, code).last(MybatisConstant.LIMIT_ONE));
        Assert.isFalse(SysRoleCodeEnum.isSpecialRole(sysRoleOld.getCode()), "无权限操作特殊角色！");
        if (!JwtUserContext.hasSuperOrSystemAdmin()) {
            Assert.isTrue(sysRoleOld == null || !sysRoleOld.getIsFixed(), "您没有权限操作固定角色！");
        }
        if (!isRefreshAllTenant) {
            Assert.isTrue(sysRoleOld == null || sysRoleOld.getRoleId().equals(roleId), "角色编码重复，请重新输入！");
        }

        if (isRefreshAllTenant && sysRoleOld != null) {
            // 查询如果有同编码的角色数据则做更新处理 -- 目的：保证各租户下都存在一个唯一的 此角色 信息
            roleId = sysRoleOld.getRoleId();
        }

        // 保存角色
        SysRole sysRole = SysRole.builder()
                .roleId(roleId)
                .parentId(parentId)
                .name(params.getName())
                .code(code)
                .status(params.getStatus())
                .isFixed(isFixed)
                .isRefreshAllTenant(isRefreshAllTenant)
                .sort(params.getSort())
                .build();
        sysRole.insertOrUpdate();

        return sysRole.getRoleId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleAndRoleMenu(Integer roleId) {
        SysRole sysRole = this.sysRoleMapper.selectById(roleId);
        Assert.isFalse(SysRoleCodeEnum.isSpecialRole(sysRole.getCode()), "无权限操作特殊角色！");
        if (sysRole.getIsFixed()) {
            Assert.isTrue(JwtUserContext.hasSuperAdmin(), "您没有权限删除固定角色！");
        }
        if (sysRole.getIsRefreshAllTenant()) {
            Assert.isTrue(JwtUserContext.hasSuperOrSystemAdmin(), "您没有权限同步更新所有租户下的角色数据！");
        }
        Assert.isFalse(SysRoleCodeEnum.CODE_LIST.contains(sysRole.getCode()) && !JwtUserContext.hasSuperAdmin(), "只有超管才有权限操作特殊角色！");
        // 1、删除该角色下关联的权限
        this.iSysRoleMenuService.delByRoleId(roleId);
        this.iSysRoleScopeService.delByRoleId(roleId);
        // 2、删除角色
        this.removeById(roleId);
    }

    @Override
    public Integer getRoleIdByCode(SysRoleCodeEnum sysRoleCodeEnum) {
        return this.sysRoleMapper.selectRoleIdByCode(sysRoleCodeEnum.getCode());
    }

}
