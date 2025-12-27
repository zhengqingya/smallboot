package com.zhengqing.wxmp.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.wxmp.model.dto.WxMpMsgAutoReplyPageDTO;
import com.zhengqing.wxmp.model.dto.WxMpMsgAutoReplySaveDTO;
import com.zhengqing.wxmp.model.vo.WxMpMsgAutoReplyPageVO;
import com.zhengqing.wxmp.service.IWxMpMsgAutoReplyService;
import com.zhengqing.common.base.model.vo.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> 微信公众号-消息自动回复 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_WXMP + "/msgAutoReply")
@Api(tags = {"微信公众号-消息自动回复"})
public class WxMpMsgAutoReplyController extends BaseController {

    private final IWxMpMsgAutoReplyService wxMsgAutoReplyService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public ApiResult<IPage<WxMpMsgAutoReplyPageVO>> page(@Validated @ModelAttribute WxMpMsgAutoReplyPageDTO params) {
        return ApiResult.ok(this.wxMsgAutoReplyService.page(params));
    }

    @PostMapping("add")
    @ApiOperation("新增")
    public ApiResult<Void> add(@Validated @RequestBody WxMpMsgAutoReplySaveDTO params) {
        params.setId(null);
        this.wxMsgAutoReplyService.addOrUpdateData(params);
        return ApiResult.ok();
    }

    @PutMapping("update")
    @ApiOperation("更新")
    public ApiResult<Void> update(@Validated(UpdateGroup.class) @RequestBody WxMpMsgAutoReplySaveDTO params) {
        this.wxMsgAutoReplyService.addOrUpdateData(params);
        return ApiResult.ok();
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public ApiResult<Void> delete(@RequestParam Integer id) {
        this.wxMsgAutoReplyService.deleteData(id);
        return ApiResult.ok();
    }

}
