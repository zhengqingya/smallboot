package com.zhengqing.common.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.zhengqing.common.auth.model.dto.AuthLoginDTO;
import com.zhengqing.common.auth.model.vo.AuthLoginVO;
import com.zhengqing.common.auth.service.IAuthService;
import com.zhengqing.common.auth.util.AuthUtil;
import com.zhengqing.common.base.enums.AuthSourceEnum;
import com.zhengqing.common.base.enums.SysRoleCodeEnum;
import com.zhengqing.common.base.model.bo.JwtUserBO;
import com.zhengqing.common.base.model.bo.ScopeDataBO;
import com.zhengqing.system.model.dto.SysUserPermDTO;
import com.zhengqing.system.model.vo.SysUserPermVO;
import com.zhengqing.system.service.ISysPermBusinessService;
import com.zhengqing.system.service.ISysRoleScopeService;
import com.zhengqing.system.service.ISysRoleService;
import com.zhengqing.system.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 授权 服务实现类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final ISysPermBusinessService iSysPermBusinessService;
    private final ISysRoleService iSysRoleService;
    private final ISysRoleScopeService iSysRoleScopeService;

    @Override
    public AuthLoginVO login(AuthLoginDTO params) {
        String username = params.getUsername();
        String password = params.getPassword();

        SysUserPermVO userPerm = this.iSysPermBusinessService.getUserPerm(SysUserPermDTO.builder().username(username).build());
        boolean isValid = PasswordUtil.isValidPassword(password, userPerm.getPassword());

        // 校验原始密码是否正确
        Assert.isTrue(isValid, "密码错误！");

        // 拿到下级角色ids
        List<Integer> roleIdList = userPerm.getRoleIdList();
        Assert.isTrue(CollUtil.isNotEmpty(roleIdList), "无权限，请先分配权限！");
        List<String> roleCodeList = userPerm.getRoleCodeList();
        List<Integer> allRoleIdList = Lists.newArrayList();
        allRoleIdList.addAll(roleIdList);
        if (!roleCodeList.contains(SysRoleCodeEnum.超级管理员.getCode())) {
            roleIdList.forEach(roleIdItem -> allRoleIdList.addAll(this.iSysRoleService.getChildRoleIdList(roleIdItem)));
        }
        // 去重
        List<Integer> allRoleIdListFinal = allRoleIdList.stream().distinct().collect(Collectors.toList());

        // 根据角色拿到关联的数据权限
        List<ScopeDataBO> scopeDataList = this.iSysRoleScopeService.getScopeListReRoleIdList(roleIdList);

        // 登录
        return AuthUtil.login(
                JwtUserBO.builder()
                        .authSourceEnum(AuthSourceEnum.B)
                        .userId(String.valueOf(userPerm.getUserId()))
                        .username(userPerm.getUsername())
                        .allRoleIdList(allRoleIdListFinal)
                        .roleCodeList(roleCodeList)
                        .deptId(userPerm.getDeptId())
                        .scopeDataList(scopeDataList)
                        .build()
        );
    }

}
