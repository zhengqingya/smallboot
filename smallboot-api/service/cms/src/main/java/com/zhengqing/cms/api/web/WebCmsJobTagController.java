package com.zhengqing.cms.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.cms.model.dto.CmsJobTagListDTO;
import com.zhengqing.cms.model.dto.CmsJobTagPageDTO;
import com.zhengqing.cms.model.dto.CmsJobTagSaveDTO;
import com.zhengqing.cms.model.vo.CmsJobTagListVO;
import com.zhengqing.cms.model.vo.CmsJobTagPageVO;
import com.zhengqing.cms.service.ICmsJobTagService;
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
 * <p> 内容管理-招聘岗位标签 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_CMS + "/job/tag")
@Api(tags = {"web-内容管理-招聘岗位标签"})
public class WebCmsJobTagController extends BaseController {

    private final ICmsJobTagService iCmsJobTagService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<CmsJobTagPageVO> page(@Validated @ModelAttribute CmsJobTagPageDTO params) {
        return this.iCmsJobTagService.page(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<CmsJobTagListVO> list(@Validated @ModelAttribute CmsJobTagListDTO params) {
        return this.iCmsJobTagService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody CmsJobTagSaveDTO params) {
        params.setId(null);
        this.iCmsJobTagService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody CmsJobTagSaveDTO params) {
        this.iCmsJobTagService.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.iCmsJobTagService.deleteData(id);
    }

}
