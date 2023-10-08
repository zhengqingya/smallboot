package com.zhengqing.system.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.dto.SysUserListDTO;
import com.zhengqing.system.model.dto.SysUserRoleSaveDTO;
import com.zhengqing.system.model.dto.SysUserSaveDTO;
import com.zhengqing.system.model.dto.SysUserUpdatePasswordDTO;
import com.zhengqing.system.model.vo.SysUserDetailVO;
import com.zhengqing.system.model.vo.SysUserListVO;
import com.zhengqing.system.service.ISysUserRoleService;
import com.zhengqing.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 系统管理 - 用户管理接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/user")
@Api(tags = "web-系统管理-用户管理接口")
public class WebSysUserController extends BaseController {

    private final ISysUserService iSysUserService;
    private final ISysUserRoleService iSysUserRoleService;

    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<SysUserListVO> listPage(@ModelAttribute SysUserListDTO params) {
        return this.iSysUserService.listPage(params);
    }

    @NoRepeatSubmit
    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated @RequestBody SysUserSaveDTO params) {
        return this.iSysUserService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody SysUserSaveDTO params) {
        return this.iSysUserService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer userId) {
        this.iSysUserService.deleteUser(userId);
    }

    @GetMapping("")
    @ApiOperation("详情")
    public SysUserDetailVO detail(@RequestParam Integer userId) {
        return this.iSysUserService.detail(userId);
    }

    @PutMapping("updatePassword")
    @ApiOperation("修改用户密码")
    public void updatePassword(@RequestBody @Valid SysUserUpdatePasswordDTO params) {
        this.iSysUserService.updatePassword(params);
    }

    @GetMapping("resetPassword")
    @ApiOperation("重置用户密码")
    public void resetPassword(@RequestParam Integer userId, @RequestParam(required = false) String password) {
        this.iSysUserService.resetPassword(userId, password);
    }

    @PostMapping("saveRoleIds")
    @ApiOperation("保存用户角色ids")
    public void saveRoleIds(@Validated @RequestBody SysUserRoleSaveDTO params) {
        this.iSysUserRoleService.addOrUpdateData(params);
    }

}
