package com.zhengqing.wxmp.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.wxmp.model.dto.WxUserDetailDTO;
import com.zhengqing.wxmp.model.dto.WxUserPageDTO;
import com.zhengqing.wxmp.model.dto.WxUserSaveDTO;
import com.zhengqing.wxmp.model.vo.WxUserDetailVO;
import com.zhengqing.wxmp.model.vo.WxUserPageVO;
import com.zhengqing.wxmp.service.IWxUserService;
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
public class WxUserController extends BaseController {

    private final IWxUserService wxUserService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<WxUserPageVO> page(@Validated @ModelAttribute WxUserPageDTO params) {
        return this.wxUserService.page(params);
    }

    @GetMapping("detail")
    @ApiOperation("详情")
    public WxUserDetailVO detail(@Validated @ModelAttribute WxUserDetailDTO params) {
        return this.wxUserService.detail(params);
    }

    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody WxUserSaveDTO params) {
        params.setId(null);
        this.wxUserService.addOrUpdateData(params);
    }

    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody WxUserSaveDTO params) {
        this.wxUserService.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.wxUserService.deleteData(id);
    }

}
