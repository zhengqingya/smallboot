package com.zhengqing.cms.api.mini;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.cms.model.dto.CmsJobApplyPageDTO;
import com.zhengqing.cms.model.dto.CmsJobApplySaveDTO;
import com.zhengqing.cms.model.dto.CmsJobBaseDTO;
import com.zhengqing.cms.model.dto.CmsJobCategoryListDTO;
import com.zhengqing.cms.model.vo.CmsJobApplyPageVO;
import com.zhengqing.cms.model.vo.CmsJobBaseVO;
import com.zhengqing.cms.model.vo.CmsJobCategoryListVO;
import com.zhengqing.cms.service.ICmsJobApplyService;
import com.zhengqing.cms.service.ICmsJobCategoryService;
import com.zhengqing.cms.service.ICmsJobService;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.enums.CommonStatusEnum;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
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
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_CMS + "/job")
@Api(tags = {"mini-内容管理-招聘岗位"})
public class MiniCmsJobController extends BaseController {

    private final ICmsJobService iCmsJobService;
    private final ICmsJobCategoryService iCmsJobCategoryService;
    private final ICmsJobApplyService iCmsJobApplyService;

    @GetMapping("category/list")
    @ApiOperation("分类列表")
    public List<CmsJobCategoryListVO> list(@Validated @ModelAttribute CmsJobCategoryListDTO params) {
        return this.iCmsJobCategoryService.list(params);
    }

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<CmsJobBaseVO> page(@Validated @ModelAttribute CmsJobBaseDTO params) {
        params.setStatus(CommonStatusEnum.ENABLE.getStatus());
        return this.iCmsJobService.page(params);
    }

    @GetMapping("detail")
    @ApiOperation("详情")
    public CmsJobBaseVO detail(@Validated @ModelAttribute CmsJobBaseDTO params) {
        params.setStatus(CommonStatusEnum.ENABLE.getStatus());
        return this.iCmsJobService.detail(params);
    }

    @NoRepeatSubmit
    @PostMapping("apply")
    @ApiOperation("申请")
    public void apply(@Validated @RequestBody CmsJobApplySaveDTO params) {
        this.iCmsJobApplyService.addOrUpdateData(params);
    }

    @GetMapping("apply/page")
    @ApiOperation("申请-分页列表")
    public IPage<CmsJobApplyPageVO> applyPage(@Validated @ModelAttribute CmsJobApplyPageDTO params) {
        return this.iCmsJobApplyService.page(params);
    }

}
