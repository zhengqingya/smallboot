package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.entity.OmsOrderAfterSale;
import com.zhengqing.mall.model.dto.MiniOmsOrderAfterSalePageDTO;
import com.zhengqing.mall.model.dto.MiniOmsOrderRepealAfterSaleDTO;
import com.zhengqing.mall.model.vo.MiniOmsOrderAfterSaleDetailVO;
import com.zhengqing.mall.model.vo.MiniOmsOrderAfterSalePageVO;

/**
 * <p>  商城-售后表 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
public interface MiniOmsOrderAfterSaleService extends OmsOrderAfterSaleService<OmsOrderAfterSale> {

    /**
     * 分页列表
     *
     * @param params 提交参数
     * @return 订单信息
     * @author zhengqingya
     * @date 2021/10/18 11:46
     */
    IPage<MiniOmsOrderAfterSalePageVO> page(MiniOmsOrderAfterSalePageDTO params);

    /**
     * 详情
     *
     * @param afterSaleNo 售后编号
     * @return 详情数据
     * @author zhengqingya
     * @date 2021/10/21 11:06
     */
    MiniOmsOrderAfterSaleDetailVO detailByMini(String afterSaleNo);

    /**
     * 申请售后-撤销
     *
     * @param params
     * @return void
     * @author zhengqingya
     * @date 2021/10/21 14:53
     */
    void repeal(MiniOmsOrderRepealAfterSaleDTO params);

}
