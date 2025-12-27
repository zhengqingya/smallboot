package com.zhengqing.system.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.auth.custom.open.ApiOpen;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.dto.SysTenantListDTO;
import com.zhengqing.system.model.dto.SysTenantPageDTO;
import com.zhengqing.system.model.dto.SysTenantSaveDTO;
import com.zhengqing.system.model.vo.SysTenantListVO;
import com.zhengqing.system.model.vo.SysTenantPageVO;
import com.zhengqing.system.service.ISysTenantService;
import com.zhengqing.common.base.model.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p> 系统管理-租户信息 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 15:40
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/tenant")
@Api(tags = {"web-系统管理-租户信息"})
public class WebSysTenantController extends BaseController {

    private final ISysTenantService iSysTenantService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public ApiResult<IPage<SysTenantPageVO>> page(@Validated @ModelAttribute SysTenantPageDTO params) {
        return ApiResult.ok(this.iSysTenantService.page(params));
    }

    @ApiOpen
    @GetMapping("list")
    @ApiOperation("列表")
    public ApiResult<List<SysTenantListVO>> list(@Validated @ModelAttribute SysTenantListDTO params) {
        return ApiResult.ok(this.iSysTenantService.list(params));
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public ApiResult<Void> add(@Validated @RequestBody SysTenantSaveDTO params) {
        params.setId(null);
        this.iSysTenantService.addOrUpdateData(params);
        return ApiResult.ok();
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public ApiResult<Void> update(@Validated(UpdateGroup.class) @RequestBody SysTenantSaveDTO params) {
        this.iSysTenantService.addOrUpdateData(params);
        return ApiResult.ok();
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public ApiResult<Void> delete(@RequestParam Integer id) {
        this.iSysTenantService.deleteData(id);
        return ApiResult.ok();
    }

}
