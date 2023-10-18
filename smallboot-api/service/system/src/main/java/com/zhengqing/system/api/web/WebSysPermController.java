package com.zhengqing.system.api.web;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.context.SysUserContext;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.system.model.bo.SysRoleRePermSaveBO;
import com.zhengqing.system.model.dto.SysRoleRePermSaveDTO;
import com.zhengqing.system.model.dto.SysRoleReScopeSaveDTO;
import com.zhengqing.system.model.dto.SysUserPermDTO;
import com.zhengqing.system.model.vo.SysUserPermVO;
import com.zhengqing.system.service.ISysPermBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        SysUserPermVO userPerm = this.iSysPermBusinessService.getUserPerm(
                SysUserPermDTO.builder()
                        .userId(userId == null ? SysUserContext.getUserId() : userId)
                        .build()
        );
        userPerm.setPassword(null);
        return userPerm;
    }

    @NoRepeatSubmit
    @PostMapping("saveRoleReMenu")
    @ApiOperation("保存角色权限（菜单权限+按钮权限）")
    public void saveRoleReMenu(@Validated @RequestBody SysRoleRePermSaveDTO params) {
        this.iSysPermBusinessService.saveRoleRePerm(SysRoleRePermSaveBO.builder()
                .roleId(params.getRoleId())
                .menuIdList(params.getMenuIdList())
                .build());
    }

    @NoRepeatSubmit
    @PostMapping("saveRoleReScope")
    @ApiOperation("保存角色权限（数据权限）")
    public void saveRoleReScope(@Validated @RequestBody SysRoleReScopeSaveDTO params) {
        this.iSysPermBusinessService.saveRoleReScope(params);
    }

}
