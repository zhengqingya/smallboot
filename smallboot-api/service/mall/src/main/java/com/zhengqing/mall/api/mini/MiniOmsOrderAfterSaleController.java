package com.zhengqing.mall.api.mini;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.entity.OmsOrderAfterSale;
import com.zhengqing.mall.model.dto.MiniOmsOrderAfterSaleUpdateDTO;
import com.zhengqing.mall.model.dto.MiniOmsOrderRepealAfterSaleDTO;
import com.zhengqing.mall.model.dto.OmsOrderAfterSaleDeleteDTO;
import com.zhengqing.mall.model.dto.OmsOrderAfterSalePageDTO;
import com.zhengqing.mall.model.enums.OmsOrderAfterSaleStatusEnum;
import com.zhengqing.mall.model.vo.OmsOrderAfterSaleBaseVO;
import com.zhengqing.mall.service.IOmsOrderAfterSaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> mini-订单-售后 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/14 10:27
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_MALL + "/order/afterSale")
@Api(tags = {"mini-订单-售后"})
public class MiniOmsOrderAfterSaleController {

    private final IOmsOrderAfterSaleService iOmsOrderAfterSaleService;

    @GetMapping("page")
    @ApiOperation("列表分页")
    public IPage<OmsOrderAfterSaleBaseVO> page(@Validated @ModelAttribute OmsOrderAfterSalePageDTO params) {
        return this.iOmsOrderAfterSaleService.page(params);
    }

    @GetMapping("")
    @ApiOperation("详情")
    public OmsOrderAfterSaleBaseVO detail(@RequestParam String afterSaleNo) {
        return this.iOmsOrderAfterSaleService.detail(afterSaleNo);
    }

    @PutMapping("")
    @ApiOperation("更新售后信息（退款/退货退款/换货）")
    public Boolean updateData(@Validated @RequestBody MiniOmsOrderAfterSaleUpdateDTO params) {
        this.iOmsOrderAfterSaleService.updateData(OmsOrderAfterSale.builder()
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
        this.iOmsOrderAfterSaleService.repeal(params);
        return true;
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("批量删除")
    public Boolean deleteBatch(@Validated @ModelAttribute OmsOrderAfterSaleDeleteDTO params) {
        this.iOmsOrderAfterSaleService.deleteBatch(params);
        return true;
    }

}
