package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.system.entity.SysTenant;
import com.zhengqing.system.model.dto.SysTenantPageDTO;
import com.zhengqing.system.model.vo.SysTenantPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 系统管理-租户信息 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 15:40
 */
public interface SysTenantMapper extends BaseMapper<SysTenant> {

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

}
