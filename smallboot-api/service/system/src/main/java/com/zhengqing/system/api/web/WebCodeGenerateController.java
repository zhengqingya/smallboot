package com.zhengqing.system.api.web;


import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.core.custom.log.ApiLog;
import com.zhengqing.system.model.bo.SysCgConfigBO;
import com.zhengqing.system.service.ICodeGenerateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p> 代码生成器 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/5 2:36 下午
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/codeGenerate")
@Api(tags = "web-代码生成器")
@RequiredArgsConstructor
public class WebCodeGenerateController {

    private final ICodeGenerateService iCodeGenerateService;

    @ApiOperation("模板配置")
    @GetMapping("getConfig")
    public ApiResult<SysCgConfigBO> getConfig() {
        return ApiResult.ok(this.iCodeGenerateService.getConfig());
    }

    @ApiLog(isSave = false)
    @ApiOperation("保存模块配置")
    @PostMapping("saveConfig")
    public ApiResult saveConfig(@RequestBody SysCgConfigBO config) {
        this.iCodeGenerateService.saveConfig(config);
        return ApiResult.ok();
    }

    @ApiLog(isSave = false)
    @ApiOperation("生成代码")
    @PostMapping("generateTplData")
    public ApiResult generateTplData(@Validated @RequestBody SysCgConfigBO params) {
        this.iCodeGenerateService.generateTplData(params);
        return ApiResult.ok();
    }

}
