package com.zhengqing.system.api;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.file.util.FileStorageUtil;
import com.zhengqing.system.model.vo.SysFileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
@Api(tags = {"web-系统管理 - 文件上传"})
public class SysFileController {

    private final FileStorageUtil fileStorageUtil;

    @PostMapping("upload")
    @ApiOperation("上传文件")
    @SneakyThrows(Exception.class)
    public SysFileVO upload(@RequestPart @RequestParam MultipartFile file) {
        return SysFileVO.builder()
                .name(file.getOriginalFilename())
                .url(this.fileStorageUtil.upload(file))
                .type(file.getContentType())
                .build();
    }

}
