package com.zhengqing.mall.api.mini;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.model.dto.MiniPmsSpuPresellRemindDTO;
import com.zhengqing.mall.model.dto.PmsSpuPageDTO;
import com.zhengqing.mall.model.vo.PmsSpuBaseVO;
import com.zhengqing.mall.service.IPmsSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> 商城-商品 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_MALL + "/spu")
@Api(tags = {"mini-商品"})
public class MiniPmsSpuController {

    private final IPmsSpuService iPmsSpuService;

    @GetMapping("page")
    @ApiOperation("列表分页")
    public IPage<PmsSpuBaseVO> page(@Validated @ModelAttribute PmsSpuPageDTO params) {
        return this.iPmsSpuService.page(params);
    }

    @GetMapping("{id}")
    @ApiOperation("详情")
    public PmsSpuBaseVO detail(@ApiParam("商品id") @PathVariable String id) {
        return this.iPmsSpuService.detail(id);
    }

    @PostMapping("presellRemind")
    @ApiOperation("预售提醒")
    public boolean presellRemind(@Validated @RequestBody MiniPmsSpuPresellRemindDTO params) {
        this.iPmsSpuService.presellRemind(params);
        return true;
    }

}
