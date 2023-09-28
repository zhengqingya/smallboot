package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.entity.OmsLogistic;
import com.zhengqing.mall.model.dto.OmsLogisticDTO;
import com.zhengqing.mall.enums.TpsLogisticsCodeEnum;
import com.zhengqing.mall.model.vo.OmsLogisticVO;

/**
 * <p>  商城-物流信息表 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/26 15:01
 */
public interface IOmsLogisticService extends IService<OmsLogistic> {

    /**
     * 详情
     *
     * @param params 查询参数
     * @return 详情
     * @author zhengqingya
     * @date 2021/10/26 15:01
     */
    OmsLogisticVO detail(OmsLogisticDTO params);

    /**
     * 保存物流信息
     *
     * @param logisticsCode 物流公司编码 {@link TpsLogisticsCodeEnum }
     * @param logisticsNo   快递公司快递号
     * @param receiverPhone 收货人电话
     * @return void
     * @author zhengqingya
     * @date 2021/10/26 18:17
     */
    void saveLogistic(String logisticsCode, String logisticsNo, String receiverPhone);

    /**
     * 新增或更新
     *
     * @param omsLogistic 保存参数
     * @return 主键id
     * @author zhengqingya
     * @date 2021/10/26 15:01
     */
    String addOrUpdateData(OmsLogistic omsLogistic);

    /**
     * 更新数据库中的物流信息
     *
     * @return void
     * @author zhengqingya
     * @date 2021/10/26 17:49
     */
    void updateDb();

}
