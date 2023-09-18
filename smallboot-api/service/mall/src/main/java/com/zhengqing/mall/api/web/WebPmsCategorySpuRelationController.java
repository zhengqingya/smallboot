package com.zhengqing.mall.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.mall.model.dto.*;
import com.zhengqing.mall.model.vo.WebPmsCategorySpuRelationPageVO;
import com.zhengqing.mall.service.IPmsCategorySpuRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/category/spu/relation")
@Api(tags = {"web-分类关联商品"})
public class WebPmsCategorySpuRelationController {

    private final IPmsCategorySpuRelationService iPmsCategorySpuRelationService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<WebPmsCategorySpuRelationPageVO> page(@Validated @ModelAttribute WebPmsCategorySpuRelationPageDTO params) {
        return this.iPmsCategorySpuRelationService.page(params);
    }

    @PostMapping("")
    @ApiOperation("新增")
    public String add(@Validated @RequestBody WebPmsCategorySpuRelationSaveDTO params) {
        params.setId(null);
        return this.iPmsCategorySpuRelationService.addOrUpdateData(params);
    }

    @PutMapping("")
    @ApiOperation("更新")
    public String update(@Validated(UpdateGroup.class) @RequestBody WebPmsCategorySpuRelationSaveDTO params) {
        return this.iPmsCategorySpuRelationService.addOrUpdateData(params);
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    public void delete(@RequestParam List<String> idList) {
        this.iPmsCategorySpuRelationService.deleteBatch(idList);
    }

    @PutMapping("updateBatchShow")
    @ApiOperation("批量更新显示状态")
    public Boolean updateBatchShow(@Validated @RequestBody WebPmsCategorySpuRelationEditShowDTO params) {
        this.iPmsCategorySpuRelationService.updateBatchShow(params);
        return true;
    }

    @PutMapping("updateBatchPut")
    @ApiOperation("批量更新上下架状态")
    public Boolean updateBatchPut(@Validated @RequestBody WebPmsCategorySpuRelationEditPutDTO params) {
        this.iPmsCategorySpuRelationService.updateBatchPut(params);
        return true;
    }

    @PutMapping("updateBatchSort")
    @ApiOperation("批量排序")
    public Boolean updateBatchSort(@Validated @RequestBody ValidList<WebPmsCategorySpuRelationEditSortDTO> list) {
        this.iPmsCategorySpuRelationService.updateBatchSort(list);
        return true;
    }

}
