package com.zhengqing.system.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.dto.SysRoleBaseDTO;
import com.zhengqing.system.model.dto.SysRoleSaveDTO;
import com.zhengqing.system.model.vo.SysRoleAllPermissionDetailVO;
import com.zhengqing.system.model.vo.SysRoleBaseVO;
import com.zhengqing.system.service.ISysPermBusinessService;
import com.zhengqing.system.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统管理 - 角色管理接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/9/10 18:33
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/role")
@Api(tags = "web-系统管理-角色管理接口")
public class WebSysRoleController extends BaseController {

    private final ISysRoleService iSysRoleService;
    private final ISysPermBusinessService iSysPermBusinessService;

    @GetMapping("page")
    @ApiOperation("列表分页")
    public ApiResult<IPage<SysRoleBaseVO>> page(@ModelAttribute SysRoleBaseDTO params) {
        return ApiResult.ok(this.iSysRoleService.listPage(params));
    }

    @GetMapping("tree")
    @ApiOperation("树")
    public ApiResult<List<SysRoleBaseVO>> tree(@Validated @ModelAttribute SysRoleBaseDTO params) {
        return ApiResult.ok(this.iSysRoleService.tree(params));
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public ApiResult<List<SysRoleBaseVO>> list(@ModelAttribute SysRoleBaseDTO params) {
        return ApiResult.ok(this.iSysRoleService.list(params));
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public ApiResult<Integer> add(@Validated @RequestBody SysRoleSaveDTO params) {
        params.setRoleId(null);
        return ApiResult.ok(this.iSysRoleService.addOrUpdateData(params));
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public ApiResult<Integer> update(@Validated(UpdateGroup.class) @RequestBody SysRoleSaveDTO params) {
        Integer roleId = this.iSysRoleService.addOrUpdateData(params);
        this.iSysPermBusinessService.logoutUserByRole(roleId);
        return ApiResult.ok(roleId);
    }

    @GetMapping("detail")
    @ApiOperation("详情(角色信息+菜单树+按钮+所拥有的权限)")
    public ApiResult<SysRoleAllPermissionDetailVO> detail(@RequestParam Integer roleId) {
        return ApiResult.ok(this.iSysPermBusinessService.permissionDetail(roleId));
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public ApiResult<Void> delete(@RequestParam Integer roleId) {
        this.iSysRoleService.deleteRoleAndRoleMenu(roleId);
        this.iSysPermBusinessService.logoutUserByRole(roleId);
        return ApiResult.ok();
    }

}
