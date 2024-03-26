package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.entity.SmsCoupon;
import com.zhengqing.mall.model.dto.SmsCouponPageDTO;
import com.zhengqing.mall.model.vo.SmsCouponPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 商城-优惠券 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/03/26 15:37
 */
public interface SmsCouponMapper extends BaseMapper<SmsCoupon> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2024/03/26 15:37
     */
    IPage<SmsCouponPageVO> selectDataPage(IPage<SmsCouponPageVO> page, @Param("filter") SmsCouponPageDTO filter);

}
