package com.zhengqing.common.file.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 文件存储类型枚举类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/28 22:56
 */
@Getter
@AllArgsConstructor
public enum FileStorageTypeEnum {

    /**
     * local(本地nginx作为文件访问)
     */
    LOCAL("local"),

    /**
     * minio
     */
    MINIO("minio");

    /**
     * 文件存储类型
     */
    private final String type;


    private static final List<FileStorageTypeEnum> LIST = Lists.newArrayList();

    static {
        LIST.addAll(Arrays.asList(FileStorageTypeEnum.values()));
    }

    /**
     * 根据指定的规则类型查找相应枚举类
     *
     * @param type 类型
     * @return 类型枚举信息
     * @author zhengqingya
     * @date 2022/1/10 12:52
     */
    @SneakyThrows(Exception.class)
    public static FileStorageTypeEnum getEnum(String type) {
        for (FileStorageTypeEnum itemEnum : LIST) {
            if (itemEnum.getType().equals(type)) {
                return itemEnum;
            }
        }
        throw new Exception("未找到指定文件存储类型数据！");
    }


}
