package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.common.model.bo.OmsOrderAfterSaleCloseBO;
import com.zhengqing.mall.common.model.dto.OmsOrderAfterSaleDeleteDTO;
import com.zhengqing.mall.entity.OmsOrderAfterSale;

import java.util.List;
import java.util.Map;

/**
 * <p>  商城-售后表 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
public interface OmsOrderAfterSaleService<T> extends IService<OmsOrderAfterSale> {

    /**
     * 详情
     *
     * @param afterSaleNo 售后编号
     * @return 详情
     * @author zhengqingya
     * @date 2022/3/4 16:12
     */
    OmsOrderAfterSale detail(String afterSaleNo);

    /**
     * 新增
     *
     * @param omsOrderAfterSale 保存参数
     * @return 主键id
     * @author zhengqingya
     * @date 2021/08/30 13:55
     */
    String addData(OmsOrderAfterSale omsOrderAfterSale);

    /**
     * 更新
     *
     * @param omsOrderAfterSale 保存参数
     * @return 售后编号
     * @author zhengqingya
     * @date 2021/08/30 13:55
     */
    String updateData(OmsOrderAfterSale omsOrderAfterSale);

    /**
     * 详情
     *
     * @param afterSaleNo 售后编号
     * @return 详情
     * @author zhengqingya
     * @date 2021/08/30 13:55
     */
    OmsOrderAfterSale getOrderAfterSale(String afterSaleNo);

    /**
     * 批量删除
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:05
     */
    void deleteBatch(OmsOrderAfterSaleDeleteDTO params);

    /**
     * 查询该订单是否处理售后(售后中/售后完成)
     *
     * @param orderNoList 订单编号
     * @return 订单号 -> 是否售后中
     * @author zhengqingya
     * @date 2021/08/30 13:55
     */
    Map<String, Boolean> mapByOrderNoList(List<String> orderNoList);

    /**
     * 查询该订单属于售后(售后中/售后完成) 每一个订单详情id
     *
     * @param orderNo 订单编号
     * @return 属于售后的订单详情ids
     * @author zhengqingya
     * @date 2021/08/30 13:55
     */
    List<String> orderItemIdsForAfterSale(String orderNo);

    /**
     * 查询订单属于售后(售后中/售后完成) 每一个订单详情id
     *
     * @param orderNoList 订单编号
     * @return 订单号 -> 属于售后的订单详情ids
     * @author zhengqingya
     * @date 2021/08/30 13:55
     */
    Map<String, List<String>> mapOrderItemIdsForAfterSale(List<String> orderNoList);

    /**
     * 买家/卖家未处理，自动关闭
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/10/23 5:08 下午
     */
    void unHandleAutoClose(OmsOrderAfterSaleCloseBO params);

    /**
     * 获取处理中的数量
     *
     * @param userId 用户ID
     * @return 数量
     * @author zhengqingya
     * @date 2021/12/3 17:42
     */
    int getHandleIngNum(Long userId);

    /**
     * 根据订单号查询不可申请售后的订单详情ids
     *
     * @param orderNo 订单号
     * @return 可申请售后的订单详情ids
     * @author zhengqingya
     * @date 2021/12/10 15:05
     */
    List<String> getNoApplyReOrderItemIdListByOrderNo(String orderNo);

}
