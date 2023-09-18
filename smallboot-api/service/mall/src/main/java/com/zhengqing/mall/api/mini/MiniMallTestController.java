package com.zhengqing.mall.api.mini;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.service.IOmsOrderService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p> 商城-测试 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_MALL + "/test")
@Api(tags = {"admin-测试api"})
public class MiniMallTestController {

    private final IOmsOrderService iOmsOrderService;

//    @PostMapping("paySuccessCallback")
//    @ApiOperation("支付成功回调业务处理")
//    public String paySuccessCallback(@Validated @RequestBody PayOrderNotifyBO payOrderNotifyBO) {
//        TenantIdContext.setTenantId(payOrderNotifyBO.getTenantId());
//        this.iOmsOrderService.paySuccessCallback(payOrderNotifyBO);
//        return "OK";
//    }

}
