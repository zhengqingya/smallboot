package com.zhengqing.system.api.web;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.dto.SysDeptSaveDTO;
import com.zhengqing.system.model.dto.SysDeptTreeDTO;
import com.zhengqing.system.model.vo.SysDeptTreeVO;
import com.zhengqing.system.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p> 系统管理-部门 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 18:10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/dept")
@Api(tags = {"web-系统管理-部门"})
public class WebSysDeptController extends BaseController {

    private final ISysDeptService iSysDeptService;

    @GetMapping("tree")
    @ApiOperation("树")
    public List<SysDeptTreeVO> tree(@Validated @ModelAttribute SysDeptTreeDTO params) {
        return this.iSysDeptService.tree(params);
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody SysDeptSaveDTO params) {
        params.setId(null);
        this.iSysDeptService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody SysDeptSaveDTO params) {
        this.iSysDeptService.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.iSysDeptService.deleteData(id);
    }

}
