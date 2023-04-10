package com.zhengqing.mall.controller.mini;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.mall.model.dto.*;
import com.zhengqing.mall.model.vo.*;
import com.zhengqing.mall.service.MiniOmsOrderService;
import com.zhengqing.pay.model.vo.PayOrderCreateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p> mini-订单 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/14 10:27
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_MALL + "/order")
@Api(tags = {"mini-订单"})
public class MiniOmsOrderController {

    @Resource
    private MiniOmsOrderService miniOmsOrderService;

    @GetMapping("getTabCondition")
    @ApiOperation("获取tab条件")
    public List<MallTabConditionListVO> getTabCondition(@ModelAttribute MiniOmsOrderPageDTO params) {
        Assert.notNull(params.getUserId(), "用户id不能为空！");
        params.setTabValue(null);
        return this.miniOmsOrderService.getTabCondition(params);
    }

    @GetMapping("page")
    @ApiOperation("列表分页")
    public IPage<MiniOmsOrderPageVO> page(@Validated @ModelAttribute MiniOmsOrderPageDTO params) {
        return this.miniOmsOrderService.page(params);
    }

    @GetMapping("{orderNo}")
    @ApiOperation("详情")
    public MiniOmsOrderDetailVO detail(@PathVariable String orderNo) {
        return this.miniOmsOrderService.detailByMini(orderNo);
    }

    @PostMapping("create")
    @ApiOperation("购买-创建订单")
    public MiniOmsSpuBuyVO createOrder(@Validated @RequestBody MiniOmsSpuBuyDTO params) {
        return this.miniOmsOrderService.createOrder(params);
    }

    @PutMapping("cancel")
    @ApiOperation("待支付-取消订单")
    public Boolean cancel(@Validated @RequestBody OmsOrderCancelDTO params) {
        this.miniOmsOrderService.cancelOrderForBusiness(params);
        return true;
    }

    @PostMapping("pay")
    @ApiOperation("待支付-支付订单")
    public PayOrderCreateVO pay(@Validated @RequestBody MiniOmsOrderPayDTO params) {
        return this.miniOmsOrderService.payOrder(params);
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
        this.miniOmsOrderService.updateReceiverAddress(params);
        return true;
    }

    @GetMapping("getAfterSaleStatus")
    @ApiOperation("售后状态(判断是否可申请售后)")
    public MiniPmsOrderReAfterSaleStatusVO getAfterSaleStatus(@RequestParam String orderNo) {
        return this.miniOmsOrderService.getAfterSaleStatus(orderNo);
    }

    @PostMapping("applyAfterSale")
    @ApiOperation("申请售后（退款/退货退款/换货）")
    public Boolean applyAfterSale(@Validated @RequestBody MiniOmsOrderApplyAfterSaleDTO params) {
        this.miniOmsOrderService.applyAfterSale(params);
        return true;
    }

    @PostMapping("confirmReceipt")
    @ApiOperation("确认收货")
    public Boolean confirmReceipt(@Validated @RequestBody MiniOmsOrderConfirmReceiptDTO params) {
        this.miniOmsOrderService.confirmReceipt(params);
        return true;
    }

}
