package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.mall.entity.SmsShop;
import com.zhengqing.mall.mapper.SmsShopMapper;
import com.zhengqing.mall.model.dto.SmsShopPageDTO;
import com.zhengqing.mall.model.dto.WebSmsShopSaveDTO;
import com.zhengqing.mall.model.vo.SmsShopBaseVO;
import com.zhengqing.mall.service.ISmsShopService;
import com.zhengqing.system.model.dto.SysProvinceCityAreaBindReShopDTO;
import com.zhengqing.system.service.ISysProvinceCityAreaService;
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
    private final ISysProvinceCityAreaService sysProvinceCityAreaService;

    @Override
    public SmsShop detailData(Integer shopId) {
        SmsShop detailData = this.smsShopMapper.selectById(shopId);
        Assert.notNull(detailData, "门店:" + shopId + " 不存在！");
        return detailData;
    }

    @Override
    public IPage<SmsShopBaseVO> page(SmsShopPageDTO params) {
        IPage<SmsShopBaseVO> result = this.smsShopMapper.selectDataPage(new Page<>(), params);
        List<SmsShopBaseVO> list = result.getRecords();
        list.forEach(SmsShopBaseVO::handleData);
        return result;
    }

    @Override
    public SmsShopBaseVO detail(Integer shopId) {
        return this.page(SmsShopPageDTO.builder().shopId(shopId).build()).getRecords().get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(WebSmsShopSaveDTO params) {
        Integer shopId = params.getShopId();

        // 1、保存门店信息
        SmsShop smsShop = SmsShop.builder()
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
                .build();
        smsShop.insertOrUpdate();

        // 2、省市区门店数据关联处理
        this.handleRegion(true, smsShop);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer shopId) {
        // 1、查询门店信息
        SmsShop smsShop = this.detailData(shopId);

        // 2、删除门店信息
        this.smsShopMapper.deleteById(shopId);

        // 2、省市区门店数据关联处理
        this.handleRegion(false, smsShop);
    }

    /**
     * 绑定或解除关联店铺
     *
     * @param isRe    是否关联
     * @param smsShop 店铺信息
     * @return void
     * @author zhengqingya
     */
    private void handleRegion(boolean isRe, SmsShop smsShop) {
        this.sysProvinceCityAreaService.bindReShop(
                SysProvinceCityAreaBindReShopDTO.builder()
                        .isShop(isRe)
                        .provinceName(smsShop.getProvinceName())
                        .cityName(smsShop.getCityName())
                        .areaName(smsShop.getAreaName())
                        .build()
        );
    }

}
