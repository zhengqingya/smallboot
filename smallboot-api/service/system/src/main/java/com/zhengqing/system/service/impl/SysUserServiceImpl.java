package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.auth.util.AuthUtil;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.common.core.util.DesUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.system.entity.SysTenant;
import com.zhengqing.system.entity.SysUser;
import com.zhengqing.system.mapper.SysUserMapper;
import com.zhengqing.system.model.dto.*;
import com.zhengqing.system.model.vo.SysUserListVO;
import com.zhengqing.system.model.vo.SysUserPermVO;
import com.zhengqing.system.service.ISysDeptService;
import com.zhengqing.system.service.ISysTenantService;
import com.zhengqing.system.service.ISysUserRoleService;
import com.zhengqing.system.service.ISysUserService;
import com.zhengqing.system.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final SysUserMapper sysUserMapper;
    private final ISysUserRoleService iSysUserRoleService;
    private final ISysDeptService iSysDeptService;
    @Lazy
    @Resource
    private ISysTenantService iSysTenantService;

    @Override
    public IPage<SysUserListVO> listPage(SysUserListDTO params) {
        Integer deptId = params.getDeptId();
        if (deptId != null) {
            // 拿到子级部门
            params.setDeptIdList(this.iSysDeptService.getChildDeptIdList(deptId));
        }
        IPage<SysUserListVO> result = this.sysUserMapper.selectDataList(new Page<>(), params);
        this.handleUserList(result.getRecords());
        return result;
    }

    @Override
    public List<SysUserListVO> list(SysUserListDTO params) {
        List<SysUserListVO> userList = this.sysUserMapper.selectDataList(params);
        this.handleUserList(userList);
        return userList;
    }

    public void handleUserList(List<SysUserListVO> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return;
        }
        // 用户ids
        List<Integer> userIdList = userList.stream().map(SysUserListVO::getUserId).collect(Collectors.toList());
        Map<Integer, List<Integer>> mapRoleInfo = this.iSysUserRoleService.mapRoleId(userIdList);
        userList.forEach(userInfo -> {
            userInfo.setRoleIdList(mapRoleInfo.get(userInfo.getUserId()));
            userInfo.handleData();
        });
    }

    @Override
    public SysUser detail(Integer userId) {
        SysUser detail = this.sysUserMapper.selectById(userId);
        Assert.notNull(detail, "用户不存在！");
        return detail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysUserSaveDTO params) {
        Integer userId = params.getUserId();
        boolean isAdd = userId == null;
        Integer deptId = params.getDeptId();
        if (deptId != null) {
            // 校验最大用户数
            SysTenant sysTenant = this.iSysTenantService.checkData(TenantIdContext.getTenantId());
            Integer accountCount = sysTenant.getAccountCount();
            Assert.isTrue(accountCount > this.sysUserMapper.selectUserNumByDeptId(deptId), "限制：该租户最大用户数 " + accountCount);
        }

        String password = params.getPassword();
        Boolean isFixed = params.getIsFixed();

        // 校验名称是否重复
        SysUser sysUserOld = this.sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, params.getUsername()).last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysUserOld == null || sysUserOld.getUserId().equals(params.getUserId()), "用户名重复，请重新输入！");

        // 保存用户
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setNickname(params.getNickname());
        sysUser.setSexEnum(UserSexEnum.getEnum(params.getSex()));
        sysUser.setPhone(params.getPhone());
        sysUser.setEmail(params.getEmail());
        sysUser.setAvatarUrl(params.getAvatarUrl());
        sysUser.setDeptId(deptId);
        sysUser.setPostIdList(params.getPostIdList());
        sysUser.setIsFixed(isFixed);

        boolean isUpdateRole = true;
        if (isAdd) {
            sysUser.setUsername(params.getUsername());
            sysUser.setPassword(PasswordUtil.encodePassword(StrUtil.isBlank(password) ? AppConstant.DEFAULT_PASSWORD : password));
            sysUser.insert();
            userId = sysUser.getUserId();
        } else {
            if (StrUtil.isNotBlank(password)) {
                sysUser.setPassword(PasswordUtil.encodePassword(password));
            }
            SysUser sysUserOldData = this.sysUserMapper.selectById(userId);
            isUpdateRole = !sysUserOldData.getIsFixed();
            sysUser.updateById();
        }

        // 给用户绑定角色信息
        if (!AppConstant.SYSTEM_SUPER_ADMIN_USER_ID.equals(userId) && isUpdateRole) {
            this.iSysUserRoleService.addOrUpdateData(SysUserRoleSaveDTO.builder().userId(userId).roleIdList(params.getRoleIdList()).build());

            AuthUtil.logout(userId);
        }

        return userId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer userId) {
        SysUser sysUser = this.detail(userId);
        Assert.isFalse(sysUser.getIsFixed(), "您没有权限删除系统用户！");
        if (AppConstant.SYSTEM_SUPER_ADMIN_USER_ID.equals(userId)) {
            throw new MyException("您没有权限删除超级管理员！");
        }
        // 1、删除关联角色
        this.iSysUserRoleService.delByUserId(userId);
        // 2、删除该用户
        this.removeById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(SysUserUpdatePasswordDTO params) {
        String password = DesUtil.decrypt(params.getPassword(), AppConstant.DES_KEY);
        SysUser userInfo = this.sysUserMapper.selectById(params.getUserId());
        boolean isValid = PasswordUtil.isValidPassword(password, userInfo.getPassword());
        // 校验原始密码是否正确
        Assert.isTrue(isValid, AppConstant.WRONG_OLD_PASSWORD);
        userInfo.setPassword(PasswordUtil.encodePassword(params.getNewPassword()));
        this.sysUserMapper.updateById(userInfo);
    }

    @Override
    public SysUserPermVO getUserPerm(SysUserPermDTO params) {
        params.checkParam();
        // 拿到用户基础信息+角色编码
        SysUserPermVO userPerm = this.sysUserMapper.selectUserPerm(params);
        Assert.notNull(userPerm, "用户不存在或无权限！");
        return userPerm;
    }


    @Override
    public SysUser getUserByUsername(String username) {
        return this.sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, username)
                        .last(MybatisConstant.LIMIT_ONE)
        );
    }

}
