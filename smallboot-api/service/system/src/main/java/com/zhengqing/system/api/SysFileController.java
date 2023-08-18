package com.zhengqing.system.api;

import cn.hutool.core.io.FileUtil;
import com.zhengqing.common.base.constant.ProjectConstant;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.common.file.util.MinIoUtil;
import com.zhengqing.system.model.vo.SysFileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
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
@Api(tags = {"系统管理 - 文件上传"})
public class SysFileController {

    @Value("${smallboot.nginx-file-url}")
    private String nginxFileUrl;

    @PostMapping("localUpload")
    @ApiOperation("上传文件-本地")
    @SneakyThrows(Exception.class)
    public SysFileVO localUpload(@RequestPart @RequestParam MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        // 写入文件到本地
        String absolutePath = FileUtil.writeBytes(file.getBytes(), ProjectConstant.LOCAL_FILE_TMP + IdGeneratorUtil.nextId() + "-" + originalFilename).getAbsolutePath();
        String url = absolutePath.substring(absolutePath.indexOf("tmp") + 3);
        return SysFileVO.builder()
                .name(originalFilename)
                .url(this.nginxFileUrl + url.replaceAll("\\\\", ProjectConstant.SYSTEM_SEPARATOR))
                .type(file.getContentType())
                .build();
    }

    @PostMapping("upload")
    @ApiOperation("上传文件")
    @SneakyThrows(Exception.class)
    public SysFileVO upload(@RequestPart @RequestParam MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return SysFileVO.builder()
                .name(originalFilename)
                .url(MinIoUtil.upload(file))
                .type(file.getContentType())
                .build();
    }

}
