package com.zhengqing.mall.controller;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.common.model.dto.OmsLogisticDTO;
import com.zhengqing.mall.common.model.vo.MallFileVO;
import com.zhengqing.mall.common.model.vo.OmsLogisticVO;
import com.zhengqing.mall.service.MallCommonService;
import com.zhengqing.mall.service.OmsLogisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


/**
 * <p> 商城-通用 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_MALL + "/common")
@Api(tags = {"mini-通用"})
public class MiniMallCommonController {

    @Resource
    private MallCommonService mallCommonService;

    @Resource
    private OmsLogisticService omsLogisticService;

    @ApiOperation("上传文件")
    @PostMapping("uploadFile")
    public MallFileVO uploadFile(@RequestParam("file") MultipartFile file) {
        return this.mallCommonService.uploadFile(file);
    }

    @GetMapping("getLogisticInfo")
    @ApiOperation("查询物流")
    public OmsLogisticVO getLogisticInfo(@Validated @ModelAttribute OmsLogisticDTO params) {
        return this.omsLogisticService.detail(params);
    }

}
