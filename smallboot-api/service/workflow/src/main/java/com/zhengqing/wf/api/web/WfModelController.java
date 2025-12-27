package com.zhengqing.wf.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.wf.model.dto.WfModelPageDTO;
import com.zhengqing.wf.model.dto.WfModelSaveDTO;
import com.zhengqing.wf.model.vo.WfModelDetailVO;
import com.zhengqing.wf.model.vo.WfModelPageVO;
import com.zhengqing.wf.service.IWfModelService;
import com.zhengqing.common.base.model.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> 工作流-模型 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/12/04 17:06
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_WF + "/model")
@Api(tags = {"web-工作流-模型"})
public class WfModelController extends BaseController {

    private final IWfModelService iWfModelService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public ApiResult<IPage<WfModelPageVO>> page(@Validated @ModelAttribute WfModelPageDTO params) {
        return ApiResult.ok(this.iWfModelService.page(params));
    }

    @GetMapping("detail")
    @ApiOperation("详情")
    public ApiResult<WfModelDetailVO> detail(@RequestParam String id) {
        return ApiResult.ok(this.iWfModelService.detail(id));
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public ApiResult<Void> add(@Validated @RequestBody WfModelSaveDTO params) {
        params.setId(null);
        this.iWfModelService.addOrUpdateData(params);
        return ApiResult.ok();
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public ApiResult<Void> update(@Validated(UpdateGroup.class) @RequestBody WfModelSaveDTO params) {
        this.iWfModelService.addOrUpdateData(params);
        return ApiResult.ok();
    }


}
