package com.zhengqing.common.sdk.douyin.service.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * <p> 抖音 模板列表 响应参数 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/7/28 15:28
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DyServiceTplVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码 {@link com.zhengqing.common.sdk.douyin.service.enums.DyServiceResultCodeEnum}
     */
    private Long errno;

    /**
     * 错误信息
     */
    private String message;

    private List<Tpl> template_list;

    @Data
    public static class Tpl {
        /**
         * 模板 id
         */
        private Integer template_id;
        /**
         * 版本号
         */
        private String user_version;
        /**
         * 描述
         */
        private String user_desc;
        /**
         * 模板创建时间戳
         */
        private Long create_time;
    }

}
