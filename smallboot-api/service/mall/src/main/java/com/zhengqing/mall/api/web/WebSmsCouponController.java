package com.zhengqing.mall.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.mall.model.dto.SmsCouponPageDTO;
import com.zhengqing.mall.model.dto.SmsCouponSaveDTO;
import com.zhengqing.mall.model.vo.SmsCouponPageVO;
import com.zhengqing.mall.service.ISmsCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> 商城-优惠券 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/03/26 15:37
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/sms/coupon")
@Api(tags = {"商城-优惠券"})
public class WebSmsCouponController extends BaseController {

    private final ISmsCouponService smsCouponService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<SmsCouponPageVO> page(@Validated @ModelAttribute SmsCouponPageDTO params) {
        return this.smsCouponService.page(params);
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody SmsCouponSaveDTO params) {
        params.setId(null);
        this.smsCouponService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody SmsCouponSaveDTO params) {
        this.smsCouponService.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Long id) {
        this.smsCouponService.deleteData(id);
    }

}
