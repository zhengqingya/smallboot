package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.db.mapper.MyBaseMapper;
import com.zhengqing.system.entity.SysTenant;
import com.zhengqing.system.model.dto.SysTenantListDTO;
import com.zhengqing.system.model.dto.SysTenantPageDTO;
import com.zhengqing.system.model.vo.SysTenantListVO;
import com.zhengqing.system.model.vo.SysTenantPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 系统管理-租户信息 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 15:40
 */
public interface SysTenantMapper extends MyBaseMapper<SysTenant> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/08 15:40
     */
    IPage<SysTenantPageVO> selectDataPage(IPage page, @Param("filter") SysTenantPageDTO filter);

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/08 15:40
     */
    List<SysTenantListVO> selectDataList(@Param("filter") SysTenantListDTO filter);

    /**
     * 拿到删除整个租户下所有数据的sql -- 逻辑删除
     *
     * @param tableSchema 库
     * @param tenantId    租户id
     * @return void
     * @author zhengqingya
     * @date 2023/10/08 15:40
     */
    List<String> selectLogicDelAllDataSql(@Param("tableSchema") String tableSchema, @Param("tenantId") Integer tenantId);

    /**
     * 拿到删除整个租户下所有数据的sql -- 物理删除
     *
     * @param tableSchema 库
     * @param tenantId    租户id
     * @return void
     * @author zhengqingya
     * @date 2023/10/08 15:40
     */
    List<String> selectPhysicsDelAllDataSql(@Param("tableSchema") String tableSchema, @Param("tenantId") Integer tenantId);

}
