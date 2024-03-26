package com.zhengqing.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.mall.entity.SmsCoupon;
import com.zhengqing.mall.mapper.SmsCouponMapper;
import com.zhengqing.mall.model.dto.SmsCouponPageDTO;
import com.zhengqing.mall.model.dto.SmsCouponSaveDTO;
import com.zhengqing.mall.model.vo.SmsCouponPageVO;
import com.zhengqing.mall.service.ISmsCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> 商城-优惠券 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/03/26 15:37
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SmsCouponServiceImpl extends ServiceImpl<SmsCouponMapper, SmsCoupon> implements ISmsCouponService {

    private final SmsCouponMapper smsCouponMapper;

    @Override
    public IPage<SmsCouponPageVO> page(SmsCouponPageDTO params) {
        IPage<SmsCouponPageVO> result = this.smsCouponMapper.selectDataPage(new Page<>(), params);
        List<SmsCouponPageVO> list = result.getRecords();
        list.forEach(SmsCouponPageVO::handleData);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SmsCouponSaveDTO params) {
        SmsCoupon.builder()
                .id(params.getId())
                .name(params.getName())
                .status(params.getStatus())
                .type(params.getType())
                .discount(params.getDiscount())
                .discountMaxPrice(params.getDiscountMaxPrice())
                .fullPrice(params.getFullPrice())
                .reducePrice(params.getReducePrice())
                .faceValue(params.getFaceValue())
                .threshold(params.getThreshold())
                .isGive(params.getIsGive())
                .indateObj(params.getIndateObj())
                .useShopIdList(params.getUseShopIdList())
                .useSpuIdList(params.getUseSpuIdList())
                .explain(params.getExplain())
                .totalStock(params.getTotalStock())
                .useStock(params.getUseStock())
                .limitCount(params.getLimitCount())
                .openTimeList(params.getOpenTimeList())
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Long id) {
        this.smsCouponMapper.deleteById(id);
    }

}
