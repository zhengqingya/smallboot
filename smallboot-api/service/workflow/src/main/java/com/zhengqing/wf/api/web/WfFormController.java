package com.zhengqing.wf.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.wf.model.dto.WfFormDetailDTO;
import com.zhengqing.wf.model.dto.WfFormListDTO;
import com.zhengqing.wf.model.dto.WfFormPageDTO;
import com.zhengqing.wf.model.dto.WfFormSaveDTO;
import com.zhengqing.wf.model.vo.WfFormDetailVO;
import com.zhengqing.wf.model.vo.WfFormListVO;
import com.zhengqing.wf.model.vo.WfFormPageVO;
import com.zhengqing.wf.service.IWfFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p> 工作流-表单 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/12/04 17:06
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_WF + "/form")
@Api(tags = {"web-工作流-表单"})
public class WfFormController extends BaseController {

    private final IWfFormService iWfFormService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<WfFormPageVO> page(@Validated @ModelAttribute WfFormPageDTO params) {
        return this.iWfFormService.page(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<WfFormListVO> list(@Validated @ModelAttribute WfFormListDTO params) {
        return this.iWfFormService.list(params);
    }

    @GetMapping("detail")
    @ApiOperation("详情")
    public WfFormDetailVO detail(@Validated @ModelAttribute WfFormDetailDTO params) {
        return this.iWfFormService.detail(params);
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody WfFormSaveDTO params) {
        params.setId(null);
        this.iWfFormService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody WfFormSaveDTO params) {
        this.iWfFormService.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Long id) {
        this.iWfFormService.deleteData(id);
    }

}
