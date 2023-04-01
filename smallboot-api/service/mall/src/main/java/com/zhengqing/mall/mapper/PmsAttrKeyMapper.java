package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.mall.entity.PmsAttrKey;
import com.zhengqing.mall.model.dto.WebPmsAttrKeyListDTO;
import com.zhengqing.mall.model.vo.WebPmsAttrKeyListVO;
import com.zhengqing.mall.model.vo.WebPmsAttrVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 商城-属性key Mapper 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/20 17:38
 */
public interface PmsAttrKeyMapper extends BaseMapper<PmsAttrKey> {

    /**
     * 列表
     *
     * @param filter 查询参数
     * @return 列表数据
     * @author zhengqingya
     * @date 2021/8/22 2:41 下午
     */
    List<WebPmsAttrKeyListVO> selectDataList(@Param("filter") WebPmsAttrKeyListDTO filter);

    /**
     * 根据ids查询列表数据
     *
     * @param idList ids
     * @return 列表数据
     * @author zhengqingya
     * @date 2021/8/31 19:38
     */
    List<WebPmsAttrVO> selectDataListByIdList(@Param("idList") List<String> idList);

}
