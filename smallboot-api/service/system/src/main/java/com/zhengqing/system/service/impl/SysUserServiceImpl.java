package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.auth.util.AuthUtil;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.constant.AuthConstant;
import com.zhengqing.common.base.context.JwtUserContext;
import com.zhengqing.common.base.exception.BizException;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.common.netty.enums.NettyUserLogoutMsgEnum;
import com.zhengqing.common.netty.util.NettyUtil;
import com.zhengqing.system.entity.SysUser;
import com.zhengqing.system.mapper.SysUserMapper;
import com.zhengqing.system.model.dto.*;
import com.zhengqing.system.model.vo.SysUserListVO;
import com.zhengqing.system.model.vo.SysUserPermVO;
import com.zhengqing.system.service.*;
import com.zhengqing.system.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
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
    private final ISysRoleService iSysRoleService;
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
        // 角色ids
        List<Integer> roleIdList = Lists.newArrayList();
        mapRoleInfo.forEach((key, value) -> roleIdList.addAll(value));
        List<Integer> disRoleIdList = roleIdList.stream().distinct().collect(Collectors.toList());
        // 角色id -> 角色名称
        Map<Integer, String> roleNameMap = this.iSysRoleService.mapByRoleIdList(disRoleIdList);
        // 用户id -> 在线情况
        Map<Long, Boolean> userReOnlineStatusMap = NettyUtil.ONLINE_STATUS.batchGet(userIdList.stream().map(Integer::longValue).collect(Collectors.toList()));
        userList.forEach(item -> {
            List<Integer> itemRoleIdList = mapRoleInfo.get(item.getUserId());
            item.setRoleIdList(itemRoleIdList);
            if (CollUtil.isNotEmpty(itemRoleIdList)) {
                StringJoiner sj = new StringJoiner(",");
                itemRoleIdList.forEach(roleIdItem -> {
                    String itemRoleName = roleNameMap.get(roleIdItem);
                    if (StrUtil.isNotBlank(itemRoleName)) {
                        sj.add(itemRoleName);
                    }
                });
                item.setRoleNames(sj.toString());
            }
            item.setIsOnline(userReOnlineStatusMap.getOrDefault(Long.valueOf(item.getUserId()), false));
            item.handleData();
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
        // 1、保存用户基本信息
        Integer userId = params.getUserId();
        boolean isAdd = userId == null;

        // 校验名称是否重复
        SysUser sysUserOld = this.sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, params.getUsername()).last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(sysUserOld == null || sysUserOld.getUserId().equals(params.getUserId()), "用户名重复，请重新输入！");

        // 保存用户
        boolean isFixedUser = params.getIsFixed();
        SysUser sysUser = SysUser.builder()
                .userId(userId)
                .nickname(params.getNickname())
                .sexEnum(UserSexEnum.getEnum(params.getSex()))
                .phone(params.getPhone())
                .email(params.getEmail())
                .avatarUrl(params.getAvatarUrl())
                .deptId(params.getDeptId())
                .postIdList(params.getPostIdList())
                .isFixed(isFixedUser)
                .build();

        if (isAdd) {
            sysUser.setUsername(params.getUsername());
            sysUser.setPassword(PasswordUtil.encodePassword(StrUtil.isBlank(params.getPassword()) ? AppConstant.DEFAULT_PASSWORD : params.getPassword()));
            sysUser.insert();
            userId = sysUser.getUserId();
        } else {
            SysUser sysUserOldData = this.sysUserMapper.selectById(userId);
            isFixedUser = sysUserOldData.getIsFixed();
            sysUser.setIsFixed(sysUserOldData.getIsFixed());
            sysUser.updateById();
        }

        // 2、保存用户角色信息
        if (!params.getIsUpdateRolePerm()) {
            return userId;
        }
        if (AuthConstant.SYSTEM_SUPER_ADMIN_USER_ID.equals(userId)) {
            // 超管的角色信息不能修改
            return userId;
        }
        // 给用户绑定角色信息  超管可以操作固定用户角色的修改
        if (!isFixedUser || (isFixedUser && JwtUserContext.hasSuperAdmin())) {
            this.iSysUserRoleService.addOrUpdateData(SysUserRoleSaveDTO.builder().userId(userId).roleIdList(params.getRoleIdList()).build());

            AuthUtil.logout(userId);
            NettyUtil.SEND_MSG.logout(Long.valueOf(userId), NettyUserLogoutMsgEnum.UPDATE_ROLE_PERM);
        }

        return userId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer userId) {
        SysUser sysUser = this.detail(userId);
        Assert.isFalse(sysUser.getIsFixed(), "您没有权限删除系统固定用户！");
        if (AuthConstant.SYSTEM_SUPER_ADMIN_USER_ID.equals(userId)) {
            throw new BizException("您没有权限删除超级管理员！");
        }
        // 1、删除关联角色
        this.iSysUserRoleService.delByUserId(userId);
        // 2、删除该用户
        this.removeById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(SysUserUpdatePasswordDTO params) {
        // TODO: 后面可以配置个更新密码接口的数据权限 & 密码加密
//        String password = DesUtil.decrypt(params.getPassword(), AppConstant.DES_KEY);
//        SysUser sysUser = this.sysUserMapper.selectById(params.getUserId());
//        boolean isValid = PasswordUtil.isValidPassword(password, sysUser.getPassword());
//        // 校验原始密码是否正确
//        Assert.isTrue(isValid, AppConstant.WRONG_OLD_PASSWORD);
//        sysUser.setPassword(PasswordUtil.encodePassword(params.getNewPassword()));
//        this.sysUserMapper.updateById(sysUser);

        Integer userId = params.getUserId();
        if (AuthConstant.SYSTEM_SUPER_ADMIN_USER_ID.equals(userId) && !JwtUserContext.hasSuperAdmin()) {
            throw new BizException("超管的密码你别搞！！！");
        }
        SysUser sysUser = this.detail(userId);
        sysUser.setPassword(PasswordUtil.encodePassword(params.getNewPassword()));
        this.sysUserMapper.updateById(sysUser);
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
