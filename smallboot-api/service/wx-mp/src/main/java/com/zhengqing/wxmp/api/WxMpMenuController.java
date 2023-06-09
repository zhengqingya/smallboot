package com.zhengqing.wxmp.api;

import com.zhengqing.common.base.constant.ServiceConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.web.bind.annotation.*;

/**
 * <p> 微信公众号-自定义菜单 </p>
 *
 * @author zhengqingya
 * @description 参考 https://github.com/binarywang/wx-java-mp-demo
 * @date 2023/3/16 16:51
 */
@AllArgsConstructor
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_WXMP + "/menu")
@Api(tags = {"微信公众号-自定义菜单"})
public class WxMpMenuController {
    private final WxMpService wxService;

    @GetMapping("/detail")
    @ApiOperation("详情")
    @SneakyThrows(Exception.class)
    public WxMpMenu detail(@RequestHeader String appId) {
        return this.wxService.switchoverTo(appId).getMenuService().menuGet();
    }

    @PutMapping("/update")
    @ApiOperation("更新")
    @SneakyThrows(Exception.class)
    public void update(@RequestHeader String appId, @RequestBody WxMenu menu) {
        this.wxService.switchoverTo(appId).getMenuService().menuCreate(menu);
    }

}
