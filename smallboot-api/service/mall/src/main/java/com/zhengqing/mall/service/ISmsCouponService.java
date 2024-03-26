package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.entity.SmsCoupon;
import com.zhengqing.mall.model.dto.SmsCouponPageDTO;
import com.zhengqing.mall.model.dto.SmsCouponSaveDTO;
import com.zhengqing.mall.model.vo.SmsCouponPageVO;

/**
 * <p>  商城-优惠券 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/03/26 15:37
 */
public interface ISmsCouponService extends IService<SmsCoupon> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2024/03/26 15:37
     */
    IPage<SmsCouponPageVO> page(SmsCouponPageDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2024/03/26 15:37
     */
    void addOrUpdateData(SmsCouponSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2024/03/26 15:37
     */
    void deleteData(Long id);

}
