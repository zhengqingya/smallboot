package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.system.entity.SysVersion;
import com.zhengqing.system.model.dto.SysVersionBaseDTO;
import com.zhengqing.system.model.vo.SysVersionBaseVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 系统管理-版本记录 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/15 14:58
 */
public interface SysVersionMapper extends BaseMapper<SysVersion> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/15 14:58
     */
    IPage<SysVersionBaseVO> selectDataPage(IPage<SysVersionBaseVO> page, @Param("filter") SysVersionBaseDTO filter);

}
