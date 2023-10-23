package com.zhengqing.system.api.web;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.context.JwtUserContext;
import com.zhengqing.common.base.context.SysUserContext;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.system.model.dto.SysRoleRePermSaveDTO;
import com.zhengqing.system.model.dto.SysUserPermDTO;
import com.zhengqing.system.model.vo.SysUserPermVO;
import com.zhengqing.system.service.ISysPermBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统管理 - 权限接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/10 18:33
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/perm")
@Api(tags = "web-系统管理-权限接口")
public class WebSysPermController extends BaseController {

    private final ISysPermBusinessService iSysPermBusinessService;

    @GetMapping("getUserPerm")
    @ApiOperation("获取当前登录用户权限信息")
    public SysUserPermVO getUserPerm(@RequestParam(required = false) Integer userId) {
        if (JwtUserContext.hasSuperOrSystemAdmin()) {
            TenantIdContext.removeFlag();
        }
        SysUserPermVO userPerm = this.iSysPermBusinessService.getUserPerm(
                SysUserPermDTO.builder()
                        .userId(userId == null ? SysUserContext.getUserId() : userId)
                        .build()
        );
        userPerm.setPassword(null);
        return userPerm;
    }

    @NoRepeatSubmit
    @PostMapping("saveRoleRePerm")
    @ApiOperation("保存角色权限（菜单权限+按钮权限+数据权限）")
    public void saveRoleRePerm(@Validated @RequestBody SysRoleRePermSaveDTO params) {
        this.iSysPermBusinessService.saveRoleRePerm(params);
        this.iSysPermBusinessService.logoutUserByRole(params.getRoleId());
    }

    @GetMapping("getScopeIdListByRoleId")
    @ApiOperation("根据角色id拿到关联的数据权限")
    public List<Integer> getScopeIdListByRoleId(@RequestParam Integer roleId) {
        return this.iSysPermBusinessService.getScopeIdListByRoleId(roleId);
    }

}
