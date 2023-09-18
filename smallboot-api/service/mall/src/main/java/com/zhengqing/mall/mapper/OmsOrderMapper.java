package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.entity.OmsOrder;
import com.zhengqing.mall.model.dto.OmsOrderPageDTO;
import com.zhengqing.mall.model.vo.MallTabConditionListVO;
import com.zhengqing.mall.model.vo.OmsOrderBaseVO;
import com.zhengqing.mall.model.vo.WebOmsOrderExportVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * <p> 商城-订单信息 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:40
 */
public interface OmsOrderMapper extends BaseMapper<OmsOrder> {

    /**
     * 查询指定订单
     *
     * @param orderNo 订单号
     * @return 订单数据
     * @author zhengqingya
     * @date 2022/3/29 17:18
     */
    @Select("SELECT * FROM oms_order WHERE order_no=#{orderNo} LIMIT 1")
    OmsOrder selectOrder(@Param("orderNo") String orderNo);

    /**
     * 删除订单
     *
     * @param orderNoList 订单号
     * @return 操作条数
     * @author zhengqingya
     * @date 2022/3/29 17:18
     */
    int deleteBatchOrder(@Param("orderNoList") List<String> orderNoList);

    /**
     * 获取tab条件 - web
     *
     * @param filter 查询过滤参数
     * @return tab条件
     * @author zhengqingya
     * @date 2021/8/26 15:45
     */
    List<MallTabConditionListVO> selectTabConditionByWeb(@Param("filter") OmsOrderPageDTO filter);

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/30 13:40
     */
    IPage<OmsOrderBaseVO> selectDataList(IPage<OmsOrderBaseVO> page, @Param("filter") OmsOrderPageDTO filter);

    /**
     * 查询导出订单数据
     *
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/30 13:40
     */
    List<WebOmsOrderExportVO> selectExportDataListByWeb(@Param("filter") OmsOrderPageDTO filter);

    /**
     * 详情
     *
     * @param orderNo 订单编号
     * @return 详情
     * @author zhengqingya
     * @date 2021/8/30 17:31
     */
    OmsOrderBaseVO detailData(@Param("orderNo") String orderNo);


    /**
     * 查询此订单售后处理截止时间
     *
     * @param orderNo 订单号
     * @return 售后处理截止时间
     * @author zhengqingya
     * @date 2021/12/10 14:58
     */
    Date getAfterSaleEndTime(@Param("orderNo") String orderNo);

}
