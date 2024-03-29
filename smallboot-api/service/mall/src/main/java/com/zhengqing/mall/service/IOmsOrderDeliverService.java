package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.entity.OmsOrderDeliver;
import com.zhengqing.mall.model.vo.OmsOrderDeliverVO;

import java.util.List;
import java.util.Map;

/**
 * <p>  商城-订单配送表 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:56
 */
public interface IOmsOrderDeliverService extends IService<OmsOrderDeliver> {

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     * @author zhengqingya
     * @date 2021/08/30 13:56
     */
    OmsOrderDeliver detail(String id);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2021/08/30 13:56
     */
    void deleteData(String id);

    /**
     * 新增
     *
     * @param omsOrderDeliver 配送信息
     * @return void
     * @author zhengqingya
     * @date 2021/10/15 9:48
     */
    String addData(OmsOrderDeliver omsOrderDeliver);

    /**
     * 更新
     *
     * @param omsOrderDeliver 配送信息
     * @return void
     * @author zhengqingya
     * @date 2021/10/15 9:48
     */
    String updateData(OmsOrderDeliver omsOrderDeliver);

    /**
     * 根据订单号查询关联配送信息
     *
     * @param orderNo 订单编号
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/30 13:56
     */
    List<OmsOrderDeliverVO> listByOrderNo(String orderNo);

    /**
     * 根据订单号查询关联配送信息
     *
     * @param orderNoList 订单编号
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/30 13:56
     */
    List<OmsOrderDeliverVO> listByOrderNoList(List<String> orderNoList);

    /**
     * 根据订单号查询关联配送信息
     *
     * @param orderNoList 订单编号
     * @return 订单号 -> 配送信息
     * @author zhengqingya
     * @date 2021/08/30 13:56
     */
    Map<String, List<OmsOrderDeliverVO>> mapByOrderNoList(List<String> orderNoList);

}
