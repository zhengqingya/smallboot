package com.zhengqing.mall.api.web;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.model.dto.OmsLogisticDTO;
import com.zhengqing.mall.model.vo.OmsLogisticVO;
import com.zhengqing.mall.service.IOmsLogisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> 商城-通用 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/common")
@Api(tags = {"web-通用"})
public class WebMallCommonController {

    private final IOmsLogisticService omsLogisticService;

    @GetMapping("getLogisticInfo")
    @ApiOperation("查询物流")
    public OmsLogisticVO getLogisticInfo(@Validated @ModelAttribute OmsLogisticDTO params) {
        return this.omsLogisticService.detail(params);
    }

    @PostMapping("updateLogisticForDb")
    @ApiOperation("更新数据库中的物流信息")
    public void updateLogisticForDb() {
        this.omsLogisticService.updateDb();
    }

}
