package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.system.entity.SysPost;
import com.zhengqing.system.model.dto.SysPostListDTO;
import com.zhengqing.system.model.dto.SysPostPageDTO;
import com.zhengqing.system.model.vo.SysPostListVO;
import com.zhengqing.system.model.vo.SysPostPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 系统管理-岗位 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 17:49
 */
public interface SysPostMapper extends BaseMapper<SysPost> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/09 17:49
     */
    IPage<SysPostPageVO> selectDataPage(IPage<SysPostPageVO> page, @Param("filter") SysPostPageDTO filter);

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/09 17:49
     */
    List<SysPostListVO> selectDataList(@Param("filter") SysPostListDTO filter);

}
