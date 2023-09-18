package com.zhengqing.mall.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.model.dto.OmsOrderAfterSalePageDTO;
import com.zhengqing.mall.model.dto.WebOmsOrderAfterSaleUpdateDTO;
import com.zhengqing.mall.model.vo.MallTabConditionListVO;
import com.zhengqing.mall.model.vo.OmsOrderAfterSaleDetailVO;
import com.zhengqing.mall.model.vo.OmsOrderAfterSalePageVO;
import com.zhengqing.mall.service.IOmsOrderAfterSaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p> web-订单-售后 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/14 10:27
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/order/afterSale")
@Api(tags = {"web-订单-售后"})
public class WebOmsOrderAfterSaleController {

    private final IOmsOrderAfterSaleService iOmsOrderAfterSaleService;

    @GetMapping("getTabCondition")
    @ApiOperation("获取tab条件")
    public List<MallTabConditionListVO> getTabCondition(@ModelAttribute OmsOrderAfterSalePageDTO params) {
        params.setTabValue(null);
        return this.iOmsOrderAfterSaleService.getTabCondition(params);
    }

    @GetMapping("page")
    @ApiOperation("列表分页")
    public IPage<OmsOrderAfterSalePageVO> page(@Validated @ModelAttribute OmsOrderAfterSalePageDTO params) {
        return this.iOmsOrderAfterSaleService.page(params);
    }

    @GetMapping("")
    @ApiOperation("详情")
    public OmsOrderAfterSaleDetailVO detail(@RequestParam String afterSaleNo) {
        return this.iOmsOrderAfterSaleService.detailData(afterSaleNo);
    }

    @PutMapping("")
    @ApiOperation("更新售后信息（退款/退货退款/换货）")
    public Boolean updateData(@Validated @RequestBody WebOmsOrderAfterSaleUpdateDTO params) {
        this.iOmsOrderAfterSaleService.updateDataByWeb(params);
        return true;
    }

}
