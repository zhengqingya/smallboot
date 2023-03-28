package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.entity.PmsSpuRate;
import com.zhengqing.mall.model.dto.MiniPmsSpuRatePageDTO;
import com.zhengqing.mall.model.vo.MiniPmsSpuRatePageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 商城-商品评价 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 17:10
 */
public interface PmsSpuRateMapper extends BaseMapper<PmsSpuRate> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/10/21 17:10
     */
    IPage<MiniPmsSpuRatePageVO> selectDataList(IPage<MiniPmsSpuRatePageVO> page, @Param("filter") MiniPmsSpuRatePageDTO filter);


}
