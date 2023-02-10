package com.zhengqing.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.core.enums.UserSexEnum;
import com.zhengqing.common.core.util.DesUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.system.entity.SysRole;
import com.zhengqing.system.entity.SysUser;
import com.zhengqing.system.enums.SysMenuTypeEnum;
import com.zhengqing.system.enums.SysUserReRoleEnum;
import com.zhengqing.system.mapper.SysUserMapper;
import com.zhengqing.system.model.dto.*;
import com.zhengqing.system.model.vo.*;
import com.zhengqing.system.service.*;
import com.zhengqing.system.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
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

    private final ISysRoleService sysRoleService;

    private final ISysUserRoleService sysUserRoleService;

    private final ISysMenuService sysMenuService;

    private final ISysRolePermissionService sysRolePermissionService;

    private final ISysRoleMenuService sysRoleMenuService;


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
        Map<Integer, List<Integer>> mapRoleInfo = this.sysUserRoleService.mapRoleId(userIdList);
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
        String username = params.getUsername();
        String nickname = params.getNickname();
        Byte sex = params.getSex();
        String phone = params.getPhone();
        String email = params.getEmail();
        String avatarUrl = params.getAvatarUrl();

        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setUsername(username);
        user.setNickname(nickname);
        user.setSexEnum(UserSexEnum.getEnum(sex));
        user.setPhone(phone);
        user.setEmail(email);
        user.setAvatarUrl(avatarUrl);

        if (userId == null) {
            user.setPassword(PasswordUtil.encodePassword(AppConstant.DEFAULT_PASSWORD));
            user.insert();

            // 绑定角色信息
            SysUserRoleSaveDTO userRoleSaveDTO = new SysUserRoleSaveDTO();
            userRoleSaveDTO.setUserId(user.getUserId());
            userRoleSaveDTO.setRoleIdList(Lists.newArrayList(SysUserReRoleEnum.凡人.getRoleId()));
            this.sysUserRoleService.addOrUpdateData(userRoleSaveDTO);
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
        this.sysUserRoleService.deleteUserReRoleIds(userId);
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
        Assert.isFalse(params.getUserId() == null && StringUtils.isBlank(params.getUsername()),
                "查询用户条件丢失！");
        // 1、拿到基础用户信息
        SysUserPermVO userPerm = this.sysUserMapper.selectUserPerm(params);
        Assert.notNull(userPerm, "用户不存在！");
        // 2、角色信息
        List<Integer> roleIdList = this.sysUserRoleService.listRoleId(userPerm.getUserId());
        Assert.notEmpty(roleIdList, "请联系管理员为其分配角色!");
        List<SysRole> roleList = this.sysRoleService.listByIds(roleIdList);
        Assert.notEmpty(roleList, "请联系管理员为其分配角色!");
        StringJoiner roleNameSj = new StringJoiner(",", "[", "]");
        roleList.forEach(item -> roleNameSj.add(item.getName()));
        userPerm.setRoleNames(roleNameSj.toString());
        List<String> roleCodeList = roleList.stream().map(e -> e.getCode()).collect(Collectors.toList());
        userPerm.setRoleCodeList(roleCodeList);

        // 3、权限
        // 角色可访问的菜单ID
        List<Integer> menuIdList = this.sysRoleMenuService.getMenuIdsByRoleIds(roleIdList);
        // 所有菜单
        List<SysMenuTreeVO> menuTreeList = this.sysMenuService.tree();
        // 所有按钮
        List<SysRoleMenuBtnListVO> btnList = this.sysRolePermissionService.listRoleMenuBtn();
        // 用户关联的权限
        List<SysMenuTreeVO> permTreeList = this.getUserPremTreeList(menuTreeList, menuIdList, roleIdList, btnList);
        userPerm.setPermissionTreeList(permTreeList);
        return userPerm;
    }


    /**
     * 获取用户菜单权限树
     *
     * @param menuTreeList 菜单树
     * @param menuIdList   用户所拥有的菜单权限ids
     * @param roleIdList   用户所拥有的角色ids
     * @param btnList      用户所拥有的菜单按钮权限
     * @return 过滤后的用户关联的权限菜单树
     * @author zhengqingya
     * @date 2020/9/11 14:34
     */
    private List<SysMenuTreeVO> getUserPremTreeList(List<SysMenuTreeVO> menuTreeList,
                                                    List<Integer> menuIdList,
                                                    List<Integer> roleIdList,
                                                    List<SysRoleMenuBtnListVO> btnList) {
        List<SysMenuTreeVO> resultList = Lists.newArrayList();
        for (SysMenuTreeVO menu : menuTreeList) {
            Integer menuId = menu.getMenuId();
            if (menuIdList.contains(menuId) && SysMenuTypeEnum.菜单.getType().equals(menu.getType())) {
                List<SysMenuTreeVO> menuChildList = menu.getChildren();
                if (!CollectionUtils.isEmpty(menuChildList)) {
                    menu.setChildren(this.getUserPremTreeList(menuChildList, menuIdList, roleIdList, btnList));
                }
                List<String> btnPermList = this.getUserBtnPermList(menuId, roleIdList, btnList);
                menu.setMeta(SysUserBtnVO.builder()
                        .title(menu.getTitle())
                        .icon(menu.getIcon())
                        .btnPermList(btnPermList)
                        .build());
                resultList.add(menu);
            }
        }
        return resultList;
    }

    /**
     * 获取用户按钮权限标识
     *
     * @param menuId     菜单id
     * @param roleIdList 用户所拥有的角色ids
     * @param btnList    按钮权限
     * @return 按钮权限标识
     * @author zhengqingya
     * @date 2020/9/11 14:36
     */
    private List<String> getUserBtnPermList(Integer menuId,
                                            List<Integer> roleIdList,
                                            List<SysRoleMenuBtnListVO> btnList) {
        if (CollectionUtils.isEmpty(btnList)) {
            return Lists.newArrayList();
        }
        Set<String> btnSet = new HashSet<>();
        btnList.forEach(btn -> {
            if (menuId.equals(btn.getMenuId()) && roleIdList.contains(btn.getRoleId())) {
                btnSet.add(btn.getBtnPerm());
            }
        });
        return new ArrayList<>(btnSet);
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
