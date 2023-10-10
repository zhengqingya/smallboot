package com.zhengqing.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.cms.entity.CmsJobApply;
import com.zhengqing.cms.model.dto.CmsJobApplyPageDTO;
import com.zhengqing.cms.model.vo.CmsJobApplyPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 内容管理-职位申请 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/10 18:11
 */
public interface CmsJobApplyMapper extends BaseMapper<CmsJobApply> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/10 18:11
     */
    IPage<CmsJobApplyPageVO> selectDataPage(IPage<CmsJobApplyPageVO> page, @Param("filter") CmsJobApplyPageDTO filter);

}
