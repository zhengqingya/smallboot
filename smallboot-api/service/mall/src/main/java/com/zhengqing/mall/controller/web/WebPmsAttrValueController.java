package com.zhengqing.mall.controller.web;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.mall.model.dto.WebPmsAttrValueListDTO;
import com.zhengqing.mall.model.dto.WebPmsAttrValueSaveDTO;
import com.zhengqing.mall.model.vo.WebPmsAttrValueListVO;
import com.zhengqing.mall.service.WebPmsAttrValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p> 商城-属性value 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/22 15:16
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/attr/value")
@Api(tags = {"web-属性value"})
public class WebPmsAttrValueController {

    @Resource
    private WebPmsAttrValueService webPmsAttrValueService;

    @GetMapping("list")
    @ApiOperation("列表")
    public List<WebPmsAttrValueListVO> list(@Validated @ModelAttribute WebPmsAttrValueListDTO params) {
        return this.webPmsAttrValueService.list(params);
    }

    @PostMapping("")
    @ApiOperation("新增")
    public String add(@Validated @RequestBody WebPmsAttrValueSaveDTO params) {
        params.setId(null);
        return this.webPmsAttrValueService.addOrUpdateData(params);
    }

    @PutMapping("")
    @ApiOperation("更新")
    public String update(@Validated(UpdateGroup.class) @RequestBody WebPmsAttrValueSaveDTO params) {
        return this.webPmsAttrValueService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam String id) {
        this.webPmsAttrValueService.deleteData(id);
    }

}
