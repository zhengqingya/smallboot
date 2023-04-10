package com.zhengqing.pay.controller;

import com.zhengqing.pay.model.dto.PayOrderRefundDTO;
import com.zhengqing.pay.service.IPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p> 微信支付-管理员专用 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wx/admin")
@Api(tags = {"微信支付-管理员专用"})
public class WxAdminController {

    private IPayService payService;

    @ApiOperation("退款")
    @PostMapping("/refund")
    public Boolean refund(@Validated @RequestBody PayOrderRefundDTO params) {
        return this.payService.refund(params);
    }

}
