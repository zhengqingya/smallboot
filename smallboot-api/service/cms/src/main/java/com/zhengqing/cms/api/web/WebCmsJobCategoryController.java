package com.zhengqing.cms.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.cms.model.dto.CmsJobCategoryListDTO;
import com.zhengqing.cms.model.dto.CmsJobCategoryPageDTO;
import com.zhengqing.cms.model.dto.CmsJobCategorySaveDTO;
import com.zhengqing.cms.model.vo.CmsJobCategoryListVO;
import com.zhengqing.cms.model.vo.CmsJobCategoryPageVO;
import com.zhengqing.cms.service.ICmsJobCategoryService;
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
 * <p> 内容管理-招聘岗位分类 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:09
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_CMS + "/job/category")
@Api(tags = {"web-内容管理-招聘岗位分类"})
public class WebCmsJobCategoryController extends BaseController {

    private final ICmsJobCategoryService iCmsJobCategoryService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<CmsJobCategoryPageVO> page(@Validated @ModelAttribute CmsJobCategoryPageDTO params) {
        if (this.isFillMerchantId()) {
            params.setMerchantId(this.getCurrentUserReMerchantId());
        }
        return this.iCmsJobCategoryService.page(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<CmsJobCategoryListVO> list(@Validated @ModelAttribute CmsJobCategoryListDTO params) {
        if (this.isFillMerchantId()) {
            params.setMerchantId(this.getCurrentUserReMerchantId());
        }
        return this.iCmsJobCategoryService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody CmsJobCategorySaveDTO params) {
        params.setId(null);
        this.iCmsJobCategoryService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody CmsJobCategorySaveDTO params) {
        this.iCmsJobCategoryService.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.iCmsJobCategoryService.deleteData(id);
    }

}
