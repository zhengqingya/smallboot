package com.zhengqing.mall.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.model.dto.OmsOrderCancelDTO;
import com.zhengqing.mall.model.dto.WebOmsOrderPageDTO;
import com.zhengqing.mall.model.dto.WebOmsOrderSendSpuDTO;
import com.zhengqing.mall.model.vo.MallTabConditionListVO;
import com.zhengqing.mall.model.vo.WebOmsOrderDetailVO;
import com.zhengqing.mall.model.vo.WebOmsOrderPageVO;
import com.zhengqing.mall.service.IOmsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * <p> 商城-订单信息 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:40
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/order")
@Api(tags = {"web-订单"})
public class WebOmsOrderController {

    private final IOmsOrderService iOmsOrderService;

//    @GetMapping("set")
//    @ApiOperation("订单设置-查询")
//    public WebOmsOrderSetVO getOrderSet() {
//        return this.iOmsOrderService.getOrderSet();
//    }
//
//    @PutMapping("set")
//    @ApiOperation("订单设置-更新")
//    public Boolean updateOrderSet(@Validated @RequestBody WebOmsOrderSetDTO params) {
//        this.iOmsOrderService.updateOrderSet(params);
//        return true;
//    }

    @GetMapping("getTabCondition")
    @ApiOperation("获取tab条件")
    public List<MallTabConditionListVO> getTabCondition(@ModelAttribute WebOmsOrderPageDTO params) {
        params.setTabValue(null);
        return this.iOmsOrderService.getTabCondition(params);
    }

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<WebOmsOrderPageVO> page(@Validated @ModelAttribute WebOmsOrderPageDTO params) {
        return this.iOmsOrderService.page(params);
    }

    @GetMapping("{orderNo}")
    @ApiOperation("详情")
    public WebOmsOrderDetailVO detail(@PathVariable String orderNo) {
        return this.iOmsOrderService.detail(orderNo);
    }

    @PutMapping("cancel")
    @ApiOperation("待支付-取消订单")
    public Boolean cancel(@Validated @RequestBody OmsOrderCancelDTO params) {
        this.iOmsOrderService.cancelOrderForBusiness(params);
        return true;
    }

    @PostMapping("sendSpu")
    @ApiOperation("订单发货")
    public Boolean sendSpu(@Validated @RequestBody WebOmsOrderSendSpuDTO params) {
        this.iOmsOrderService.sendSpu(params);
        return true;
    }

    @PostMapping("importBatchSendSpu")
    @ApiOperation("导入批量发货")
    public void importBatchSendSpu(MultipartFile file) {
        this.iOmsOrderService.importBatchSendSpu(file);
    }

    @GetMapping("export")
    @ApiOperation("导出(最多导出10000条数据)")
    public void export(HttpServletResponse response, @ModelAttribute WebOmsOrderPageDTO params) {
        this.iOmsOrderService.export(response, params);
    }

}
