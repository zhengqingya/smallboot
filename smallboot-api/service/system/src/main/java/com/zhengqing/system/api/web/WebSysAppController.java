package com.zhengqing.system.api.web;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.web.custom.noreturnhandle.NoReturnHandle;
import com.zhengqing.system.model.dto.SysAppOperationDTO;
import com.zhengqing.system.model.dto.SysAppQrcodeDTO;
import com.zhengqing.system.service.ISysAppConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> 系统管理-小程序管理 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/13 11:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/app")
@Api(tags = {"web-系统管理-小程序管理"})
public class WebSysAppController extends BaseController {

    private final ISysAppConfigService iSysAppConfigService;

    @GetMapping("genLink")
    @ApiOperation("生成授权链接")
    public String genLink() {
        return this.iSysAppConfigService.genLink();
    }

    @NoReturnHandle
    @GetMapping("qrcode")
    @ApiOperation("获取二维码")
    public byte[] qrcode(@Validated @ModelAttribute SysAppQrcodeDTO params) {
        return this.iSysAppConfigService.qrcode(params);
    }

    @NoRepeatSubmit
    @PostMapping("appOperationBatch")
    @ApiOperation("批量操作(小程序提审、发布)")
    public Boolean appOperationBatch(@Validated @RequestBody SysAppOperationDTO params) {
        this.iSysAppConfigService.appOperationBatch(params);
        return true;
    }

}
