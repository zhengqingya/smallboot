package com.zhengqing.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.cms.entity.CmsJobCategory;
import com.zhengqing.cms.model.dto.CmsJobCategoryListDTO;
import com.zhengqing.cms.model.dto.CmsJobCategoryPageDTO;
import com.zhengqing.cms.model.vo.CmsJobCategoryListVO;
import com.zhengqing.cms.model.vo.CmsJobCategoryPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 内容管理-招聘岗位分类 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:09
 */
public interface CmsJobCategoryMapper extends BaseMapper<CmsJobCategory> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 15:09
     */
    IPage<CmsJobCategoryPageVO> selectDataPage(IPage<CmsJobCategoryPageVO> page, @Param("filter") CmsJobCategoryPageDTO filter);

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 15:09
     */
    List<CmsJobCategoryListVO> selectDataList(@Param("filter") CmsJobCategoryListDTO filter);

}
