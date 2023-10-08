package com.zhengqing.system.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.dto.SysTenantPackageListDTO;
import com.zhengqing.system.model.dto.SysTenantPackagePageDTO;
import com.zhengqing.system.model.dto.SysTenantPackageSaveDTO;
import com.zhengqing.system.model.vo.SysTenantPackageListVO;
import com.zhengqing.system.model.vo.SysTenantPackagePageVO;
import com.zhengqing.system.service.ISysTenantPackageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p> 系统管理-租户套餐 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 10:34
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/tenant/package")
@Api(tags = {"web-系统管理-租户套餐"})
public class WebSysTenantPackageController extends BaseController {

    private final ISysTenantPackageService iSysTenantPackageService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<SysTenantPackagePageVO> page(@Validated @ModelAttribute SysTenantPackagePageDTO params) {
        return this.iSysTenantPackageService.page(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<SysTenantPackageListVO> list(@Validated @ModelAttribute SysTenantPackageListDTO params) {
        return this.iSysTenantPackageService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody SysTenantPackageSaveDTO params) {
        params.setId(null);
        this.iSysTenantPackageService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody SysTenantPackageSaveDTO params) {
        this.iSysTenantPackageService.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.iSysTenantPackageService.deleteData(id);
    }

}
