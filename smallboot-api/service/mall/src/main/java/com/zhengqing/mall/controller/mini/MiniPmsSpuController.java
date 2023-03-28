package com.zhengqing.mall.controller.mini;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.model.dto.MiniPmsSpuPageDTO;
import com.zhengqing.mall.model.dto.MiniPmsSpuPresellRemindDTO;
import com.zhengqing.mall.model.vo.MiniPmsSpuDetailVO;
import com.zhengqing.mall.model.vo.MiniPmsSpuPageVO;
import com.zhengqing.mall.service.MiniPmsSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p> 商城-商品 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_MALL + "/spu")
@Api(tags = {"mini-商品"})
public class MiniPmsSpuController {

    @Resource
    private MiniPmsSpuService miniPmsSpuService;

    @GetMapping("page")
    @ApiOperation("列表分页")
    public IPage<MiniPmsSpuPageVO> page(@Validated @ModelAttribute MiniPmsSpuPageDTO params) {
        return this.miniPmsSpuService.page(params);
    }

    @GetMapping("{id}")
    @ApiOperation("详情")
    public MiniPmsSpuDetailVO detail(@ApiParam("商品id") @PathVariable String id) {
        return this.miniPmsSpuService.detail(id);
    }

    @PostMapping("presellRemind")
    @ApiOperation("预售提醒")
    public boolean presellRemind(@Validated @RequestBody MiniPmsSpuPresellRemindDTO params) {
        this.miniPmsSpuService.presellRemind(params);
        return true;
    }

}
