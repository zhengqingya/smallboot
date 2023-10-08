package com.zhengqing.system.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.dto.SysTenantPageDTO;
import com.zhengqing.system.model.dto.SysTenantSaveDTO;
import com.zhengqing.system.model.vo.SysTenantPageVO;
import com.zhengqing.system.service.ISysTenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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

    private final ISysTenantService sysTenantService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<SysTenantPageVO> page(@Validated @ModelAttribute SysTenantPageDTO params) {
        return this.sysTenantService.page(params);
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody SysTenantSaveDTO params) {
        params.setId(null);
        this.sysTenantService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody SysTenantSaveDTO params) {
        this.sysTenantService.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.sysTenantService.deleteData(id);
    }

}
