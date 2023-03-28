package com.zhengqing.mall.service;

import com.zhengqing.mall.entity.PmsSpuRate;
import com.zhengqing.mall.model.dto.MiniPmsSpuRateSaveDTO;

/**
 * <p>  商城-商品评价 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
public interface MiniPmsSpuRateService extends PmsSpuRateService<PmsSpuRate> {

    /**
     * 新增
     *
     * @param params 保存参数
     * @return 主键id
     * @author zhengqingya
     * @date 2021/10/21 17:10
     */
    void addBatchData(MiniPmsSpuRateSaveDTO params);

}
