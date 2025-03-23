package com.zhengqing.common.file.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.zhengqing.common.base.config.BaseProperty;
import com.zhengqing.common.base.constant.BaseConstant;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.file.enums.FileStorageTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p> 文件统一工具类 </p>
 *
 * @author zhengqing
 * @description
 * @date 2020/8/16 20:16
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileStorageUtil {

    private final BaseProperty baseProperty;
    private final QiniuFileUtil qiniuFileUtil;

    public boolean isMinio() {
        return FileStorageTypeEnum.MINIO.getType().equals(this.baseProperty.getFileStorageType());
    }

    @SneakyThrows(Exception.class)
    public String upload(MultipartFile file) {
        // 文件名 eg: test.png
        String originalFilename = file.getOriginalFilename();
        // 自定义文件路径 eg: file/2023-12-12/1734457162606034988.png
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filePath = BaseConstant.BASE_PREFIX + "/" + MyDateUtil.nowStr(MyDateUtil.DATE_FORMAT) + "/" + IdUtil.getSnowflakeNextId() + fileExtension;
        String url = null;
        switch (FileStorageTypeEnum.getEnum(this.baseProperty.getFileStorageType())) {
            case LOCAL:
                // 写入文件到本地
                String localFileDir = this.baseProperty.getLocalFileDir();
                if (!localFileDir.startsWith("/") && !localFileDir.contains(":")) {
                    localFileDir = BaseConstant.PROJECT_ROOT_DIRECTORY + "/" + localFileDir;
                }
                String absolutePath = FileUtil.writeBytes(file.getBytes(), localFileDir + filePath).getAbsolutePath();
                absolutePath = absolutePath.replaceAll("\\\\", "/");
                url = absolutePath.substring(absolutePath.indexOf(localFileDir) + localFileDir.length());
                break;
            case MINIO:
                url = MinIoUtil.upload(file);
                break;
            case QINIU:
                url = this.qiniuFileUtil.upload(file, filePath);
                break;
            default:
                break;
        }
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        return url;
    }

}
