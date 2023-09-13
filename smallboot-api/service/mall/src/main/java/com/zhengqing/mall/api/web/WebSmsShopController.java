package com.zhengqing.mall.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.mall.model.dto.SmsShopDetailDTO;
import com.zhengqing.mall.model.dto.SmsShopPageDTO;
import com.zhengqing.mall.model.dto.SmsShopSaveDTO;
import com.zhengqing.mall.model.vo.SmsShopDetailVO;
import com.zhengqing.mall.model.vo.SmsShopPageVO;
import com.zhengqing.mall.service.ISmsShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> 商城-店铺信息 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/13 09:51
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/sms/shop")
@Api(tags = {"商城-店铺信息"})
public class WebSmsShopController extends BaseController {

    private final ISmsShopService smsShopService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<SmsShopPageVO> page(@Validated @ModelAttribute SmsShopPageDTO params) {
        return this.smsShopService.page(params);
    }

    @GetMapping("detail")
    @ApiOperation("详情")
    public SmsShopDetailVO detail(@Validated @ModelAttribute SmsShopDetailDTO params) {
        return this.smsShopService.detail(params);
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody SmsShopSaveDTO params) {
        params.setShopId(null);
        this.smsShopService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody SmsShopSaveDTO params) {
        this.smsShopService.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer shopId) {
        this.smsShopService.deleteData(shopId);
    }

}
