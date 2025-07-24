package com.zhengqing.system.api.mini;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.model.vo.ApiResult;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.system.model.vo.SysConfigVO;
import com.zhengqing.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * <p> 系统管理-系统配置 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/09/06 22:57
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_SYSTEM + "/config")
@Api(tags = {"mini-系统管理-系统配置接口"})
public class MiniSysConfigController extends BaseController {

    private final ISysConfigService iSysConfigService;

    @GetMapping("listByKey")
    @ApiOperation("根据属性key查询")
    public ApiResult<Map<String, SysConfigVO>> listByKey(@RequestParam List<String> keyList) {
        return ApiResult.ok(this.iSysConfigService.mapByKey(keyList));
    }

}
