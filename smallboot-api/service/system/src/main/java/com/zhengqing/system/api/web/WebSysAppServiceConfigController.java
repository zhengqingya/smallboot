package com.zhengqing.system.api.web;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.dto.SysAppServiceConfigSaveDTO;
import com.zhengqing.system.model.vo.SysAppServiceConfigDetailVO;
import com.zhengqing.system.service.ISysAppServiceConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> 系统管理-小程序服务商平台配置 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/20 16:03
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/app/service/config")
@Api(tags = {"web-系统管理-小程序服务商平台配置"})
public class WebSysAppServiceConfigController extends BaseController {

    private final ISysAppServiceConfigService iSysAppServiceConfigService;

    @GetMapping("detail")
    @ApiOperation("详情")
    public SysAppServiceConfigDetailVO detail() {
        return this.iSysAppServiceConfigService.detail();
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody SysAppServiceConfigSaveDTO params) {
        this.iSysAppServiceConfigService.addOrUpdateData(params);
    }

}
