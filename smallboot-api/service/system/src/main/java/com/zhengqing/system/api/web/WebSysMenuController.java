package com.zhengqing.system.api.web;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.dto.SysMenuReBtnPermSaveDTO;
import com.zhengqing.system.model.dto.SysMenuSaveDTO;
import com.zhengqing.system.model.vo.SysMenuReBtnPermListVO;
import com.zhengqing.system.service.ISysMenuService;
import com.zhengqing.system.service.ISysPermBusinessService;
import com.zhengqing.system.service.ISysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统管理 - 菜单表接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:52
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/menu")
@Api(tags = "web-系统管理-菜单表接口")
public class WebSysMenuController extends BaseController {

    private final ISysMenuService iSysMenuService;

    private final ISysPermissionService iSysPermissionService;

    private final ISysPermBusinessService iSysPermBusinessService;

    // @GetMapping("/listPage")
    // @ApiOperation("列表分页")
    // public IPage<SysMenu> listPage(@ModelAttribute SysMenuDTO params) {
    // return menuService.listPage(params);
    // }
    //
    // @GetMapping("/list")
    // @ApiOperation("列表")
    // public List<SysMenu> list(@ModelAttribute SysMenuDTO params) {
    // return menuService.list(params);
    // }

    @GetMapping("menuTree")
    @ApiOperation("菜单树")
    public List<SysMenuTree> menuTree(@RequestParam(required = false) Integer roleId) {
        return this.iSysPermBusinessService.tree(Lists.newArrayList(roleId), false);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody SysMenuSaveDTO params) {
        return this.iSysMenuService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody SysMenuSaveDTO params) {
        return this.iSysMenuService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer menuId) {
        this.iSysMenuService.removeById(menuId);
    }

    // 下：菜单按钮权限(菜单页面中配置页面所属按钮使用)

    @GetMapping("getPermListByMenuId")
    @ApiOperation("菜单关联按钮权限-列表")
    public List<SysMenuReBtnPermListVO> getPermListByMenuId(@RequestParam Integer menuId) {
        return this.iSysPermissionService.getPermListByMenuId(menuId);
    }

    @DeleteMapping("deleteMenuReBtnPerm")
    @ApiOperation("菜单关联按钮权限-删除")
    public void deleteMenuReBtnPerm(@RequestParam Integer id) {
        this.iSysPermissionService.removeById(id);
        this.iSysPermBusinessService.refreshRedisPerm();
    }

    @NoRepeatSubmit
    @PostMapping("addMenuReBtnPerm")
    @ApiOperation("菜单关联按钮权限-新增")
    public void addMenuReBtnPerm(@Validated @RequestBody SysMenuReBtnPermSaveDTO params) {
        params.setId(null);
        this.iSysPermissionService.addOrUpdateData(params);
        this.iSysPermBusinessService.refreshRedisPerm();
    }

    @NoRepeatSubmit
    @PutMapping("updateMenuReBtnPerm")
    @ApiOperation("菜单关联按钮权限-更新")
    public void updateMenuReBtnPerm(@Validated(UpdateGroup.class) @RequestBody SysMenuReBtnPermSaveDTO params) {
        this.iSysPermissionService.addOrUpdateData(params);
        this.iSysPermBusinessService.refreshRedisPerm();
    }

}
