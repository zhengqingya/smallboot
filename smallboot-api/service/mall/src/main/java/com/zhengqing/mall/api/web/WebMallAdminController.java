package com.zhengqing.mall.api.web;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.service.MallCommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * <p> 商城-admin 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/admin")
@Api(tags = {"web-管理员专用api"})
public class WebMallAdminController {

    @Resource
    private MallCommonService mallCommonService;

    @GetMapping("initData")
    @ApiOperation("初始化普通商品所需数据(系统模块中缓存数据) -- 仅第一次使用")
    public void initData() {
        this.mallCommonService.initData();
    }

}
