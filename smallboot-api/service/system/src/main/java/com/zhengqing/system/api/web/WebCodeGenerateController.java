package com.zhengqing.system.api.web;


import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.system.model.vo.SysCgProjectPackageVO;
import com.zhengqing.system.service.ICodeGenerateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 代码生成器 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/5 2:36 下午
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/code-generate")
@Api(tags = "web-代码生成器")
@RequiredArgsConstructor
public class WebCodeGenerateController {

    private final ICodeGenerateService iCodeGenerateService;

    @GetMapping("project-package")
    @ApiOperation("项目包")
    public SysCgProjectPackageVO projectPackage() {
        this.iCodeGenerateService.projectPackage();
        return null;
    }


}
