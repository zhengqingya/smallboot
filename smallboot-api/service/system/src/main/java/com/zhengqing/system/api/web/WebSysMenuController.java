package com.zhengqing.system.api.web;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.bo.SysMenuTree;
import com.zhengqing.system.model.dto.SysMenuSaveDTO;
import com.zhengqing.system.model.dto.SysMenuTreeDTO;
import com.zhengqing.system.service.ISysMenuService;
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
 * 系统管理 - 菜单&按钮接口
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
@Api(tags = "web-系统管理-菜单&按钮接口")
public class WebSysMenuController extends BaseController {

    private final ISysMenuService iSysMenuService;

    private final ISysPermBusinessService iSysPermBusinessService;

    @GetMapping("tree")
    @ApiOperation("菜单树")
    public List<SysMenuTree> tree(@Validated @ModelAttribute SysMenuTreeDTO params) {
        return this.iSysPermBusinessService.tree(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody SysMenuSaveDTO params) {
        params.setId(null);
        this.iSysMenuService.addOrUpdateData(params);
        this.refreshPerm();
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody SysMenuSaveDTO params) {
        this.iSysMenuService.addOrUpdateData(params);
        this.refreshPerm();
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.iSysMenuService.removeById(id);
        this.refreshPerm();
    }

    /**
     * 刷新系统管理员权限 & 系统租户权限
     */
    private void refreshPerm() {
        this.iSysPermBusinessService.refreshSysTenantRePerm();
        this.iSysPermBusinessService.refreshSuperAdminPerm();
    }

}
