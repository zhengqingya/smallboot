package com.zhengqing.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.cms.entity.CmsJob;
import com.zhengqing.cms.model.dto.CmsJobBaseDTO;
import com.zhengqing.cms.model.vo.CmsJobBaseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 内容管理-招聘岗位 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:08
 */
public interface CmsJobMapper extends BaseMapper<CmsJob> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 15:08
     */
    IPage<CmsJobBaseVO> selectDataList(IPage<CmsJobBaseVO> page, @Param("filter") CmsJobBaseDTO filter);

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 15:08
     */
    List<CmsJobBaseVO> selectDataList(@Param("filter") CmsJobBaseDTO filter);

    /**
     * 查询部门id关联的发布职位数
     *
     * @param deptId 部门id
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 15:08
     */
    long selectJobNumByDeptId(@Param("deptId") Integer deptId);

}
