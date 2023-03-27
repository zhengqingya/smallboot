package com.zhengqing.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.mall.service.WebOmsCategoryService;
import com.zhengqing.mall.web.model.dto.WebPmsCategoryEditShowDTO;
import com.zhengqing.mall.web.model.dto.WebPmsCategoryListDTO;
import com.zhengqing.mall.web.model.dto.WebPmsCategoryPageDTO;
import com.zhengqing.mall.web.model.dto.WebPmsCategorySaveDTO;
import com.zhengqing.mall.web.model.vo.WebPmsCategoryListVO;
import com.zhengqing.mall.web.model.vo.WebPmsCategoryPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p> 商城-分类 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 14:01
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/category")
@Api(tags = {"web-分类"})
public class WebPmsCategoryController {

    @Resource
    private WebOmsCategoryService webPmsCategoryService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<WebPmsCategoryPageVO> page(@Validated @ModelAttribute WebPmsCategoryPageDTO params) {
        return this.webPmsCategoryService.page(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<WebPmsCategoryListVO> list(@Validated @ModelAttribute WebPmsCategoryListDTO params) {
        return this.webPmsCategoryService.list(params);
    }

    @PostMapping("")
    @ApiOperation("新增")
    public String add(@Validated @RequestBody WebPmsCategorySaveDTO params) {
        params.setId(null);
        return this.webPmsCategoryService.addOrUpdateData(params);
    }

    @PutMapping("")
    @ApiOperation("更新")
    public String update(@Validated(UpdateGroup.class) @RequestBody WebPmsCategorySaveDTO params) {
        return this.webPmsCategoryService.addOrUpdateData(params);
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    public void deleteBatch(@RequestParam List<String> idList) {
        this.webPmsCategoryService.deleteBatchForBusiness(idList);
    }

    @PutMapping("updateBatchShow")
    @ApiOperation("批量更新显示状态")
    public Boolean updateBatchShow(@Validated @RequestBody WebPmsCategoryEditShowDTO params) {
        this.webPmsCategoryService.updateBatchShow(params);
        return true;
    }

}
