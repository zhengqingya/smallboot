package com.zhengqing.system.api.mini;

import com.zhengqing.common.auth.custom.open.ApiOpen;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.system.model.vo.SysMerchantDetailVO;
import com.zhengqing.system.service.ISysMerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p> 系统管理-商户管理 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/13 11:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_SYSTEM + "/merchant")
@Api(tags = {"mini-系统管理-商户管理"})
public class MiniSysMerchantController extends BaseController {

    private final ISysMerchantService iSysMerchantService;

    @ApiOpen
    @GetMapping("detail")
    @ApiOperation("详情")
    public SysMerchantDetailVO detailByBusiness(@RequestParam Integer id) {
        return this.iSysMerchantService.detailByBusiness(id);
    }

}
