package com.zhengqing.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.mall.common.model.vo.MallTabConditionListVO;
import com.zhengqing.mall.service.WebPmsSpuService;
import com.zhengqing.mall.web.model.dto.*;
import com.zhengqing.mall.web.model.vo.WebPmsSpuDetailVO;
import com.zhengqing.mall.web.model.vo.WebPmsSpuPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p> 商城-商品 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/spu")
@Api(tags = {"web-商品"})
public class WebPmsSpuController {

    @Resource
    private WebPmsSpuService webPmsSpuService;

    @GetMapping("getTabCondition")
    @ApiOperation("获取tab条件")
    public List<MallTabConditionListVO> getTabCondition(@Validated @ModelAttribute WebPmsSpuPageDTO params) {
        params.setTabValue(null);
        return this.webPmsSpuService.getTabCondition(params);
    }

    @GetMapping("page")
    @ApiOperation("列表分页")
    public IPage<WebPmsSpuPageVO> page(@Validated @ModelAttribute WebPmsSpuPageDTO params) {
        return this.webPmsSpuService.page(params);
    }

    @GetMapping("")
    @ApiOperation("详情")
    public WebPmsSpuDetailVO detail(@RequestParam String id) {
        return this.webPmsSpuService.detail(id);
    }

    @PostMapping("")
    @ApiOperation("新增")
    public String add(@Validated @RequestBody WebPmsSpuSaveDTO params) {
        params.setId(null);
        return this.webPmsSpuService.addOrUpdateData(params);
    }

    @PutMapping("")
    @ApiOperation("更新")
    public String update(@Validated(UpdateGroup.class) @RequestBody WebPmsSpuSaveDTO params) {
        return this.webPmsSpuService.addOrUpdateData(params);
    }

    @PutMapping("updateBatchPut")
    @ApiOperation("批量更新上下架状态")
    public Boolean updateBatchPut(@Validated @RequestBody WebPmsSpuEditPutDTO params) {
        this.webPmsSpuService.updateBatchPut(params);
        return true;
    }

    @PutMapping("updateBatchShow")
    @ApiOperation("批量更新显示状态")
    public Boolean updateBatchShow(@Validated @RequestBody WebPmsSpuEditShowDTO params) {
        this.webPmsSpuService.updateBatchShow(params);
        return true;
    }

    @PutMapping("updateBatchPresell")
    @ApiOperation("批量更新预售状态")
    public Boolean updateBatchPresell(@Validated @RequestBody WebPmsSpuEditPresellDTO params) {
        this.webPmsSpuService.updateBatchPresell(params);
        return true;
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    public Boolean deleteBatch(@Validated @ModelAttribute WebPmsSpuDeleteDTO params) {
        this.webPmsSpuService.deleteBatchForBusiness(params);
        return true;
    }

    @PutMapping("updateBatchSort")
    @ApiOperation("批量排序")
    public Boolean updateBatchSort(@Validated @RequestBody ValidList<WebPmsSpuEditSortListDTO> list) {
        this.webPmsSpuService.updateBatchSort(list);
        return true;
    }

    @PutMapping("updateBatchVirtualUseStock")
    @ApiOperation("批量修改虚拟销量")
    public Boolean updateBatchVirtualUseStock(@Validated @RequestBody ValidList<WebPmsSpuEditVirtualUseStockDTO> list) {
        this.webPmsSpuService.updateBatchVirtualUseStock(list);
        return true;
    }

}
