package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.system.entity.SysTenantPackage;
import com.zhengqing.system.model.dto.SysTenantPackageListDTO;
import com.zhengqing.system.model.dto.SysTenantPackagePageDTO;
import com.zhengqing.system.model.vo.SysTenantPackageListVO;
import com.zhengqing.system.model.vo.SysTenantPackagePageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 系统管理-租户套餐 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 10:34
 */
public interface SysTenantPackageMapper extends BaseMapper<SysTenantPackage> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/08 10:34
     */
    IPage<SysTenantPackagePageVO> selectDataPage(IPage<SysTenantPackagePageVO> page, @Param("filter") SysTenantPackagePageDTO filter);

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/08 10:34
     */
    List<SysTenantPackageListVO> selectDataList(@Param("filter") SysTenantPackageListDTO filter);

}
