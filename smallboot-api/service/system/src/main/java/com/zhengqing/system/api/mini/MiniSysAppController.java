package com.zhengqing.system.api.mini;

import com.zhengqing.common.auth.custom.open.ApiOpen;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.system.service.ISysTenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p> 系统管理-小程序配置 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/13 11:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_SYSTEM + "/app")
@Api(tags = {"mini-系统管理-小程序配置"})
public class MiniSysAppController extends BaseController {

    private final ISysTenantService iSysTenantService;

    @ApiOpen
    @GetMapping("config")
    @ApiOperation("小程序配置")
    public ApiResult<Object> config(@RequestParam String appId) {
        return ApiResult.ok(this.iSysTenantService.configForApp(TenantIdContext.getTenantId()));
    }

}
