package com.zhengqing.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.wf.entity.WfForm;
import com.zhengqing.wf.model.dto.WfFormDetailDTO;
import com.zhengqing.wf.model.dto.WfFormListDTO;
import com.zhengqing.wf.model.dto.WfFormPageDTO;
import com.zhengqing.wf.model.vo.WfFormDetailVO;
import com.zhengqing.wf.model.vo.WfFormListVO;
import com.zhengqing.wf.model.vo.WfFormPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 工作流-表单 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/12/04 17:06
 */
public interface WfFormMapper extends BaseMapper<WfForm> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/12/04 17:06
     */
    IPage<WfFormPageVO> selectDataPage(IPage<WfFormPageVO> page, @Param("filter") WfFormPageDTO filter);

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/12/04 17:06
     */
    List<WfFormListVO> selectDataList(@Param("filter") WfFormListDTO filter);

    /**
     * 详情
     *
     * @param filter 查询过滤参数
     * @return 详情
     * @author zhengqingya
     * @date 2023/12/04 17:06
     */
    WfFormDetailVO detail(@Param("filter") WfFormDetailDTO filter);

}
