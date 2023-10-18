package com.zhengqing.system.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.system.model.dto.SysFilePageDTO;
import com.zhengqing.system.model.vo.SysFilePageVO;
import com.zhengqing.system.model.vo.SysFileVO;
import com.zhengqing.system.service.ISysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * <p> 系统管理 - 文件上传 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 18:52
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/file")
@Api(tags = {"web-系统管理-文件上传"})
public class WebSysFileController {

    private final ISysFileService iSysFileService;

    @PostMapping("upload")
    @ApiOperation("上传文件")
    @SneakyThrows(Exception.class)
    public SysFileVO upload(@RequestPart @RequestParam MultipartFile file) {
        return this.iSysFileService.upload(file);
    }

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<SysFilePageVO> page(@Validated @ModelAttribute SysFilePageDTO params) {
        return this.iSysFileService.page(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Long id) {
        this.iSysFileService.removeById(id);
    }

}
