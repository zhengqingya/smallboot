package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.common.model.vo.MallTabConditionListVO;
import com.zhengqing.mall.entity.OmsOrderAfterSale;
import com.zhengqing.mall.web.model.dto.WebOmsOrderAfterSalePageDTO;
import com.zhengqing.mall.web.model.dto.WebOmsOrderAfterSaleUpdateDTO;
import com.zhengqing.mall.web.model.vo.WebOmsOrderAfterSaleDetailVO;
import com.zhengqing.mall.web.model.vo.WebOmsOrderAfterSalePageVO;

import java.util.List;

/**
 * <p>  商城-售后表 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
public interface WebOmsOrderAfterSaleService extends OmsOrderAfterSaleService<OmsOrderAfterSale> {

    /**
     * 获取tab条件
     *
     * @param params 查询参数
     * @return tab条件
     * @author zhengqingya
     * @date 2021/8/26 15:45
     */
    List<MallTabConditionListVO> getTabCondition(WebOmsOrderAfterSalePageDTO params);

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/10/22 16:04
     */
    IPage<WebOmsOrderAfterSalePageVO> page(WebOmsOrderAfterSalePageDTO params);

    /**
     * 详情
     *
     * @param afterSaleNo 售后编号
     * @return 详情数据
     * @author zhengqingya
     * @date 2021/10/21 11:06
     */
    WebOmsOrderAfterSaleDetailVO detailByWeb(String afterSaleNo);

    /**
     * 更新
     *
     * @param params 保存参数
     * @return 主键id
     * @author zhengqingya
     * @date 2021/08/30 13:55
     */
    String updateDataByWeb(WebOmsOrderAfterSaleUpdateDTO params);

    /**
     * 订单退款成功-回调处理
     *
     * @param payOrderNotifyBO 订单信息
     * @return void
     * @author zhengqingya
     * @date 2021/6/25 14:02
     */
//    void refundSuccessCallback(PayOrderNotifyBO payOrderNotifyBO);

}
