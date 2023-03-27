package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.entity.PmsSpuRate;
import com.zhengqing.mall.mini.model.dto.MiniPmsSpuRatePageDTO;
import com.zhengqing.mall.mini.model.vo.MiniPmsSpuRatePageVO;

/**
 * <p>  商城-商品评价 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/21 17:10
 */
public interface PmsSpuRateService<T> extends IService<PmsSpuRate> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/10/21 17:10
     */
    IPage<MiniPmsSpuRatePageVO> page(MiniPmsSpuRatePageDTO params);

    /**
     * 删除数据
     *
     * @param spuRateId 主键ID
     * @return void
     * @author zhengqingya
     * @date 2021/10/21 17:10
     */
    void deleteData(String spuRateId);

}
