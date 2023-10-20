package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysAppServiceConfig;
import com.zhengqing.system.model.dto.SysAppServiceConfigSaveDTO;
import com.zhengqing.system.model.vo.SysAppServiceConfigDetailVO;

/**
 * <p>  系统管理-小程序服务商平台配置 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/20 16:03
 */
public interface ISysAppServiceConfigService extends IService<SysAppServiceConfig> {

    /**
     * 详情
     *
     * @return 详情
     * @author zhengqingya
     * @date 2023/10/20 16:03
     */
    SysAppServiceConfigDetailVO detail();

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/20 16:03
     */
    void addOrUpdateData(SysAppServiceConfigSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/20 16:03
     */
    void deleteData(Integer id);

}
