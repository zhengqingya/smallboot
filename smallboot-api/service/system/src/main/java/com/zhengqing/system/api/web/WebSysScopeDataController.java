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
    public IPage<SysScopeDataBaseVO> page(@Validated @ModelAttribute SysScopeDataBaseDTO params) {
        return this.sysScopeDataService.page(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<SysScopeDataBaseVO> list(@Validated @ModelAttribute SysScopeDataBaseDTO params) {
        return this.sysScopeDataService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody SysScopeDataSaveDTO params) {
        params.setId(null);
        this.sysScopeDataService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody SysScopeDataSaveDTO params) {
        this.sysScopeDataService.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.sysScopeDataService.deleteData(id);
    }

}
