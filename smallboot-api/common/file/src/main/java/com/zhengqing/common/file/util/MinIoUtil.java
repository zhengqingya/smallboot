package com.zhengqing.common.file.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.zhengqing.common.file.config.MinIoProperties;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * MinIO工具类
 * </p>
 *
 * @author zhengqing
 * @description Java Client API参考文档：https://min.io/docs/minio/linux/developers/java/API.html
 * @date 2020/8/16 20:16
 */
@Slf4j
@Component
public class MinIoUtil {

    private static MinIoProperties minIoProperties;

    private static MinioClient minioClient;
    private static FileStorageUtil fileStorageUtil;

    @Autowired
    public MinIoUtil(MinIoProperties minIoProperties, FileStorageUtil fileStorageUtil) {
        MinIoUtil.minIoProperties = minIoProperties;
        MinIoUtil.fileStorageUtil = fileStorageUtil;
    }

    /**
     * 初始化minio配置
     */
    @PostConstruct
    public void init() {
        if (!fileStorageUtil.isMinio()) {
            return;
        }
        try {
            minioClient = MinioClient.builder()
                    .endpoint(minIoProperties.getUrl())
                    .credentials(minIoProperties.getAccessKey(), minIoProperties.getSecretKey())
                    .build();
            createBucket(minIoProperties.getBucketName());
        } catch (Exception e) {
            log.error("初始化minio配置异常：", e);
        }
    }

    // **************************** ↓↓↓↓↓↓ 桶操作 ↓↓↓↓↓↓ ****************************

    /**
     * 判断桶是否存在
     *
     * @param bucketName 桶名
     * @return true:存在 false:不存在
     * @date 2020/8/16 20:53
     */
    @SneakyThrows(Exception.class)
    public static boolean bucketExists(String bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建桶
     *
     * @param bucketName 桶名
     * @return void
     * @date 2020/8/16 20:53
     */
    @SneakyThrows(Exception.class)
    public static void createBucket(String bucketName) {
        boolean isExist = bucketExists(bucketName);
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 获取全部桶
     *
     * @return 桶信息
     * @date 2020/8/16 23:28
     */
    @SneakyThrows(Exception.class)
    public static List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }

    // **************************** ↓↓↓↓↓↓ 文件操作 ↓↓↓↓↓↓ ****************************

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 文件url地址
     * @date 2020/8/16 23:40
     */
    @SneakyThrows(Exception.class)
    public static String upload(MultipartFile file) {
        final InputStream inputStream = file.getInputStream();
        final String bucketName = minIoProperties.getBucketName();
        final String fileName = DateUtil.today() + "/" + IdUtil.fastUUID() + "@@" + file.getOriginalFilename();
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .stream(inputStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .build());
        inputStream.close();
        return getFileUrl(bucketName, fileName);
    }

    /**
     * 文件上传
     *
     * @param bucketName 桶名
     * @param file       文件
     * @return 文件url地址
     * @date 2020/8/16 23:40
     */
    @SneakyThrows(Exception.class)
    public static String upload(String bucketName, MultipartFile file) {
        final InputStream inputStream = file.getInputStream();
        final String fileName = DateUtil.today() + "/" + file.getOriginalFilename();
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .stream(inputStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .build());
        inputStream.close();
        return getFileUrl(bucketName, fileName);
    }

    /**
     * 文件上传
     *
     * @param bucketName 桶名
     * @param file       文件
     * @param objectName 存储的文件对象路径
     * @return 文件url地址
     * @date 2020/8/16 23:40
     */
    @SneakyThrows(Exception.class)
    public static String upload(String bucketName, MultipartFile file, String objectName) {
        final InputStream inputStream = file.getInputStream();
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(inputStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .build());
        inputStream.close();
        return getFileUrl(bucketName, objectName);
    }

    /**
     * 删除文件
     *
     * @param bucketName 桶名
     * @param fileName   文件名
     * @return void
     * @date 2020/8/16 20:53
     */
    @SneakyThrows(Exception.class)
    public static void deleteFile(String bucketName, String fileName) {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
    }

    /**
     * 下载文件
     *
     * @param bucketName 桶名
     * @param fileName   文件名
     * @param response
     * @return void
     * @date 2020/8/17 0:34
     */
    @SneakyThrows(Exception.class)
    public static void download(String bucketName, String fileName, HttpServletResponse response) {
        // 获取对象的元数据
        final StatObjectResponse stat = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(fileName).build());
        response.setContentType(stat.contentType());
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        InputStream is = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
        IOUtils.copy(is, response.getOutputStream());
        is.close();
    }

    /**
     * 获取minio文件的预览地址
     *
     * @param bucketName 桶名
     * @param fileName   文件名
     * @return 预览地址
     * @date 2020/8/16 22:07
     */
    @SneakyThrows(Exception.class)
    public static String getFileUrl(String bucketName, String fileName) {
        String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(fileName)
                .build());
        return url.substring(0, url.indexOf("?X-Amz-Algorithm="));
    }

    /**
     * 获取minio文件的预览地址
     *
     * @param bucketName 桶名
     * @param fileName   文件名
     * @param timeUnit   过期时间单位
     * @param time       过期时间
     * @return 预览地址
     * @date 2020/8/16 22:07
     */
    @SneakyThrows(Exception.class)
    public static String getFileUrl(String bucketName, String fileName, TimeUnit timeUnit, int time) {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(fileName)
                .expiry(time, timeUnit)
                .build());
    }

}
