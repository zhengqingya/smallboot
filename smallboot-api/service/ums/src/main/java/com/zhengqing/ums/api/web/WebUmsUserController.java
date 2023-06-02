package com.zhengqing.ums.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.ums.model.dto.WebUmsUserPageDTO;
import com.zhengqing.ums.model.vo.WebUmsUserPageVO;
import com.zhengqing.ums.service.IUmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户管理接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:43
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_UMS + "/user")
@Api(tags = "web-用户")
public class WebUmsUserController extends BaseController {

    @Resource
    private IUmsUserService sysUserService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<WebUmsUserPageVO> page(@Validated @ModelAttribute WebUmsUserPageDTO params) {
        return this.sysUserService.page(params);
    }

}
