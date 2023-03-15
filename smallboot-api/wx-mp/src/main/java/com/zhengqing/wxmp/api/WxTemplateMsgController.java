package com.zhengqing.wxmp.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.wxmp.model.dto.WxTemplateMsgDetailDTO;
import com.zhengqing.wxmp.model.dto.WxTemplateMsgPageDTO;
import com.zhengqing.wxmp.model.vo.WxTemplateMsgDetailVO;
import com.zhengqing.wxmp.model.vo.WxTemplateMsgPageVO;
import com.zhengqing.wxmp.service.IWxTemplateMsgService;
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
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_WXMP + "/templateMsg")
@Api(tags = {"微信公众号-模板消息"})
public class WxTemplateMsgController extends BaseController {

    private final IWxTemplateMsgService wxTemplateMsgService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<WxTemplateMsgPageVO> page(@Validated @ModelAttribute WxTemplateMsgPageDTO params) {
        return this.wxTemplateMsgService.page(params);
    }

    @GetMapping("detail")
    @ApiOperation("详情")
    public WxTemplateMsgDetailVO detail(@Validated @ModelAttribute WxTemplateMsgDetailDTO params) {
        return this.wxTemplateMsgService.detail(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.wxTemplateMsgService.deleteData(id);
    }

}
