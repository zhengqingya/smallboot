package com.zhengqing.cms.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.cms.model.dto.CmsJobBaseDTO;
import com.zhengqing.cms.model.dto.CmsJobSaveDTO;
import com.zhengqing.cms.model.vo.CmsJobBaseVO;
import com.zhengqing.cms.service.ICmsJobService;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p> 内容管理-招聘岗位 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_CMS + "/job")
@Api(tags = {"web-内容管理-招聘岗位"})
public class WebCmsJobController extends BaseController {

    private final ICmsJobService iCmsJobService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<CmsJobBaseVO> page(@Validated @ModelAttribute CmsJobBaseDTO params) {
        return this.iCmsJobService.page(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<CmsJobBaseVO> list(@Validated @ModelAttribute CmsJobBaseDTO params) {
        return this.iCmsJobService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody CmsJobSaveDTO params) {
        params.setId(null);
        this.iCmsJobService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody CmsJobSaveDTO params) {
        this.iCmsJobService.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.iCmsJobService.deleteData(id);
    }

}
