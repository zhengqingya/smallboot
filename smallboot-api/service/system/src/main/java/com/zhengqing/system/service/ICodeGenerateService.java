package com.zhengqing.system.service;

import com.zhengqing.system.model.bo.SysCgConfigBO;

/**
 * <p> 代码生成器 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 11:33
 */
public interface ICodeGenerateService {

    /**
     * 获取项目模板配置
     *
     * @return 配置信息
     * @author zhengqingya
     * @date 2023/12/5 9:28
     */
    SysCgConfigBO getConfig();

    /**
     * 保持模板配置
     *
     * @param config 提交参数
     * @return void
     * @author zhengqingya
     * @date 2023/12/5 9:28
     */
    void saveConfig(SysCgConfigBO config);

    /**
     * 生成代码
     *
     * @param config 提交参数
     * @return void
     * @author zhengqingya
     * @date 2023/12/5 9:28
     */
    void generateTplData(SysCgConfigBO config);

}
