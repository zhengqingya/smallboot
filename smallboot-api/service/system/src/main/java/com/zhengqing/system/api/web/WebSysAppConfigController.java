package com.zhengqing.system.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.db.util.TenantUtil;
import com.zhengqing.common.web.custom.noreturnhandle.NoReturnHandle;
import com.zhengqing.system.model.bo.SysAppConfigBO;
import com.zhengqing.system.model.dto.SysAppConfigDTO;
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
public class WebSysAppConfigController extends BaseController {

    private final ISysAppConfigService iSysAppConfigService;

    @GetMapping("page")
    @ApiOperation("列表分页")
    public ApiResult<IPage<SysAppConfigBO>> page(@ModelAttribute SysAppConfigDTO params) {
        return ApiResult.ok(TenantUtil.executeRemoveFlag(() -> this.iSysAppConfigService.page(params)));
    }

    @GetMapping("genLink")
    @ApiOperation("生成授权链接")
    public ApiResult<String> genLink() {
        return ApiResult.ok(TenantUtil.executeRemoveFlag(this.iSysAppConfigService::genLink));
    }

    @NoReturnHandle
    @GetMapping("qrcode")
    @ApiOperation("获取二维码")
    public ApiResult<byte[]> qrcode(@Validated @ModelAttribute SysAppQrcodeDTO params) {
        return ApiResult.ok(TenantUtil.executeRemoveFlag(() -> this.iSysAppConfigService.qrcode(params)));
    }

    @NoRepeatSubmit
    @PostMapping("operationBatch")
    @ApiOperation("批量操作(小程序提审、发布)")
    public ApiResult<Boolean> operationBatch(@Validated @RequestBody SysAppOperationDTO params) {
        TenantUtil.executeRemoveFlag(() -> this.iSysAppConfigService.operationBatch(params));
        return ApiResult.ok(true);
    }

    @NoRepeatSubmit
    @PostMapping("syncStatus")
    @ApiOperation("同步小程序最新状态")
    public ApiResult<Boolean> syncStatus() {
        TenantUtil.executeRemoveFlag(this.iSysAppConfigService::syncStatus);
        return ApiResult.ok(true);
    }

}
