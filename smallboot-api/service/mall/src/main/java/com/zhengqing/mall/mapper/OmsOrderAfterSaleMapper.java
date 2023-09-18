package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.entity.OmsOrderAfterSale;
import com.zhengqing.mall.model.bo.PmsSkuStockBO;
import com.zhengqing.mall.model.dto.OmsOrderAfterSalePageDTO;
import com.zhengqing.mall.model.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 商城-售后表 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:55
 */
public interface OmsOrderAfterSaleMapper extends BaseMapper<OmsOrderAfterSale> {

    /**
     * 获取tab条件
     *
     * @param filter 查询过滤参数
     * @return tab条件
     * @author zhengqingya
     * @date 2021/8/26 15:45
     */
    List<MallTabConditionListVO> selectTabCondition(@Param("filter") OmsOrderAfterSalePageDTO filter);

    /**
     * 分页列表
     *
     * @param page   分页参数
     * @param filter 查询过滤参数
     * @return 售后信息
     * @author zhengqingya
     * @date 2021/10/18 11:46
     */
    IPage<OmsOrderAfterSalePageVO> selectDataListByWeb(IPage<OmsOrderAfterSalePageVO> page, @Param("filter") OmsOrderAfterSalePageDTO filter);

    /**
     * 查询该订单是否处理售后(售后中/售后完成)
     *
     * @param orderNoList 订单编号
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/30 13:55
     */
    List<OmsOrderAfterSaleVO> selectListByOrderNoList(@Param("orderNoList") List<String> orderNoList);

    /**
     * 查询订单属于售后(售后中/售后完成) 每一个订单详情id
     *
     * @param orderNoList 订单编号
     * @return 属于售后的订单详情ids
     * @author zhengqingya
     * @date 2021/08/30 13:55
     */
    List<OmsOrderAfterSaleItemVO> selectOrderItemIdsReAfterSaleStatus(@Param("orderNoList") List<String> orderNoList);

    /**
     * 详情
     *
     * @param afterSaleNo 售后编号
     * @return 详情
     * @author zhengqingya
     * @date 2021/8/30 17:31
     */
    OmsOrderAfterSaleDetailVO detailByWeb(@Param("afterSaleNo") String afterSaleNo);

    /**
     * 查询该售后订单关联的商品信息
     *
     * @param afterSaleNo 售后订单编号
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/30 13:55
     */
    List<PmsSkuStockBO> selectSpuListByAfterSaleNo(@Param("afterSaleNo") String afterSaleNo);


    /**
     * 获取处理中的数量
     *
     * @param userId 用户ID
     * @return 数量
     * @author zhengqingya
     * @date 2021/12/3 17:42
     */
    int selectHandleIngNum(@Param("userId") Long userId);

    // ------------------------------------------------------------------------------------------------------


    /**
     * 根据订单号查询不可申请售后的订单详情ids
     *
     * @param orderNo 订单号
     * @return 可申请售后的订单详情ids
     * @author zhengqingya
     * @date 2021/12/10 15:05
     */
    List<String> selectNoApplyReOrderItemIdListByOrderNo(@Param("orderNo") String orderNo);

}
