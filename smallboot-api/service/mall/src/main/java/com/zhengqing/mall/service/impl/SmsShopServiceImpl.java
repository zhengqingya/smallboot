package com.zhengqing.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.redis.model.bo.RedisGeoPoint;
import com.zhengqing.common.redis.util.RedisGeoUtil;
import com.zhengqing.mall.constant.MallRedisConstant;
import com.zhengqing.mall.entity.SmsShop;
import com.zhengqing.mall.mapper.SmsShopMapper;
import com.zhengqing.mall.model.bo.LngLatBO;
import com.zhengqing.mall.model.dto.SmsShopLatelyDTO;
import com.zhengqing.mall.model.dto.SmsShopPageDTO;
import com.zhengqing.mall.model.dto.WebSmsShopEditStatusDTO;
import com.zhengqing.mall.model.dto.WebSmsShopSaveDTO;
import com.zhengqing.mall.model.vo.SmsShopBaseVO;
import com.zhengqing.mall.service.ISmsShopService;
import com.zhengqing.mall.util.GeoUtil;
import com.zhengqing.system.model.dto.SysProvinceCityAreaBindReShopDTO;
import com.zhengqing.system.service.ISysProvinceCityAreaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
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
        Double longitude = params.getLongitude();
        if (longitude != null) {
            LngLatBO me = new LngLatBO(longitude, params.getLatitude());
            list.forEach(e -> {
                e.setDistance(
                        GeoUtil.getDistanceMeters(
                                me,
                                new LngLatBO(e.getLongitude(), e.getLatitude())
                        )
                );
                e.handleData();
            });
        } else {
            list.forEach(SmsShopBaseVO::handleData);
        }
        return result;
    }

    @Override
    public SmsShopBaseVO detail(SmsShopPageDTO params) {
        List<SmsShopBaseVO> result = this.page(params).getRecords();
        Assert.notEmpty(result, "暂无此门店数据！");
        return result.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateData(WebSmsShopSaveDTO params) {
        Integer shopId = params.getShopId();
        Boolean isShow = params.getIsShow();

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
                .isShow(isShow)
                .snackStatus(params.getSnackStatus())
                .takeoutStatus(params.getTakeoutStatus())
                .openStatus(params.getOpenStatus())
                .openTimeList(params.getOpenTimeList())
                .build();
        smsShop.insertOrUpdate();

        // 2、其它处理
        this.handleRegion(isShow, smsShop);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(Integer shopId) {
        // 1、查询门店信息
        SmsShop smsShop = this.detailData(shopId);

        // 2、删除门店信息
        this.smsShopMapper.deleteById(shopId);

        // 2、其它处理
        this.handleRegion(false, smsShop);
    }

    /**
     * 绑定或解除关联店铺
     *
     * @param isSaveOperate 是否为保存操作
     * @param smsShop       店铺信息
     * @return void
     * @author zhengqingya
     */
    private void handleRegion(boolean isSaveOperate, SmsShop smsShop) {
        // 1、省市区关联门店数据 -- 绑定或解除
        this.sysProvinceCityAreaService.bindReShop(
                SysProvinceCityAreaBindReShopDTO.builder()
                        .isShop(isSaveOperate)
                        .provinceName(smsShop.getProvinceName())
                        .cityName(smsShop.getCityName())
                        .areaName(smsShop.getAreaName())
                        .build()
        );

        // 2、redis geo数据
        String shopId = String.valueOf(smsShop.getShopId());
        if (isSaveOperate) {
            RedisGeoUtil.geoAdd(this.getShopGeoKey(), new Point(smsShop.getLongitude(), smsShop.getLatitude()), shopId);
        } else {
            RedisGeoUtil.geoDel(this.getShopGeoKey(), shopId);
        }
    }

    @Override
    public SmsShopBaseVO lately(SmsShopLatelyDTO params) {
        List<RedisGeoPoint> geoPointList = RedisGeoUtil.geoNear(this.getShopGeoKey(), params.getLongitude(), params.getLatitude(), 3, RedisGeoCommands.DistanceUnit.KILOMETERS, 1);
        if (CollUtil.isEmpty(geoPointList)) {
            return null;
        }
        RedisGeoPoint redisGeoPoint = geoPointList.get(0);
        String shopId = redisGeoPoint.getMember();

        try {
            return this.detail(SmsShopPageDTO.builder().shopId(Integer.valueOf(shopId)).isShow(true).build());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取门店geo redis缓存key
     */
    private String getShopGeoKey() {
        return MallRedisConstant.SHOP_GEO_LOCATION + TenantIdContext.getTenantId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchStatus(WebSmsShopEditStatusDTO params) {
        List<Integer> shopIdList = params.getShopIdList();
        Boolean isShow = params.getIsShow();
        log.info("[商城] 批量更新门店状态：{}", JSONUtil.toJsonStr(params));
        if (CollUtil.isEmpty(shopIdList)) {
            return;
        }
        this.smsShopMapper.updateBatchStatus(params);

        if (isShow) {
            shopIdList.forEach(shopId -> this.handleRegion(true, this.detailData(shopId)));
        }
    }

}
