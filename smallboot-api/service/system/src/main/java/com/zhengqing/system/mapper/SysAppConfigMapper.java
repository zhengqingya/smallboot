package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysAppConfig;
import com.zhengqing.system.model.bo.SysAppConfigBO;
import com.zhengqing.system.model.dto.SysAppConfigDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 系统管理-小程序配置 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/17 21:09
 */
public interface SysAppConfigMapper extends BaseMapper<SysAppConfig> {

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    List<SysAppConfigBO> selectDataList(@Param("filter") SysAppConfigDTO filter);

}
