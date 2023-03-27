package com.zhengqing.wxmp.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.wxmp.model.dto.WxMpTemplateMsgPageDTO;
import com.zhengqing.wxmp.model.dto.WxMpTemplateMsgSendDTO;
import com.zhengqing.wxmp.model.vo.WxMpTemplateMsgPageVO;
import com.zhengqing.wxmp.service.IWxMpTemplateMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> 微信公众号-模板消息 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_WXMP + "/templateMsg/{appId}")
@Api(tags = {"微信公众号-模板消息"})
public class WxMpTemplateMsgController extends BaseController {

    private final IWxMpTemplateMsgService wxTemplateMsgService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<WxMpTemplateMsgPageVO> page(@PathVariable String appId, @Validated @ModelAttribute WxMpTemplateMsgPageDTO params) {
        params.setAppId(appId);
        return this.wxTemplateMsgService.page(params);
    }

    @PostMapping("sync")
    @ApiOperation("同步模板数据")
    public void sync(@PathVariable String appId) {
        this.wxTemplateMsgService.sync(appId);
    }

    @PostMapping("sendMsg")
    @ApiOperation("发送消息")
    public void sendMsg(@Validated @RequestBody WxMpTemplateMsgSendDTO params) {
        this.wxTemplateMsgService.sendMsg(params);
    }

}
