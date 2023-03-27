package com.zhengqing.mall.controller;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.mall.service.WebPmsAttrKeyService;
import com.zhengqing.mall.web.model.dto.WebPmsAttrListDTO;
import com.zhengqing.mall.web.model.dto.WebPmsAttrSaveDTO;
import com.zhengqing.mall.web.model.vo.WebPmsAttrKeyListVO;
import com.zhengqing.mall.web.model.vo.WebPmsAttrVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p> 商城-属性key 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/20 17:38
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_MALL + "/attr/key")
@Api(tags = {"web-属性key"})
public class WebPmsAttrController {

    @Resource
    private WebPmsAttrKeyService webPmsAttrKeyService;

    @GetMapping("list")
    @ApiOperation("列表")
    public List<WebPmsAttrKeyListVO> list(@Validated @ModelAttribute WebPmsAttrListDTO params) {
        return this.webPmsAttrKeyService.list(params);
    }

    @GetMapping("listByIdList")
    @ApiOperation("根据ids查询列表数据")
    public List<WebPmsAttrVO> listByIdList(@RequestParam List<String> idList) {
        return this.webPmsAttrKeyService.listByIdList(idList);
    }

    @PostMapping("")
    @ApiOperation("新增")
    public String add(@Validated @RequestBody WebPmsAttrSaveDTO params) {
        params.setId(null);
        return this.webPmsAttrKeyService.addOrUpdateData(params);
    }

    @PutMapping("")
    @ApiOperation("更新")
    public String update(@Validated(UpdateGroup.class) @RequestBody WebPmsAttrSaveDTO params) {
        return this.webPmsAttrKeyService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam String id) {
        this.webPmsAttrKeyService.deleteData(id);
    }

}
