package com.zhengqing.mall.api.web;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.service.IOmsOrderAfterSaleService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p> 测试 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/20 17:38
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/test")
@Api(tags = {"test-api"})
public class WebMallTestController {

    private final IOmsOrderAfterSaleService iOmsOrderAfterSaleService;

//    @PostMapping("refundSuccessCallback")
//    @ApiOperation("退款成功回调")
//    public String refundSuccessCallback(@RequestBody PayOrderNotifyBO payOrderNotifyBO) {
//        this.iOmsOrderAfterSaleService.refundSuccessCallback(payOrderNotifyBO);
//        return "OK";
//    }

}
