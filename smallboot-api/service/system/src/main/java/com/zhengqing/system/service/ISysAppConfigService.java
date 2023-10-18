package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysAppConfig;
import com.zhengqing.system.model.bo.SysAppConfigBO;
import com.zhengqing.system.model.dto.SysAppConfigDTO;
import com.zhengqing.system.model.dto.SysAppOperationDTO;
import com.zhengqing.system.model.dto.SysAppQrcodeDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>  系统管理-小程序配置 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/17 21:09
 */
public interface ISysAppConfigService extends IService<SysAppConfig> {

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return 主键ID
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    Integer addOrUpdateData(SysAppConfigBO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    List<SysAppConfigBO> list(SysAppConfigDTO params);

    /**
     * 拿到所有可用的小程序配置
     *
     * @return 小程序配置
     * @author zhengqingya
     * @date 2023/10/18 10:23
     */
    List<SysAppConfigBO> getAllUsableAppConfig();

    /**
     * 查询小程序配置
     *
     * @param idList 主键ids
     * @return 主键ID -> 小程序配置
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    Map<Integer, SysAppConfigBO> mapByIdList(List<Integer> idList);

    /**
     * 查询小程序配置
     *
     * @param appIdList appId
     * @return appId -> 小程序配置
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    Map<String, SysAppConfigBO> mapByAppIdList(List<String> appIdList);

    /**
     * 生成授权链接
     *
     * @return 授权链接
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    String genLink();

    /**
     * 二维码
     *
     * @param params 提交参数
     * @return 二维码
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    byte[] qrcode(SysAppQrcodeDTO params);

    /**
     * 批量操作(小程序提审、发布)
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    void operationBatch(SysAppOperationDTO params);

    /**
     * 同步小程序最新状态
     *
     * @return void
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    void syncStatus();

}
