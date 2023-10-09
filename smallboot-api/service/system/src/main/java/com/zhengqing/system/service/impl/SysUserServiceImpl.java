package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.common.core.util.DesUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.system.entity.SysUser;
import com.zhengqing.system.mapper.SysUserMapper;
import com.zhengqing.system.model.dto.SysUserListDTO;
import com.zhengqing.system.model.dto.SysUserPermDTO;
import com.zhengqing.system.model.dto.SysUserSaveDTO;
import com.zhengqing.system.model.dto.SysUserUpdatePasswordDTO;
import com.zhengqing.system.model.vo.SysUserDetailVO;
import com.zhengqing.system.model.vo.SysUserListVO;
import com.zhengqing.system.model.vo.SysUserPermVO;
import com.zhengqing.system.service.ISysUserRoleService;
import com.zhengqing.system.service.ISysUserService;
import com.zhengqing.system.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    @Override
    public IPage<SysUserListVO> listPage(SysUserListDTO params) {
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
    public SysUserDetailVO detail(Integer userId) {
        SysUserDetailVO detail = this.sysUserMapper.detail(userId);
        Assert.notNull(detail, "用户不存在！");
        detail.handleData();
        return detail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdateData(SysUserSaveDTO params) {
        Integer userId = params.getUserId();
        String password = params.getPassword();

        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setUsername(params.getUsername());
        user.setNickname(params.getNickname());
        user.setSexEnum(UserSexEnum.getEnum(params.getSex()));
        user.setPhone(params.getPhone());
        user.setEmail(params.getEmail());
        user.setAvatarUrl(params.getAvatarUrl());
        user.setDeptId(params.getDeptId());
        user.setPostIdList(params.getPostIdList());

        if (userId == null) {
            user.setPassword(PasswordUtil.encodePassword(StrUtil.isBlank(password) ? AppConstant.DEFAULT_PASSWORD : password));
            user.insert();
        } else {
            user.updateById();
        }
        return user.getUserId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer userId) {
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
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Integer userId, String password) {
        SysUser sysUser = this.sysUserMapper.selectById(userId);
        sysUser.setPassword(PasswordUtil.encodePassword(
                StringUtils.isBlank(password) ? AppConstant.DEFAULT_PASSWORD : password
        ));
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
