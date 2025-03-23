package com.zhengqing.common.file.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.net.URLEncoder;

/**
 * <p> 七牛云上传文件工具类 </p>
 *
 * @author zhengqingya
 * @description API见 https://developer.qiniu.com/kodo/1239/java
 * @date 2021/1/4 14:21
 */
@Slf4j
@Component
public class QiniuFileUtil implements InitializingBean {

    @Resource
    private UploadManager uploadManager;

    @Resource
    private BucketManager bucketManager;

    @Resource
    private Auth auth;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.domain}")
    private String domain;

    @Value("${qiniu.expireInSeconds}")
    private long expireInSeconds;

    /**
     * 定义七牛云上传的相关策略
     */
    private StringMap putPolicy;

    /**
     * 上传文件
     *
     * @param file     文件
     * @param fileName 文件名（可自定义uri）
     * @return 成功则返回地址url
     */
    @SneakyThrows(Exception.class)
    public String upload(MultipartFile file, String fileName) {
        Response response = this.uploadManager.put(file.getInputStream(), fileName, this.getUploadToken(), null, null);
        // 解析上传成功的结果
        DefaultPutRet putRet = JSONUtil.toBean(response.bodyString(), DefaultPutRet.class);
        return "/" + putRet.key;
    }

    /**
     * 上传文件
     *
     * @param file     文件
     * @param fileName 文件名（可自定义uri）
     * @return 成功则返回地址url
     */
    @SneakyThrows(Exception.class)
    public String upload(File file, String fileName) {
        Response response = this.uploadManager.put(file, fileName, this.getUploadToken());
        // 解析上传成功的结果
        DefaultPutRet putRet = JSONUtil.toBean(response.bodyString(), DefaultPutRet.class);
        return "/" + putRet.key;
    }

    /**
     * 下载文件 (参考私有空间下载：https://developer.qiniu.com/kodo/sdk/1239/java#private-get)
     *
     * @param fileName 文件名
     * @return 返回下载地址url
     */
    @SneakyThrows(Exception.class)
    public String downloadFile(String fileName) {
        // 公开空间下载地址方式 "http://" + domain + "/" + fileName;

        // 私有空间下载地址方式
        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        String publicUrl = StrUtil.format("{}/{}", this.domain, encodedFileName);
        return this.auth.privateDownloadUrl(publicUrl, this.expireInSeconds);
    }

    /**
     * 获取上传凭证
     */
    private String getUploadToken() {
        return this.auth.uploadToken(this.bucket, null, 3600, this.putPolicy);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy = new StringMap();
        // 自定义上传回复的凭证
        this.putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
    }

}
