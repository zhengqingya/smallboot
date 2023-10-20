package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysAppServiceConfig;
import com.zhengqing.system.model.vo.SysAppServiceConfigDetailVO;

/**
 * <p> 系统管理-小程序服务商平台配置 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/20 16:03
 */
public interface SysAppServiceConfigMapper extends BaseMapper<SysAppServiceConfig> {

    /**
     * 详情
     *
     * @return 详情
     * @author zhengqingya
     * @date 2023/10/20 16:03
     */
    SysAppServiceConfigDetailVO detail();

}
