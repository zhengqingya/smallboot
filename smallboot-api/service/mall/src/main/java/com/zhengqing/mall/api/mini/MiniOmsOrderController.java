package com.zhengqing.mall.api.mini;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.mall.model.dto.*;
import com.zhengqing.mall.model.vo.MallTabConditionListVO;
import com.zhengqing.mall.model.vo.MiniOmsSpuBuyVO;
import com.zhengqing.mall.model.vo.MiniPmsOrderReAfterSaleStatusVO;
import com.zhengqing.mall.model.vo.OmsOrderBaseVO;
import com.zhengqing.mall.service.IOmsOrderService;
import com.zhengqing.pay.model.vo.PayOrderCreateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p> mini-订单 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/14 10:27
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_MALL + "/order")
@Api(tags = {"mini-订单"})
public class MiniOmsOrderController {

    private final IOmsOrderService iOmsOrderService;

    @GetMapping("getTabCondition")
    @ApiOperation("获取tab条件")
    public List<MallTabConditionListVO> getTabCondition(@ModelAttribute OmsOrderPageDTO params) {
        params.setTabValue(null);
        return this.iOmsOrderService.getTabCondition(params);
    }

    @GetMapping("page")
    @ApiOperation("列表分页")
    public IPage<OmsOrderBaseVO> page(@Validated @ModelAttribute OmsOrderPageDTO params) {
        return this.iOmsOrderService.page(params);
    }

    @GetMapping("{orderNo}")
    @ApiOperation("详情")
    public OmsOrderBaseVO detail(@PathVariable String orderNo) {
        return this.iOmsOrderService.detail(orderNo);
    }

    @PostMapping("create")
    @ApiOperation("购买-创建订单")
    public MiniOmsSpuBuyVO createOrder(@Validated @RequestBody MiniOmsSpuBuyDTO params) {
        return this.iOmsOrderService.createOrder(params);
    }

    @PutMapping("cancel")
    @ApiOperation("待支付-取消订单")
    public Boolean cancel(@Validated @RequestBody OmsOrderCancelDTO params) {
        this.iOmsOrderService.cancelOrderForBusiness(params);
        return true;
    }

    @PostMapping("pay")
    @ApiOperation("待支付-支付订单")
    public PayOrderCreateVO pay(@Validated @RequestBody MiniOmsOrderPayDTO params) {
        return this.iOmsOrderService.payOrder(params);
    }

    @PostMapping("payTest")
    @ApiOperation("待支付-支付订单(仅测试环境使用，订单直接变成已支付状态流程)")
    public Boolean payTest(@Validated @RequestBody MiniOmsOrderPayDTO params) {
        this.iOmsOrderService.payOrderTest(params);
        return true;
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    public Boolean deleteBatch(@Validated @ModelAttribute OmsOrderDeleteDTO params) {
        throw new MyException("暂不支持删除订单操作！");
//        this.miniOmsOrderService.deleteBatch(params);
//        return true;
    }

    @PutMapping("updateReceiverAddress")
    @ApiOperation("修改收货人地址")
    public Boolean updateReceiverAddress(@Validated @RequestBody MiniOmsOrderUpdateReceiverAddressDTO params) {
        this.iOmsOrderService.updateReceiverAddress(params);
        return true;
    }

    @GetMapping("getAfterSaleStatus")
    @ApiOperation("售后状态(判断是否可申请售后)")
    public MiniPmsOrderReAfterSaleStatusVO getAfterSaleStatus(@RequestParam String orderNo) {
        return this.iOmsOrderService.getAfterSaleStatus(orderNo);
    }

    @PostMapping("applyAfterSale")
    @ApiOperation("申请售后（退款/退货退款/换货）")
    public Boolean applyAfterSale(@Validated @RequestBody MiniOmsOrderApplyAfterSaleDTO params) {
        this.iOmsOrderService.applyAfterSale(params);
        return true;
    }

    @PostMapping("confirmReceipt")
    @ApiOperation("确认收货")
    public Boolean confirmReceipt(@Validated @RequestBody MiniOmsOrderConfirmReceiptDTO params) {
        this.iOmsOrderService.confirmReceipt(params);
        return true;
    }

}
