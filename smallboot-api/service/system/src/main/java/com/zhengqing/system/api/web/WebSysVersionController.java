package com.zhengqing.system.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.dto.SysVersionBaseDTO;
import com.zhengqing.system.model.dto.SysVersionSaveDTO;
import com.zhengqing.system.model.vo.SysVersionBaseVO;
import com.zhengqing.system.service.ISysVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> 系统管理-版本记录 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/15 14:58
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/version")
@Api(tags = {"web-系统管理-版本记录"})
public class WebSysVersionController extends BaseController {

    private final ISysVersionService iSysVersionService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<SysVersionBaseVO> page(@Validated @ModelAttribute SysVersionBaseDTO params) {
        return this.iSysVersionService.page(params);
    }

    @GetMapping("lately")
    @ApiOperation("最近的一条版本记录")
    public SysVersionBaseVO lately() {
        return this.iSysVersionService.lately();
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody SysVersionSaveDTO params) {
        params.setId(null);
        this.iSysVersionService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody SysVersionSaveDTO params) {
        this.iSysVersionService.addOrUpdateData(params);
    }

//    @DeleteMapping("delete")
//    @ApiOperation("删除")
//    public void delete(@RequestParam Integer id) {
//        this.iSysVersionService.deleteData(id);
//    }

}
