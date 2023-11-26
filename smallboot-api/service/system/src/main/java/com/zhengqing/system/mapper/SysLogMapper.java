package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.system.entity.SysLog;
import com.zhengqing.system.model.dto.SysLogPageDTO;
import com.zhengqing.system.model.vo.SysLogPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 系统管理-操作日志 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/19 16:32
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/19 16:32
     */
    IPage<SysLogPageVO> selectDataPage(IPage<SysLogPageVO> page, @Param("filter") SysLogPageDTO filter);

    /**
     * 清理n天前的日志
     *
     * @param day 天数
     * @return void
     * @author zhengqingya
     * @date 2023/10/19 16:32
     */
    void deleteDataBeforeDay(@Param("day") Integer day);

}
