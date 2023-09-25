package com.zhengqing.system.api.web;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.validator.common.CreateGroup;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.entity.SysDictType;
import com.zhengqing.system.model.dto.SysDictTypeSaveDTO;
import com.zhengqing.system.service.ISysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 基础模块 - 数据字典类型接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:50
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/dict/type")
@Api(tags = "web-基础模块-数据字典类型接口")
public class WebSysDictTypeController extends BaseController {

    private final ISysDictTypeService dictTypeService;

    @GetMapping("list")
    @ApiOperation("列表")
    public List<SysDictType> list() {
        return this.dictTypeService.list().stream().sorted(Comparator.comparing(SysDictType::getSort)).collect(Collectors.toList());
    }

    @PostMapping("")
    @ApiOperation("新增")
    public Integer add(@Validated(CreateGroup.class) @RequestBody SysDictTypeSaveDTO params) {
        params.setId(null);
        return this.dictTypeService.addOrUpdateData(params);
    }

    @PutMapping("")
    @ApiOperation("更新")
    public Integer update(@Validated(UpdateGroup.class) @RequestBody SysDictTypeSaveDTO params) {
        return this.dictTypeService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.dictTypeService.deleteType(id);
    }

}
