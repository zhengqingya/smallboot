package com.zhengqing.system.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * <p> ext.json代开发配置 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/28 4:54 上午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysExtJsonBO {

    private Boolean extEnable;

    private String extAppid;
    private Boolean directCommit;
    private HashMap<String, Object> extPages;
    private Ext ext;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Ext {
        private String tenantId;
        private String deptId;
        private String appId;
    }

}
