package com.zhengqing.common.sdk.douyin.service.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <p> 抖音 小程序版本列表 响应参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/28 15:28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DyServiceVersionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码 {@link com.zhengqing.common.sdk.douyin.service.enums.DyServiceResultCodeEnum}
     */
    private Long errno;

    /**
     * 错误信息
     */
    private String message;

    private Data data;

    @lombok.Data
    public static class Data {
        /**
         * 测试版本
         */
        private LatestVersion latest;
        /**
         * 审核版本
         */
        private AuditVersion audit;
        /**
         * 线上版本
         */
        private CurrentVersion current;
    }

    @lombok.Data
    public static class Version {
        // ------------- 版本信息通用返回值 -------------
        /**
         * 服务类目中文形式
         */
        private Object categories;
        /**
         * 该版本的创建时间
         */
        private Long ctime;
        /**
         * 小程序拥有者的头像
         */
        private String developer_avatar;
        /**
         * 小程序拥有者的 ID
         */
        private String developer_id;
        /**
         * 小程序拥有者的昵称
         */
        private String developer_name;

        /**
         * 该版本的更新信息
         */
        private String summary;
        /**
         * 该版本的版本号
         */
        private String version;
    }

    @lombok.Data
    public static class LatestVersion extends Version {
        // ------------- 测试版本返回值 -------------

        /**
         * 是否已提审，0:否，1:是
         */
        private Integer has_audit;

        /**
         * 上传代码包时的三张截图路径，英文分号分隔
         */
        private String screenshot;
    }

    @lombok.Data
    public static class AuditVersion extends Version {
        // ------------- 审核版本返回值 -------------
        /**
         * 申请的宿主端中，有哪些宿主端通过审核
         */
        private Object approvedApps;

        /**
         * 审核不通过时的附件信息
         */
        private Object attachInfo;
        /**
         * 是否已发布，0:否，1:是
         */
        private Integer has_publish;
        /**
         * 当前审核版本是否是被下架时的那个版本，如果是则不能发布
         */
        private boolean is_illegal_version;
        /**
         * 审核不通过原因
         */
        private Integer reason;
        /**
         * 格式化后的审核不通过原因
         */
        private Object reasonDetail;
        /**
         * 当前审核状态，0:审核中，1:通过，2:不通过，3：撤回审核
         */
        private Integer status;
    }

    @lombok.Data
    public static class CurrentVersion extends Version {
        // ------------- 线上版本返回值 -------------
        /**
         * 申请的宿主端中，有哪些宿主端通过审核
         */
        private Object approvedApps;
        /**
         * 审核不通过时的附件信息
         */
        private Object attachInfo;
        /**
         * 是否下架，0:否，1:是
         */
        private Integer has_down;
        /**
         * 申请的宿主端中，有哪些宿主端没有通过审核
         */
        private Object notApprovedApps;
        /**
         * 审核不通过原因
         */
        private String reason;
        /**
         * 格式化的审核不通过原因
         */
        private Object reasonDetail;
        /**
         * 回退信息
         */
        private Object rollback;
        /**
         * 开发者平台账号 id
         */
        private Integer uid;
    }

}
