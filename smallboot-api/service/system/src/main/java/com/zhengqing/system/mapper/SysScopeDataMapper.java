package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.system.entity.SysScopeData;
import com.zhengqing.system.model.dto.SysScopeDataBaseDTO;
import com.zhengqing.system.model.vo.SysScopeDataBaseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 系统管理-数据权限 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/18 14:00
 */
public interface SysScopeDataMapper extends BaseMapper<SysScopeData> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/18 14:00
     */
    IPage<SysScopeDataBaseVO> selectDataList(IPage<SysScopeDataBaseVO> page, @Param("filter") SysScopeDataBaseDTO filter);

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/18 14:00
     */
    List<SysScopeDataBaseVO> selectDataList(@Param("filter") SysScopeDataBaseDTO filter);

}
