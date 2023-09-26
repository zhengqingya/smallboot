package com.zhengqing.mall.api.mini;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.mall.model.dto.SmsShopLatelyDTO;
import com.zhengqing.mall.model.dto.SmsShopPageDTO;
import com.zhengqing.mall.model.vo.SmsShopBaseVO;
import com.zhengqing.mall.service.ISmsShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p> 商城-店铺信息 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/13 09:51
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_MALL + "/sms/shop")
@Api(tags = {"mini-商城-店铺信息"})
public class MiniSmsShopController extends BaseController {

    private final ISmsShopService smsShopService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<SmsShopBaseVO> page(@Validated @ModelAttribute SmsShopPageDTO params) {
        return this.smsShopService.page(params);
    }

    @GetMapping("lately")
    @ApiOperation("最近门店")
    public SmsShopBaseVO lately(@Validated @ModelAttribute SmsShopLatelyDTO params) {
        return this.smsShopService.lately(params);
    }

}
