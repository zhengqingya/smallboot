package com.zhengqing.cms.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.cms.model.dto.CmsJobApplyPageDTO;
import com.zhengqing.cms.model.dto.CmsJobApplySaveDTO;
import com.zhengqing.cms.model.vo.CmsJobApplyPageVO;
import com.zhengqing.cms.service.ICmsJobApplyService;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> 内容管理-职位申请 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 18:11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_CMS + "/job/apply")
@Api(tags = {"web-内容管理-职位申请"})
public class WebCmsJobApplyController extends BaseController {

    private final ICmsJobApplyService iCmsJobApplyService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<CmsJobApplyPageVO> page(@Validated @ModelAttribute CmsJobApplyPageDTO params) {
        return this.iCmsJobApplyService.page(params);
    }

    //    @NoRepeatSubmit
//    @PostMapping("add")
//    @ApiOperation("新增")
//    public void add(@Validated @RequestBody CmsJobApplySaveDTO params) {
//        params.setId(null);
//        this.cmsJobApplyService.addOrUpdateData(params);
//    }
//
    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody CmsJobApplySaveDTO params) {
        this.iCmsJobApplyService.addOrUpdateData(params);
    }
//
//    @DeleteMapping("delete")
//    @ApiOperation("删除")
//    public void delete(@RequestParam Integer id) {
//        this.cmsJobApplyService.deleteData(id);
//    }

}
