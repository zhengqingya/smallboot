package com.zhengqing.mall.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.mall.model.dto.WebPmsCategoryBaseDTO;
import com.zhengqing.mall.model.dto.WebPmsCategoryEditShowDTO;
import com.zhengqing.mall.model.dto.WebPmsCategorySaveDTO;
import com.zhengqing.mall.model.vo.WebPmsCategoryBaseVO;
import com.zhengqing.mall.service.IOmsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p> 商城-分类 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 14:01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/category")
@Api(tags = {"web-分类"})
public class WebPmsCategoryController {

    private final IOmsCategoryService iOmsCategoryService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<WebPmsCategoryBaseVO> page(@Validated @ModelAttribute WebPmsCategoryBaseDTO params) {
        return this.iOmsCategoryService.page(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<WebPmsCategoryBaseVO> list(@Validated @ModelAttribute WebPmsCategoryBaseDTO params) {
        return this.iOmsCategoryService.list(params);
    }

    @PostMapping("")
    @ApiOperation("新增")
    public String add(@Validated @RequestBody WebPmsCategorySaveDTO params) {
        params.setId(null);
        return this.iOmsCategoryService.addOrUpdateData(params);
    }

    @PutMapping("")
    @ApiOperation("更新")
    public String update(@Validated(UpdateGroup.class) @RequestBody WebPmsCategorySaveDTO params) {
        return this.iOmsCategoryService.addOrUpdateData(params);
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    public void deleteBatch(@RequestParam List<String> idList) {
        this.iOmsCategoryService.deleteBatchForBusiness(idList);
    }

    @PutMapping("updateBatchShow")
    @ApiOperation("批量更新显示状态")
    public Boolean updateBatchShow(@Validated @RequestBody WebPmsCategoryEditShowDTO params) {
        this.iOmsCategoryService.updateBatchShow(params);
        return true;
    }

}
