package com.zhengqing.system.api;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 首页
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/19 13:49
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "")
@Api(tags = "首页 - 接口")
public class SysIndexController extends BaseController {

    @Value(value = "${spring.application.name}")
    private String applicationName;

    @GetMapping("/index")
    @ApiOperation(value = "首页")
    public String index() {
        return "您好，欢迎访问【" + this.applicationName + "】";
    }

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        // 模拟业务耗时处理流程
        Thread.sleep(2 * 1000L);
        return "hello";
    }

}
