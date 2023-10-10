package com.zhengqing.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.cms.entity.CmsJobTag;
import com.zhengqing.cms.model.dto.CmsJobTagListDTO;
import com.zhengqing.cms.model.dto.CmsJobTagPageDTO;
import com.zhengqing.cms.model.vo.CmsJobTagListVO;
import com.zhengqing.cms.model.vo.CmsJobTagPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 内容管理-招聘岗位标签 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 15:10
 */
public interface CmsJobTagMapper extends BaseMapper<CmsJobTag> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 15:10
     */
    IPage<CmsJobTagPageVO> selectDataPage(IPage page, @Param("filter") CmsJobTagPageDTO filter);

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 15:10
     */
    List<CmsJobTagListVO> selectDataList(@Param("filter") CmsJobTagListDTO filter);

}
