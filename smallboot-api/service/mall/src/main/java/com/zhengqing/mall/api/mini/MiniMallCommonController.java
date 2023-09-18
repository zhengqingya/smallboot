package com.zhengqing.mall.api.mini;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.model.dto.OmsLogisticDTO;
import com.zhengqing.mall.model.vo.MallFileVO;
import com.zhengqing.mall.model.vo.OmsLogisticVO;
import com.zhengqing.mall.service.IMallCommonService;
import com.zhengqing.mall.service.IOmsLogisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * <p> 商城-通用 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_MALL + "/common")
@Api(tags = {"mini-通用"})
public class MiniMallCommonController {

    private final IMallCommonService iMallCommonService;
    private final IOmsLogisticService iOmsLogisticService;

    @ApiOperation("上传文件")
    @PostMapping("uploadFile")
    public MallFileVO uploadFile(@RequestParam("file") MultipartFile file) {
        return this.iMallCommonService.uploadFile(file);
    }

    @GetMapping("getLogisticInfo")
    @ApiOperation("查询物流")
    public OmsLogisticVO getLogisticInfo(@Validated @ModelAttribute OmsLogisticDTO params) {
        return this.iOmsLogisticService.detail(params);
    }

}
