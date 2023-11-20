package com.zhengqing.system.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.system.model.dto.SysLogPageDTO;
import com.zhengqing.system.model.vo.SysLogPageVO;
import com.zhengqing.system.service.ISysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p> 系统管理-操作日志 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/19 16:32
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/log")
@Api(tags = {"web-系统管理-操作日志"})
public class WebSysLogController extends BaseController {

    private final ISysLogService iSysLogService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<SysLogPageVO> page(@Validated @ModelAttribute SysLogPageDTO params) {
        return this.iSysLogService.page(params);
    }

    @DeleteMapping("deleteDataBeforeDay")
    @ApiOperation("清理n天前的日志")
    public void deleteDataBeforeDay(@RequestParam Integer day) {
        this.iSysLogService.deleteDataBeforeDay(day);
    }


}
