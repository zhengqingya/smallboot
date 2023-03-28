package com.zhengqing.mall.controller.web;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.service.WebOmsOrderAfterSaleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * <p> 测试 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/20 17:38
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/test")
@Api(tags = {"test-api"})
public class WebMallTestController {

    @Resource
    private WebOmsOrderAfterSaleService webOmsOrderAfterSaleService;

//    @PostMapping("refundSuccessCallback")
//    @ApiOperation("退款成功回调")
//    public String refundSuccessCallback(@RequestBody PayOrderNotifyBO payOrderNotifyBO) {
//        this.webOmsOrderAfterSaleService.refundSuccessCallback(payOrderNotifyBO);
//        return "OK";
//    }

}
