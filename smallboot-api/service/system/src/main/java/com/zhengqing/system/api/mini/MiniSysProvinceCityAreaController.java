package com.zhengqing.system.api.mini;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.system.model.dto.SysProvinceCityAreaTreeDTO;
import com.zhengqing.system.model.vo.SysProvinceCityAreaTreeVO;
import com.zhengqing.system.service.ISysProvinceCityAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p> 系统管理-省市区 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/14 11:38
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_SYSTEM + "/pca")
@Api(tags = {"mini-系统管理-省市区"})
public class MiniSysProvinceCityAreaController extends BaseController {

    private final ISysProvinceCityAreaService sysProvinceCityAreaService;

    @GetMapping("tree")
    @ApiOperation("树")
    public List<SysProvinceCityAreaTreeVO> tree(@Validated @ModelAttribute SysProvinceCityAreaTreeDTO params) {
        return this.sysProvinceCityAreaService.tree(params);
    }

}
