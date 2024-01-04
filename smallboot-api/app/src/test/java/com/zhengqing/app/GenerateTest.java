package com.zhengqing.app;

import com.zhengqing.common.base.config.CommonProperty;
import com.zhengqing.system.config.SystemProperty;
import com.zhengqing.system.model.bo.SysCgConfigBO;
import com.zhengqing.system.service.ICodeGenerateService;
import com.zhengqing.system.service.impl.CodeGenerateServiceImpl;
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
        // 拿到配置
        ICodeGenerateService iCodeGenerateService = new CodeGenerateServiceImpl(SystemProperty.builder()
                .mysql(
                        CommonProperty.Mysql.builder()
                                .master(CommonProperty.MysqlConn.builder()
                                        .ip("127.0.0.1")
                                        .port("3306")
                                        .dbName("demo")
                                        .username("root")
                                        .password("root")
                                        .build())
                                .build()
                )
                .build());
        SysCgConfigBO config = iCodeGenerateService.getConfig();

        // 生成表
        config.setTableName("t_user");

        // 生成代码
        iCodeGenerateService.generateTplData(config);
    }


}
