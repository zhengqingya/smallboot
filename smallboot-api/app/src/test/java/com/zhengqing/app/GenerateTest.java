package com.zhengqing.app;

import com.zhengqing.common.base.config.CommonProperty;
import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.web.util.YmlUtil;
import com.zhengqing.system.config.SystemProperty;
import com.zhengqing.system.model.bo.SysCgConfigBO;
import com.zhengqing.system.service.ICodeGenerateService;
import com.zhengqing.system.service.impl.CodeGenerateServiceImpl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <p> 代码生成器测试 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/7/29 11:23
 */
@Slf4j
public class GenerateTest {

    @Test
    public void testGenerate() throws Exception {
        // 读取基本生成配置信息（mysql连接配置等...）
        final String GENERATE_CONFIG_PATH = AppConstant.PROJECT_ROOT_DIRECTORY + "/src/main/resources/generate-config.yml";
        GenerateConfig generateConfig = YmlUtil.getYml(GENERATE_CONFIG_PATH, GenerateConfig.class);

        // 拿到配置
        ICodeGenerateService iCodeGenerateService = new CodeGenerateServiceImpl(SystemProperty.builder()
                .mysql(
                        CommonProperty.Mysql.builder()
                                .master(CommonProperty.MysqlConn.builder()
                                        .ip(generateConfig.getIp())
                                        .port(generateConfig.getPort())
                                        .dbName(generateConfig.getDbName())
                                        .username(generateConfig.getUsername())
                                        .password(generateConfig.getPassword())
                                        .build())
                                .build()
                )
                .build());
        SysCgConfigBO config = iCodeGenerateService.getConfig();

        // 生成表名
        config.setTableName(generateConfig.getTableName());
        // 包名
        config.setParentPackageName(generateConfig.getParentPackageName());
        // 模块名
        config.setModuleName(generateConfig.getModuleName());

        // 生成代码
        iCodeGenerateService.generateTplData(config);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("代码生成配置参数")
    public static class GenerateConfig {
        @ApiModelProperty("mysql-ip地址")
        private String ip;

        @ApiModelProperty("端口")
        private String port;

        @ApiModelProperty("用户名")
        private String username;

        @ApiModelProperty("密码")
        private String password;

        @ApiModelProperty("库名")
        private String dbName;

        @ApiModelProperty("表名")
        private String tableName;

        @ApiModelProperty("父包名")
        private String parentPackageName;

        @ApiModelProperty("模块名")
        private String moduleName;
    }

}
