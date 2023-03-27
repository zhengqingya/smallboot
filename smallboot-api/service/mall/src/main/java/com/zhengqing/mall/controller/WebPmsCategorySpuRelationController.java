package com.zhengqing.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.mall.service.WebPmsCategorySpuRelationService;
import com.zhengqing.mall.web.model.dto.*;
import com.zhengqing.mall.web.model.vo.WebPmsCategorySpuRelationPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p> 商城-分类关联商品 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 14:01
 */
@AllArgsConstructor
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/category/spu/relation")
@Api(tags = {"web-分类关联商品"})
public class WebPmsCategorySpuRelationController {

    private WebPmsCategorySpuRelationService webPmsCategorySpuRelationService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<WebPmsCategorySpuRelationPageVO> page(@Validated @ModelAttribute WebPmsCategorySpuRelationPageDTO params) {
        return this.webPmsCategorySpuRelationService.page(params);
    }

    @PostMapping("")
    @ApiOperation("新增")
    public String add(@Validated @RequestBody WebPmsCategorySpuRelationSaveDTO params) {
        return this.webPmsCategorySpuRelationService.addData(params);
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    public void delete(@RequestParam List<String> idList) {
        this.webPmsCategorySpuRelationService.deleteBatch(idList);
    }

    @PutMapping("updateBatchShow")
    @ApiOperation("批量更新显示状态")
    public Boolean updateBatchShow(@Validated @RequestBody WebPmsCategorySpuRelationEditShowDTO params) {
        this.webPmsCategorySpuRelationService.updateBatchShow(params);
        return true;
    }

    @PutMapping("updateBatchPut")
    @ApiOperation("批量更新上下架状态")
    public Boolean updateBatchPut(@Validated @RequestBody WebPmsCategorySpuRelationEditPutDTO params) {
        this.webPmsCategorySpuRelationService.updateBatchPut(params);
        return true;
    }

    @PutMapping("updateBatchSort")
    @ApiOperation("批量排序")
    public Boolean updateBatchSort(@Validated @RequestBody ValidList<WebPmsCategorySpuRelationEditSortDTO> list) {
        this.webPmsCategorySpuRelationService.updateBatchSort(list);
        return true;
    }

}
