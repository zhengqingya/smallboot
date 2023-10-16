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

    public boolean isMinio() {
        return FileStorageTypeEnum.MINIO.getType().equals(this.baseProperty.getFileStorageType());
    }

    @SneakyThrows(Exception.class)
    public String upload(MultipartFile file) {
        // 文件名 eg: test.png
        String originalFilename = file.getOriginalFilename();
        String url = null;
        switch (FileStorageTypeEnum.getEnum(this.baseProperty.getFileStorageType())) {
            case LOCAL:
                // 写入文件到本地
                String localFileDir = this.baseProperty.getLocalFileDir();
                if (!localFileDir.startsWith("/")) {
                    localFileDir = BaseConstant.PROJECT_ROOT_DIRECTORY + "/" + localFileDir;
                }
                String absolutePath = FileUtil.writeBytes(file.getBytes(), localFileDir + MyDateUtil.nowStr(MyDateUtil.DATE_FORMAT) + "/" + IdUtil.getSnowflakeNextId() + "-" + originalFilename).getAbsolutePath();
                absolutePath = absolutePath.replaceAll("\\\\", "/");
                url = absolutePath.substring(absolutePath.indexOf(localFileDir) + localFileDir.length());
                break;
            case MINIO:
                url = MinIoUtil.upload(file);
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
