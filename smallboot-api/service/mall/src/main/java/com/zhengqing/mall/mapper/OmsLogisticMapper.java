package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.entity.OmsLogistic;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p> 商城-物流信息表 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/26 15:01
 */
public interface OmsLogisticMapper extends BaseMapper<OmsLogistic> {

    /**
     * 查询之前更新时间之前-未完成的物流信息数据
     *
     * @param updateTime 更新时间
     * @return 未完成的物流信息数据
     * @author zhengqingya
     * @date 2021/10/26 19:01
     */
    IPage<OmsLogistic> selectUnFinishData(IPage<OmsLogistic> page, @Param("updateTime") Date updateTime);

}
