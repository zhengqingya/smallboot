package com.zhengqing.system.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.dto.SysPostListDTO;
import com.zhengqing.system.model.dto.SysPostPageDTO;
import com.zhengqing.system.model.dto.SysPostSaveDTO;
import com.zhengqing.system.model.vo.SysPostListVO;
import com.zhengqing.system.model.vo.SysPostPageVO;
import com.zhengqing.system.service.ISysPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p> 系统管理-岗位 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 17:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/post")
@Api(tags = {"web-系统管理-岗位"})
public class WebSysPostController extends BaseController {

    private final ISysPostService iSysPostService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<SysPostPageVO> page(@Validated @ModelAttribute SysPostPageDTO params) {
        return this.iSysPostService.page(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<SysPostListVO> list(@Validated @ModelAttribute SysPostListDTO params) {
        return this.iSysPostService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody SysPostSaveDTO params) {
        params.setId(null);
        this.iSysPostService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody SysPostSaveDTO params) {
        this.iSysPostService.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.iSysPostService.deleteData(id);
    }

}
