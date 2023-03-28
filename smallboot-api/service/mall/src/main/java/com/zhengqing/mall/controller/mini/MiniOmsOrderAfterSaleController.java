package com.zhengqing.mall.controller.mini;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.entity.OmsOrderAfterSale;
import com.zhengqing.mall.model.dto.MiniOmsOrderAfterSalePageDTO;
import com.zhengqing.mall.model.dto.MiniOmsOrderAfterSaleUpdateDTO;
import com.zhengqing.mall.model.dto.MiniOmsOrderRepealAfterSaleDTO;
import com.zhengqing.mall.model.dto.OmsOrderAfterSaleDeleteDTO;
import com.zhengqing.mall.model.enums.OmsOrderAfterSaleStatusEnum;
import com.zhengqing.mall.model.vo.MiniOmsOrderAfterSaleDetailVO;
import com.zhengqing.mall.model.vo.MiniOmsOrderAfterSalePageVO;
import com.zhengqing.mall.service.MiniOmsOrderAfterSaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p> mini-订单-售后 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/14 10:27
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_MALL + "/order/afterSale")
@Api(tags = {"mini-订单-售后"})
public class MiniOmsOrderAfterSaleController {

    @Resource
    private MiniOmsOrderAfterSaleService miniOmsOrderAfterSaleService;

    @GetMapping("page")
    @ApiOperation("列表分页")
    public IPage<MiniOmsOrderAfterSalePageVO> page(@Validated @ModelAttribute MiniOmsOrderAfterSalePageDTO params) {
        return this.miniOmsOrderAfterSaleService.page(params);
    }

    @GetMapping("")
    @ApiOperation("详情")
    public MiniOmsOrderAfterSaleDetailVO detail(@RequestParam String afterSaleNo) {
        return this.miniOmsOrderAfterSaleService.detailByMini(afterSaleNo);
    }

    @PutMapping("")
    @ApiOperation("更新售后信息（退款/退货退款/换货）")
    public Boolean updateData(@Validated @RequestBody MiniOmsOrderAfterSaleUpdateDTO params) {
        this.miniOmsOrderAfterSaleService.updateData(OmsOrderAfterSale.builder()
                .afterSaleNo(params.getAfterSaleNo())
                // 买家填写退货单号后，申请退款处理
                .afterStatus(OmsOrderAfterSaleStatusEnum.APPLY_REFUND.getStatus())
                .returnLogisticsCode(params.getReturnLogisticsCode())
                .returnLogisticsNo(params.getReturnLogisticsNo())
                .returnAddress(params.getReturnAddress())
                .build());
        return true;
    }

    @PostMapping("repeal")
    @ApiOperation("撤销")
    public Boolean repeal(@Validated @RequestBody MiniOmsOrderRepealAfterSaleDTO params) {
        this.miniOmsOrderAfterSaleService.repeal(params);
        return true;
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    public Boolean deleteBatch(@Validated @ModelAttribute OmsOrderAfterSaleDeleteDTO params) {
        this.miniOmsOrderAfterSaleService.deleteBatch(params);
        return true;
    }

}
