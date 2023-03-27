package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.mall.common.model.vo.OmsOrderShippingVO;
import com.zhengqing.mall.entity.OmsOrderShipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 商城-订单配送表 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:56
 */
public interface OmsOrderShippingMapper extends BaseMapper<OmsOrderShipping> {

    /**
     * 列表
     *
     * @param orderNoList 订单编号
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/30 13:56
     */
    List<OmsOrderShippingVO> selectDataListByOrderNo(@Param("orderNoList") List<String> orderNoList);

}
