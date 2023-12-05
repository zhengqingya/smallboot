package com.zhengqing.system.api.web;


import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.system.model.vo.SysCgProjectPackageTreeVO;
import com.zhengqing.system.service.ICodeGenerateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p> 代码生成器 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/5 2:36 下午
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/codeGenerate")
@Api(tags = "web-代码生成器")
@RequiredArgsConstructor
public class WebCodeGenerateController {

    private final ICodeGenerateService iCodeGenerateService;

    @ApiOperation("项目树包")
    @GetMapping("projectPackageTree")
    public List<SysCgProjectPackageTreeVO> projectPackageTree() {
        return this.iCodeGenerateService.projectPackageTree();
    }

    @ApiOperation("生成代码")
    @PostMapping("generateTplData")
    public void generateTplData() {
        this.iCodeGenerateService.generateTplData();
    }

}
