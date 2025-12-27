package com.zhengqing.system.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.dto.SysScopeDataBaseDTO;
import com.zhengqing.system.model.dto.SysScopeDataSaveDTO;
import com.zhengqing.system.model.vo.SysScopeDataBaseVO;
import com.zhengqing.system.service.ISysScopeDataService;
import com.zhengqing.common.base.model.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p> 系统管理-数据权限 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/18 14:00
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/scope/data")
@Api(tags = {"web-系统管理-数据权限"})
public class WebSysScopeDataController extends BaseController {

    private final ISysScopeDataService sysScopeDataService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public ApiResult<IPage<SysScopeDataBaseVO>> page(@Validated @ModelAttribute SysScopeDataBaseDTO params) {
        return ApiResult.ok(this.sysScopeDataService.page(params));
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public ApiResult<List<SysScopeDataBaseVO>> list(@Validated @ModelAttribute SysScopeDataBaseDTO params) {
        return ApiResult.ok(this.sysScopeDataService.list(params));
    }

    @GetMapping("tree")
    @ApiOperation("树")
    public ApiResult<List<SysScopeDataBaseVO>> tree(@Validated @ModelAttribute SysScopeDataBaseDTO params) {
        return ApiResult.ok(this.sysScopeDataService.tree(params));
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public ApiResult<Void> add(@Validated @RequestBody SysScopeDataSaveDTO params) {
        params.setId(null);
        this.sysScopeDataService.addOrUpdateData(params);
        return ApiResult.ok();
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public ApiResult<Void> update(@Validated(UpdateGroup.class) @RequestBody SysScopeDataSaveDTO params) {
        this.sysScopeDataService.addOrUpdateData(params);
        return ApiResult.ok();
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public ApiResult<Void> delete(@RequestParam Integer id) {
        this.sysScopeDataService.deleteData(id);
        return ApiResult.ok();
    }

}
