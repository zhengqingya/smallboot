package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysProvinceCityArea;
import com.zhengqing.system.model.dto.SysProvinceCityAreaTreeDTO;
import com.zhengqing.system.model.vo.SysProvinceCityAreaTreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 系统管理-省市区 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/14 11:38
 */
public interface SysProvinceCityAreaMapper extends BaseMapper<SysProvinceCityArea> {

    /**
     * 列表数据
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/09/14 11:38
     */
    List<SysProvinceCityAreaTreeVO> selectDataList(@Param("filter") SysProvinceCityAreaTreeDTO filter);


}
