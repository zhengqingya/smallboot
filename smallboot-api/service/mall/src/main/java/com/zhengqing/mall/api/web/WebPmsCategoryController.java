package com.zhengqing.mall.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.mall.model.dto.WebPmsCategoryBaseDTO;
import com.zhengqing.mall.model.dto.WebPmsCategoryEditShowDTO;
import com.zhengqing.mall.model.dto.WebPmsCategorySaveDTO;
import com.zhengqing.mall.model.vo.WebPmsCategoryBaseVO;
import com.zhengqing.mall.service.IPmsCategoryService;
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

    private final IPmsCategoryService iPmsCategoryService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<WebPmsCategoryBaseVO> page(@Validated @ModelAttribute WebPmsCategoryBaseDTO params) {
        return this.iPmsCategoryService.page(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<WebPmsCategoryBaseVO> list(@Validated @ModelAttribute WebPmsCategoryBaseDTO params) {
        return this.iPmsCategoryService.list(params);
    }

    @PostMapping("")
    @ApiOperation("新增")
    public Long add(@Validated @RequestBody WebPmsCategorySaveDTO params) {
        params.setId(null);
        return this.iPmsCategoryService.addOrUpdateData(params);
    }

    @PutMapping("")
    @ApiOperation("更新")
    public Long update(@Validated(UpdateGroup.class) @RequestBody WebPmsCategorySaveDTO params) {
        return this.iPmsCategoryService.addOrUpdateData(params);
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    public void deleteBatch(@RequestParam List<String> idList) {
        this.iPmsCategoryService.deleteBatchForBusiness(idList);
    }

    @PutMapping("updateBatchShow")
    @ApiOperation("批量更新显示状态")
    public Boolean updateBatchShow(@Validated @RequestBody WebPmsCategoryEditShowDTO params) {
        this.iPmsCategoryService.updateBatchShow(params);
        return true;
    }

}
