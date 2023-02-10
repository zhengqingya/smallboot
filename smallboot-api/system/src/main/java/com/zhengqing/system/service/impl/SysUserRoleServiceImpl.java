package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
        // ① 先删除关联角色
        this.deleteUserReRoleIds(userId);
        // ② 再新增角色
        roleIdList.forEach(roleId ->
                SysUserRole.builder()
                        .userId(userId)
                        .roleId(roleId)
                        .build()
                        .insert());
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
    public void deleteUserReRoleIds(Integer userId) {
        this.sysUserRoleMapper.delete(new LambdaUpdateWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
    }

}
