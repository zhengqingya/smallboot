package com.zhengqing.mall.api.mini;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.model.dto.MiniPmsSpuRatePageDTO;
import com.zhengqing.mall.model.dto.MiniPmsSpuRateSaveDTO;
import com.zhengqing.mall.model.vo.MiniPmsSpuRatePageVO;
import com.zhengqing.mall.service.MiniPmsSpuRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p> 商城-商品评价 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 17:10
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_MALL + "/spu/rate")
@Api(tags = {"mini-商品评价"})
public class MiniPmsSpuRateController {

    @Resource
    private MiniPmsSpuRateService miniPmsSpuRateService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<MiniPmsSpuRatePageVO> page(@Validated @ModelAttribute MiniPmsSpuRatePageDTO params) {
        return this.miniPmsSpuRateService.page(params);
    }

    @PostMapping("addBatch")
    @ApiOperation("批量新增")
    public void addBatch(@Validated @RequestBody MiniPmsSpuRateSaveDTO params) {
        this.miniPmsSpuRateService.addBatchData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam String spuRateId) {
        this.miniPmsSpuRateService.deleteData(spuRateId);
    }

}
