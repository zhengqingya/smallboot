package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.mall.model.vo.OmsOrderShippingItemVO;
import com.zhengqing.mall.model.vo.OmsOrderShippingVO;
import com.zhengqing.mall.entity.OmsOrderShipping;
import com.zhengqing.mall.mapper.OmsOrderShippingMapper;
import com.zhengqing.mall.service.OmsLogisticService;
import com.zhengqing.mall.service.OmsOrderShippingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
public class OmsOrderShippingServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<OmsOrderShippingMapper, OmsOrderShipping> implements OmsOrderShippingService<OmsOrderShipping> {

    @Resource
    private OmsOrderShippingMapper omsOrderShippingMapper;

    @Resource
    private OmsLogisticService omsLogisticService;


    @Override
    public OmsOrderShipping detail(String id) {
        OmsOrderShipping detailData = this.omsOrderShippingMapper.selectById(id);
        Assert.notNull(detailData, "该配送数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(String id) {
        this.omsOrderShippingMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addData(OmsOrderShipping omsOrderShipping) {
        omsOrderShipping.setId(IdGeneratorUtil.nextStrId());
        omsOrderShipping.insert();
        this.handleLogisticsData(omsOrderShipping);
        return omsOrderShipping.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateData(OmsOrderShipping omsOrderShipping) {
        Assert.isTrue(omsOrderShipping.updateById(), "该配送数据不存在！");
        this.handleLogisticsData(omsOrderShipping);
        return omsOrderShipping.getId();
    }

    /**
     * 处理物流数据
     *
     * @param omsOrderShipping 配送数据
     * @return void
     * @author zhengqingya
     * @date 2021/10/26 18:15
     */
    private void handleLogisticsData(OmsOrderShipping omsOrderShipping) {
        String logisticsCode = omsOrderShipping.getLogisticsCode();
        String logisticsNo = omsOrderShipping.getLogisticsNo();
        String receiverPhone = omsOrderShipping.getReceiverPhone();
        if (StringUtils.isBlank(logisticsNo)) {
            return;
        }
        this.omsLogisticService.saveLogistic(logisticsCode, logisticsNo, receiverPhone);
    }

    @Override
    public List<OmsOrderShippingVO> listByOrderNo(String orderNo) {
        return this.mapByOrderNoList(Lists.newArrayList(orderNo)).get(orderNo);
    }

    @Override
    public List<OmsOrderShippingVO> listByOrderNoList(List<String> orderNoList) {
        List<OmsOrderShippingVO> resultList = Lists.newLinkedList();
        if (CollectionUtils.isEmpty(orderNoList)) {
            return resultList;
        }
        List<OmsOrderShippingVO> shippingList = this.omsOrderShippingMapper.selectDataListByOrderNo(orderNoList);
        // 根据配送id分组
        Map<String, List<OmsOrderShippingVO>> map = shippingList.stream().collect(Collectors.groupingBy(OmsOrderShippingVO::getShippingId));
        // 遍历装入配送关联商品信息
        for (List<OmsOrderShippingVO> shippingBOList : map.values()) {
            List<OmsOrderShippingItemVO> shippingItemBOList = Lists.newLinkedList();
            shippingBOList.forEach(shippingItemBO -> shippingItemBOList.add(OmsOrderShippingItemVO.builder()
                    .name(shippingItemBO.getSpuName())
                    .coverImg(shippingItemBO.getSpuCoverImg())
                    .build()));
            OmsOrderShippingVO omsOrderShippingVO = shippingBOList.get(0);
            omsOrderShippingVO.setSpuList(shippingItemBOList);
            resultList.add(omsOrderShippingVO);
        }
        resultList.forEach(OmsOrderShippingVO::handleData);
        return resultList;
    }

    @Override
    public Map<String, List<OmsOrderShippingVO>> mapByOrderNoList(List<String> orderNoList) {
        return this.listByOrderNoList(orderNoList)
                .stream().collect(Collectors.groupingBy(OmsOrderShippingVO::getOrderNo));
    }

}
