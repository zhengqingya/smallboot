package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.mall.entity.PmsAttrValue;
import com.zhengqing.mall.model.dto.WebPmsAttrValueListDTO;
import com.zhengqing.mall.model.vo.WebPmsAttrValueListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 商城-属性value Mapper 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/20 17:39
 */
public interface PmsAttrValueMapper extends BaseMapper<PmsAttrValue> {

    /**
     * 列表
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/22 15:16
     */
    List<WebPmsAttrValueListVO> selectDataList(@Param("filter") WebPmsAttrValueListDTO filter);

}
