package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysDept;
import com.zhengqing.system.model.dto.SysDeptTreeDTO;
import com.zhengqing.system.model.vo.SysDeptTreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 系统管理-部门 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/09 18:10
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/09 18:10
     */
    List<SysDeptTreeVO> selectDataList(@Param("filter") SysDeptTreeDTO filter);

    /**
     * 获取小程序可以提审、发布的部门数据
     *
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/13 11:17
     */
    List<SysDept> selectAppIdList();

}
