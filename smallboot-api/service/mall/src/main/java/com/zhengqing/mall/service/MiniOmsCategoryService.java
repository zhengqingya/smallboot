package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.entity.PmsCategory;
import com.zhengqing.mall.model.dto.MiniPmsCategoryListDTO;
import com.zhengqing.mall.model.dto.MiniPmsCategoryPageDTO;
import com.zhengqing.mall.model.dto.MiniPmsCategoryReSpuListDTO;
import com.zhengqing.mall.model.vo.MiniPmsCategoryListVO;
import com.zhengqing.mall.model.vo.MiniPmsCategoryPageVO;
import com.zhengqing.mall.model.vo.MiniPmsCategoryReSpuListVO;

import java.util.List;

/**
 * <p>  商城-属性key 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
public interface MiniOmsCategoryService extends OmsCategoryService<PmsCategory> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    IPage<MiniPmsCategoryPageVO> page(MiniPmsCategoryPageDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/3/7 18:43
     */
    List<MiniPmsCategoryListVO> list(MiniPmsCategoryListDTO params);

    /**
     * 列表(包含关联商品数据)
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    List<MiniPmsCategoryReSpuListVO> reSpuList(MiniPmsCategoryReSpuListDTO params);

    /**
     * 分页列表(包含4个商品数据)
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    IPage<MiniPmsCategoryReSpuListVO> reSpuPage(MiniPmsCategoryReSpuListDTO params);


}
