package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.system.entity.SysUserRole;
import com.zhengqing.system.mapper.SysUserRoleMapper;
import com.zhengqing.system.model.bo.SysUserReRoleIdListBO;
import com.zhengqing.system.model.dto.SysUserRoleSaveDTO;
import com.zhengqing.system.service.ISysUserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统管理 - 用户角色管理 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/10 11:52
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SysUserRoleSaveDTO params) {
        Integer userId = params.getUserId();
        List<Integer> roleIdList = params.getRoleIdList();

        if (CollUtil.isEmpty(roleIdList)) {
            // 直接删除用户关联的所有角色
            this.delByUserId(userId);
            return;
        }

        // 1、查询用户关联的旧角色信息
        List<SysUserRole> reRoleListOld = this.sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        // 角色id -> 主键id
        Map<Integer, Integer> roleReIdMapOld = reRoleListOld.stream().collect(Collectors.toMap(SysUserRole::getRoleId, SysUserRole::getId, (oldData, newData) -> newData));
        // 旧角色id
        List<Integer> reRoleIdListOld = reRoleListOld.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());

        // 2、筛选出需删除的旧数据
        List<Integer> removeRoleIdList = reRoleIdListOld.stream().filter(oldRoleId -> !roleIdList.contains(oldRoleId)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(removeRoleIdList)) {
            // 删除角色关联的菜单权限信息
            this.sysUserRoleMapper.delete(
                    new LambdaQueryWrapper<SysUserRole>()
                            .eq(SysUserRole::getUserId, userId)
                            .in(SysUserRole::getRoleId, removeRoleIdList)
            );
        }

        // 3、再新增角色
        List<SysUserRole> saveList = Lists.newArrayList();
        roleIdList.forEach(roleId ->
                saveList.add(
                        SysUserRole.builder()
                                .tenantId(TenantIdContext.getTenantId())
                                .id(roleReIdMapOld.get(roleId))
                                .userId(userId)
                                .roleId(roleId)
                                .build()
                )
        );
        this.sysUserRoleMapper.insertBatchSomeColumn(saveList);
    }

    @Override
    public List<Integer> listUserId(Integer roleId) {
        return this.sysUserRoleMapper.listUserId(roleId);
    }

    @Override
    public List<Integer> listRoleId(Integer userId) {
        return this.mapRoleId(Lists.newArrayList(userId)).get(userId);
    }

    @Override
    public Map<Integer, List<Integer>> mapRoleId(List<Integer> userIdList) {
        Map<Integer, List<Integer>> resultMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(userIdList)) {
            return resultMap;
        }
        List<SysUserReRoleIdListBO> userReRoleIdList = this.sysUserRoleMapper.selectListByUserIds(userIdList);
        for (SysUserReRoleIdListBO item : userReRoleIdList) {
            resultMap.computeIfAbsent(item.getUserId(), k -> new LinkedList<>()).add(item.getRoleId());
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delByUserId(Integer userId) {
        this.sysUserRoleMapper.delete(new LambdaUpdateWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
    }

}
