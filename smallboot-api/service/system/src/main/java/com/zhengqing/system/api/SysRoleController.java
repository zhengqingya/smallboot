package com.zhengqing.system.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.dto.SysRoleListDTO;
import com.zhengqing.system.model.dto.SysRoleSaveDTO;
import com.zhengqing.system.model.vo.SysRoleAllPermissionDetailVO;
import com.zhengqing.system.model.vo.SysRoleListVO;
import com.zhengqing.system.service.ISysPermBusinessService;
import com.zhengqing.system.service.ISysRolePermissionService;
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
@Api(tags = "系统管理 - 角色管理接口")
public class SysRoleController extends BaseController {

    private final ISysRoleService roleService;
    private final ISysPermBusinessService sysPermBusinessService;
    private final ISysRolePermissionService sysRolePermissionService;

    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<SysRoleListVO> listPage(@ModelAttribute SysRoleListDTO params) {
        return this.roleService.listPage(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<SysRoleListVO> list(@ModelAttribute SysRoleListDTO params) {
        return this.roleService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody SysRoleSaveDTO params) {
        return this.roleService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody SysRoleSaveDTO params) {
        return this.roleService.addOrUpdateData(params);
    }

    @GetMapping("detail")
    @ApiOperation("详情(角色信息+菜单树+按钮+所拥有的权限)")
    public SysRoleAllPermissionDetailVO detail(@RequestParam Integer roleId) {
        return this.sysPermBusinessService.permissionDetail(roleId);
    }

//    @GetMapping("permissionDetail")
//    @ApiOperation("详情(带树+按钮+所拥有的权限)")
//    public SysRoleAllPermissionDetailVO permissionDetail(@RequestParam Integer roleId) {
//        return this.roleService.permissionDetail(roleId);
//    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer roleId) {
        this.roleService.deleteRoleAndRoleMenu(roleId);
    }

    // ======================== ↓↓↓↓↓↓ 角色关联菜单按钮权限 ↓↓↓↓↓↓ ==========================

    @GetMapping("getPermissionBtnsByRoleIdAndMenuId")
    @ApiOperation("通过角色id和菜单id查询该菜单所拥有的所有按钮")
    public List<Integer> getPermissionBtnsByRoleIdAndMenuId(@RequestParam Integer roleId,
                                                            @RequestParam Integer menuId) {
        return this.sysRolePermissionService.getPermissionBtnsByRoleIdAndMenuId(roleId, menuId);
    }


}
