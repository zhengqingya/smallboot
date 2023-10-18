package com.zhengqing.system.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
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

    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<SysRoleBaseVO> listPage(@ModelAttribute SysRoleBaseDTO params) {
        return this.iSysRoleService.listPage(params);
    }

    @GetMapping("tree")
    @ApiOperation("树")
    public List<SysRoleBaseVO> tree(@Validated @ModelAttribute SysRoleBaseDTO params) {
        return this.iSysRoleService.tree(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<SysRoleBaseVO> list(@ModelAttribute SysRoleBaseDTO params) {
        return this.iSysRoleService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody SysRoleSaveDTO params) {
        return this.iSysRoleService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody SysRoleSaveDTO params) {
        return this.iSysRoleService.addOrUpdateData(params);
    }

    @GetMapping("detail")
    @ApiOperation("详情(角色信息+菜单树+按钮+所拥有的权限)")
    public SysRoleAllPermissionDetailVO detail(@RequestParam Integer roleId) {
        return this.iSysPermBusinessService.permissionDetail(roleId);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer roleId) {
        this.iSysRoleService.deleteRoleAndRoleMenu(roleId);
    }

}
