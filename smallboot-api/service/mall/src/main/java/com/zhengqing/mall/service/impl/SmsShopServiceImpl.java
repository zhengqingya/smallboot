package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.mall.entity.SmsShop;
import com.zhengqing.mall.mapper.SmsShopMapper;
import com.zhengqing.mall.model.dto.WebSmsShopDetailDTO;
import com.zhengqing.mall.model.dto.WebSmsShopPageDTO;
import com.zhengqing.mall.model.dto.WebSmsShopSaveDTO;
import com.zhengqing.mall.model.vo.WebSmsShopDetailVO;
import com.zhengqing.mall.model.vo.WebSmsShopPageVO;
import com.zhengqing.mall.service.ISmsShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> 商城-店铺信息 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/13 09:51
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SmsShopServiceImpl extends ServiceImpl<SmsShopMapper, SmsShop> implements ISmsShopService {

    private final SmsShopMapper smsShopMapper;

    @Override
    public IPage<WebSmsShopPageVO> page(WebSmsShopPageDTO params) {
        IPage<WebSmsShopPageVO> result = this.smsShopMapper.selectDataPage(new Page<>(), params);
        List<WebSmsShopPageVO> list = result.getRecords();
        list.forEach(WebSmsShopPageVO::handleData);
        return result;
    }

    @Override
    public WebSmsShopDetailVO detail(WebSmsShopDetailDTO params) {
        WebSmsShopDetailVO detailData = this.smsShopMapper.detail(params);
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(WebSmsShopSaveDTO params) {
        Integer shopId = params.getShopId();

        SmsShop.builder()
                .shopId(shopId)
                .shopName(params.getShopName())
                .provinceName(params.getProvinceName())
                .cityName(params.getCityName())
                .areaName(params.getAreaName())
                .address(params.getAddress())
                .type(params.getType())
                .contactName(params.getContactName())
                .contactPhone(params.getContactPhone())
                .longitude(params.getLongitude())
                .latitude(params.getLatitude())
//                .deliverFeeList(params.getDeliverFeeList())
                .deliverDistance(params.getDeliverDistance())
//                .deliverScopeList(params.getDeliverScopeList())
                .isShow(params.getIsShow())
                .snackStatus(params.getSnackStatus())
                .takeoutStatus(params.getTakeoutStatus())
                .openStatus(params.getOpenStatus())
                .openTimeList(params.getOpenTimeList())
                .build()
                .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer shopId) {
        this.smsShopMapper.deleteById(shopId);
    }

}
