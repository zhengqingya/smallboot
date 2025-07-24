package com.zhengqing.system.api.mini;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.model.vo.ApiResult;
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
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_SYSTEM + "/file")
@Api(tags = {"mini-系统管理 - 文件上传"})
public class MiniSysFileController {

    private final FileStorageUtil fileStorageUtil;

    @PostMapping("upload")
    @ApiOperation("上传文件")
    @SneakyThrows(Exception.class)
    public ApiResult<SysFileVO> upload(@RequestPart @RequestParam MultipartFile file) {
        return ApiResult.ok(SysFileVO.builder()
                .name(file.getOriginalFilename())
                .url(this.fileStorageUtil.upload(file))
                .type(file.getContentType())
                .build());
    }

}
