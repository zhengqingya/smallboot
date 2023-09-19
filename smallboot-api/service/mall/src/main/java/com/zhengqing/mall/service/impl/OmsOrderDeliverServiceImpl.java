package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.mall.entity.OmsOrderDeliver;
import com.zhengqing.mall.mapper.OmsOrderDeliverMapper;
import com.zhengqing.mall.model.vo.OmsOrderDeliverItemVO;
import com.zhengqing.mall.model.vo.OmsOrderDeliverVO;
import com.zhengqing.mall.service.IOmsLogisticService;
import com.zhengqing.mall.service.IOmsOrderDeliverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 商城-订单配送表 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:56
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OmsOrderDeliverServiceImpl extends ServiceImpl<OmsOrderDeliverMapper, OmsOrderDeliver> implements IOmsOrderDeliverService {

    private final OmsOrderDeliverMapper omsOrderDeliverMapper;
    private final IOmsLogisticService iOmsLogisticService;


    @Override
    public OmsOrderDeliver detail(String id) {
        OmsOrderDeliver detailData = this.omsOrderDeliverMapper.selectById(id);
        Assert.notNull(detailData, "该配送数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(String id) {
        this.omsOrderDeliverMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addData(OmsOrderDeliver omsOrderDeliver) {
        omsOrderDeliver.setId(IdGeneratorUtil.nextStrId());
        omsOrderDeliver.insert();
        this.handleLogisticsData(omsOrderDeliver);
        return omsOrderDeliver.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateData(OmsOrderDeliver omsOrderDeliver) {
        Assert.isTrue(omsOrderDeliver.updateById(), "该配送数据不存在！");
        this.handleLogisticsData(omsOrderDeliver);
        return omsOrderDeliver.getId();
    }

    /**
     * 处理物流数据
     *
     * @param omsOrderDeliver 配送数据
     * @return void
     * @author zhengqingya
     * @date 2021/10/26 18:15
     */
    private void handleLogisticsData(OmsOrderDeliver omsOrderDeliver) {
        String logisticsCode = omsOrderDeliver.getLogisticsCode();
        String logisticsNo = omsOrderDeliver.getLogisticsNo();
        String receiverPhone = omsOrderDeliver.getReceiverPhone();
        if (StringUtils.isBlank(logisticsNo)) {
            return;
        }
        this.iOmsLogisticService.saveLogistic(logisticsCode, logisticsNo, receiverPhone);
    }

    @Override
    public List<OmsOrderDeliverVO> listByOrderNo(String orderNo) {
        return this.mapByOrderNoList(Lists.newArrayList(orderNo)).get(orderNo);
    }

    @Override
    public List<OmsOrderDeliverVO> listByOrderNoList(List<String> orderNoList) {
        List<OmsOrderDeliverVO> resultList = Lists.newLinkedList();
        if (CollectionUtils.isEmpty(orderNoList)) {
            return resultList;
        }
        List<OmsOrderDeliverVO> deliverList = this.omsOrderDeliverMapper.selectDataListByOrderNo(orderNoList);
        // 根据配送id分组
        Map<String, List<OmsOrderDeliverVO>> map = deliverList.stream().collect(Collectors.groupingBy(OmsOrderDeliverVO::getId));
        // 遍历装入配送关联商品信息
        for (List<OmsOrderDeliverVO> deliverItemList : map.values()) {
            List<OmsOrderDeliverItemVO> itemList = Lists.newLinkedList();
            deliverItemList.forEach(item -> itemList.add(OmsOrderDeliverItemVO.builder()
                    .name(item.getSpuName())
                    .coverImg(item.getSpuCoverImg())
                    .build()));
            OmsOrderDeliverVO omsOrderDeliverVO = deliverItemList.get(0);
            omsOrderDeliverVO.setSpuList(itemList);
            resultList.add(omsOrderDeliverVO);
        }
        resultList.forEach(OmsOrderDeliverVO::handleData);
        return resultList;
    }

    @Override
    public Map<String, List<OmsOrderDeliverVO>> mapByOrderNoList(List<String> orderNoList) {
        return this.listByOrderNoList(orderNoList)
                .stream().collect(Collectors.groupingBy(OmsOrderDeliverVO::getOrderNo));
    }

}
