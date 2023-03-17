package com.zhengqing.wxmp.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.wxmp.model.dto.WxMpAccountPageDTO;
import com.zhengqing.wxmp.model.dto.WxMpAccountSaveDTO;
import com.zhengqing.wxmp.model.vo.WxMpAccountPageVO;
import com.zhengqing.wxmp.service.IWxMpAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> 微信公众号-账号 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_WXMP + "/account")
@Api(tags = {"微信公众号-账号"})
public class WxMpAccountController extends BaseController {

    private final IWxMpAccountService wxAccountService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<WxMpAccountPageVO> page(@Validated @ModelAttribute WxMpAccountPageDTO params) {
        return this.wxAccountService.page(params);
    }

    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody WxMpAccountSaveDTO params) {
        params.setId(null);
        this.wxAccountService.addOrUpdateData(params);
    }

    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody WxMpAccountSaveDTO params) {
        this.wxAccountService.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.wxAccountService.deleteData(id);
    }

}
