package com.zhengqing.wxmp.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.wxmp.model.dto.WxMpUserPageDTO;
import com.zhengqing.wxmp.model.vo.WxMpUserPageVO;
import com.zhengqing.wxmp.service.IWxMpUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> 微信公众号-用户 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:28
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_WXMP + "/user")
@Api(tags = {"微信公众号-用户"})
public class WxMpUserController extends BaseController {

    private final IWxMpUserService wxUserService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<WxMpUserPageVO> page(@Validated @ModelAttribute WxMpUserPageDTO params) {
        return this.wxUserService.page(params);
    }

    @PostMapping("sync")
    @ApiOperation("同步公众号用户数据")
    public void sync(@RequestHeader String appId) {
        this.wxUserService.sync(appId);
    }

}
