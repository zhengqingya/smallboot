package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.common.model.vo.MallTabConditionListVO;
import com.zhengqing.mall.entity.OmsOrder;
import com.zhengqing.mall.web.model.dto.WebOmsOrderPageDTO;
import com.zhengqing.mall.web.model.dto.WebOmsOrderSendSpuDTO;
import com.zhengqing.mall.web.model.vo.WebOmsOrderDetailVO;
import com.zhengqing.mall.web.model.vo.WebOmsOrderPageVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>  商城-订单信息 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:40
 */
public interface WebOmsOrderService extends OmsOrderService<OmsOrder> {

    /**
     * 获取订单设置数据
     *
     * @return 订单设置数据
     * @author zhengqingya
     * @date 2021/9/24 14:29
     */
//    WebOmsOrderSetVO getOrderSet();

    /**
     * 更新订单设置数据
     *
     * @param params 提交数据
     * @return void
     * @author zhengqingya
     * @date 2021/9/24 14:30
     */
//    void updateOrderSet(@Validated @RequestBody WebOmsOrderSetDTO params);

    /**
     * 获取tab条件
     *
     * @param params 查询参数
     * @return tab条件
     * @author zhengqingya
     * @date 2021/8/26 15:45
     */
    List<MallTabConditionListVO> getTabCondition(WebOmsOrderPageDTO params);

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/30 13:40
     */
    IPage<WebOmsOrderPageVO> page(WebOmsOrderPageDTO params);

    /**
     * 查询订单
     *
     * @param orderNo 订单编号
     * @return 详情
     * @author zhengqingya
     * @date 2021/08/30 13:40
     */
    WebOmsOrderDetailVO detail(String orderNo);

    /**
     * 订单发货
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/10/21 9:25
     */
    void sendSpu(WebOmsOrderSendSpuDTO params);

    /**
     * 导出
     *
     * @param response 响应
     * @param params   请求参数
     * @return void
     * @author zhengqingya
     * @date 2021/7/6 17:53
     */
    void export(HttpServletResponse response, WebOmsOrderPageDTO params);

    /**
     * 批量导入发货
     *
     * @param file 提交文件
     * @return void
     * @author zhengqingya
     * @date 2022/1/25 10:10
     */
    void importBatchSendSpu(MultipartFile file);

}
