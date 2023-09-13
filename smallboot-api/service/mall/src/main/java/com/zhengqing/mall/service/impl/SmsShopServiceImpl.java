package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.mall.entity.SmsShop;
import com.zhengqing.mall.mapper.SmsShopMapper;
import com.zhengqing.mall.model.dto.SmsShopDetailDTO;
import com.zhengqing.mall.model.dto.SmsShopPageDTO;
import com.zhengqing.mall.model.dto.SmsShopSaveDTO;
import com.zhengqing.mall.model.vo.SmsShopDetailVO;
import com.zhengqing.mall.model.vo.SmsShopPageVO;
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
    public IPage<SmsShopPageVO> page(SmsShopPageDTO params) {
        IPage<SmsShopPageVO> result = this.smsShopMapper.selectDataPage(new Page<>(), params);
        List<SmsShopPageVO> list = result.getRecords();
        list.forEach(SmsShopPageVO::handleData);
        return result;
    }

    @Override
    public SmsShopDetailVO detail(SmsShopDetailDTO params){
        SmsShopDetailVO detailData = this.smsShopMapper.detail(params);
        Assert.notNull(detailData, "该数据不存在！");
        return detailData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(SmsShopSaveDTO params) {
        Integer shopId = params.getShopId();
        Integer tenantId = params.getTenantId();
        String shopName = params.getShopName();
        Integer provinceCode = params.getProvinceCode();
        Integer cityCode = params.getCityCode();
        Integer areaCode = params.getAreaCode();
        String provinceName = params.getProvinceName();
        String cityName = params.getCityName();
        String areaName = params.getAreaName();
        String address = params.getAddress();
        Byte type = params.getType();
        String contactName = params.getContactName();
        String contactPhone = params.getContactPhone();
        String longitude = params.getLongitude();
        String latitude = params.getLatitude();
        String deliverFeeJson = params.getDeliverFeeJson();
        Integer deliverDistance = params.getDeliverDistance();
        String deliverScopeJson = params.getDeliverScopeJson();
        Byte isShow = params.getIsShow();
        Byte snackStatus = params.getSnackStatus();
        Byte takeoutStatus = params.getTakeoutStatus();
        Byte openStatus = params.getOpenStatus();
        String openTimeJson = params.getOpenTimeJson();
        String startTime = params.getStartTime();
        String endTime = params.getEndTime();

        SmsShop.builder()
            .shopId(shopId)
            .tenantId(tenantId)
            .shopName(shopName)
            .provinceCode(provinceCode)
            .cityCode(cityCode)
            .areaCode(areaCode)
            .provinceName(provinceName)
            .cityName(cityName)
            .areaName(areaName)
            .address(address)
            .type(type)
            .contactName(contactName)
            .contactPhone(contactPhone)
            .longitude(longitude)
            .latitude(latitude)
            .deliverFeeJson(deliverFeeJson)
            .deliverDistance(deliverDistance)
            .deliverScopeJson(deliverScopeJson)
            .isShow(isShow)
            .snackStatus(snackStatus)
            .takeoutStatus(takeoutStatus)
            .openStatus(openStatus)
            .openTimeJson(openTimeJson)
            .startTime(startTime)
            .endTime(endTime)
            .build()
            .insertOrUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer shopId){
        this.smsShopMapper.deleteById(shopId);
    }

}
