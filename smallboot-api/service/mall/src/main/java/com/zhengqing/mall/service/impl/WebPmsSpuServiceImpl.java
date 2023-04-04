package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.mall.constant.MallRabbitMqConstant;
import com.zhengqing.mall.entity.PmsSku;
import com.zhengqing.mall.entity.PmsSpu;
import com.zhengqing.mall.mapper.PmsSpuMapper;
import com.zhengqing.mall.model.bo.PmsSkuBO;
import com.zhengqing.mall.model.bo.PmsSpuReCouponBO;
import com.zhengqing.mall.model.dto.*;
import com.zhengqing.mall.model.vo.MallTabConditionListVO;
import com.zhengqing.mall.model.vo.PmsSpuTypeVO;
import com.zhengqing.mall.model.vo.WebPmsSpuDetailVO;
import com.zhengqing.mall.model.vo.WebPmsSpuPageVO;
import com.zhengqing.mall.service.MallCommonService;
import com.zhengqing.mall.service.WebPmsCategorySpuRelationService;
import com.zhengqing.mall.service.WebPmsSkuService;
import com.zhengqing.mall.service.WebPmsSpuService;
import com.zhengqing.system.enums.SysDictTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p> 商城-商品 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@Slf4j
@Service
public class WebPmsSpuServiceImpl extends PmsSpuServiceImpl<PmsSpuMapper, PmsSpu> implements WebPmsSpuService {

    @Resource
    private PmsSpuMapper pmsSpuMapper;

    @Resource
    private WebPmsSkuService webPmsSkuService;

    @Resource
    private MallCommonService mallCommonService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private WebPmsCategorySpuRelationService webPmsCategorySpuRelationService;

    @Override
    public List<MallTabConditionListVO> getTabCondition(WebPmsSpuPageDTO params) {
        params.setIsPut(null);
        // 查询tab条件数量
        List<MallTabConditionListVO> tabDataList = this.pmsSpuMapper.selectTabCondition(params);
        return this.mallCommonService.getTabDataList(tabDataList,
                SysDictTypeEnum.MALL_SPU_TAB_CONDITION);
    }

    @Override
    public IPage<WebPmsSpuPageVO> page(WebPmsSpuPageDTO params) {
        IPage<WebPmsSpuPageVO> result = this.pmsSpuMapper.selectDataListByWeb(new Page<>(), params);
        List<WebPmsSpuPageVO> list = result.getRecords();
        if (CollectionUtils.isEmpty(list)) {
            return result;
        }
        // 商品ids
        List<String> spuIdList = list.stream().map(WebPmsSpuPageVO::getId).collect(Collectors.toList());
        // 查询关联规格数据
        Map<String, List<PmsSkuBO>> skuDataMap = this.webPmsSkuService.mapBySpuId(spuIdList);
        for (WebPmsSpuPageVO item : list) {
            String id = item.getId();
            List<PmsSkuBO> skuItemList = skuDataMap.get(id);
            item.setSkuList(skuItemList);
            item.handleData(skuItemList);
        }
        return result;
    }

    @Override
    public Map<String, PmsSpuTypeVO> mapSpuType(List<String> idList) {
        Map<String, PmsSpuTypeVO> resultMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(idList)) {
            return resultMap;
        }
        List<PmsSpuTypeVO> list = this.pmsSpuMapper.selectDataTypeList(idList);
        for (PmsSpuTypeVO item : list) {
            resultMap.put(item.getId(), item);
        }
        return resultMap;
    }

    @Override
    public WebPmsSpuDetailVO detail(String id) {
        WebPmsSpuDetailVO spuDetail = this.pmsSpuMapper.detailByWeb(id);
        Assert.notNull(spuDetail, "该数据不存在！");
        // 查询关联规格数据
        List<PmsSkuBO> skuList = this.webPmsSkuService.listBySpuId(id);
        spuDetail.setSkuList(skuList);
        // 处理数据
        spuDetail.handleData(skuList);
        // 实时获取关联服务和说明数据
//        this.handleSpuData(spuDetail);
        return spuDetail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addOrUpdateData(WebPmsSpuSaveDTO params) {
        String id = params.getId();
        Boolean isPresell = params.getIsPresell();
        Date presellStartTime = params.getPresellStartTime();
        List<WebPmsSkuSaveDTO> skuList = params.getSkuList();

        boolean isAdd = (id == null);
        if (isAdd) {
            // 先初始化商品主键id
            id = IdGeneratorUtil.nextStrId();
        } else {
            // 校验商品是否存在
            this.getSpu(id);
        }

        // 1、保存商品数据
        PmsSpu.builder()
                .id(id)
                .name(params.getName())
                .sort(params.getSort())
                .coverImg(params.getCoverImg())
                .slideImgList(params.getSlideImgList())
                .type(params.getType())
                .couponId(params.getCouponId())
                .couponName(params.getCouponName())
                .couponNum(params.getCouponNum())
                .detailImgList(params.getDetailImgList())
                .freight(params.getFreight())
//                .attrList(params.getAttrList())
                .isPut(params.getIsPut())
                .isShow(params.getIsShow())
                .linePrice(params.getLinePrice())
                .isPresell(isPresell)
                .presellStartTime(presellStartTime)
                .presellEndTime(params.getPresellEndTime())
                .presellDeliverDay(params.getPresellDeliverDay())
                .serviceList(params.getServiceList())
                .explainList(params.getExplainList())
                .build()
                .insertOrUpdate();

        // 2、保存商品关联的所有规格明细信息
        for (WebPmsSkuSaveDTO item : skuList) {
            // 保存商品规格下的商品id信息
            item.setSpuId(id);
        }
        this.saveBatchSkuForBusiness(skuList);

        // 3、mq处理预售通知延时任务
        if (isPresell) {
            this.spuPresellForMq(id, presellStartTime);
        }
        return id;
    }

    /**
     * 批量保存商品规格  --  有业务处理（ex: 库存处理）
     *
     * @param skuList 保存参数
     * @return void
     * @author zhengqingya
     * @date 2021/08/17 18:22
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveBatchSkuForBusiness(List<WebPmsSkuSaveDTO> skuList) {
        // 最终需要保存的sku
        List<PmsSku> saveSkuList = Lists.newLinkedList();
        // 根据spu分组处理sku
        Map<String, List<WebPmsSkuSaveDTO>> groupBySpu = skuList.stream().collect(Collectors.groupingBy(WebPmsSkuSaveDTO::getSpuId));
        groupBySpu.forEach((spuId, spuIdForSkuItemList) -> {
            // 旧sku数据
            List<PmsSku> mysqlSkuListOld = this.webPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));
            // sku-id -> sku信息
            Map<String, PmsSku> mysqlSkuMap = mysqlSkuListOld.stream()
                    .collect(
                            Collectors.toMap(e -> e.getId(), e -> e, (k1, k2) -> k1)
                    );
            // 旧sku-id
            List<String> mysqlSkuIdListOld = mysqlSkuListOld.stream().map(PmsSku::getId).collect(Collectors.toList());
            // 最新sku-id
            List<String> skuIdListNew = spuIdForSkuItemList.stream().map(WebPmsSkuSaveDTO::getSkuId).collect(Collectors.toList());

            // 需删除的sku数据
            List<String> mysqlRemoveSkuIdList = mysqlSkuIdListOld.stream().filter(skuIdOld -> !skuIdListNew.contains(skuIdOld)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(mysqlRemoveSkuIdList)) {
                log.info("[商城] 需要删除的规格ids：[{}]", mysqlRemoveSkuIdList);
                this.webPmsSkuService.removeByIds(mysqlRemoveSkuIdList);
            }

            spuIdForSkuItemList.forEach(item -> {
                String skuId = item.getSkuId();
                Integer totalStock = item.getTotalStock();
                Integer useStock = 0;
                Integer usableStock = 0;
                if (skuId == null) {
                    // 新增sku
                    skuId = IdGeneratorUtil.nextStrId();
                    usableStock = totalStock;
                } else {
                    // 更新sku
                    PmsSku mysqlSku = mysqlSkuMap.get(skuId);
                    Assert.notNull(mysqlSku, "旧sku数据丢失！");
                    useStock = mysqlSku.getUseStock();
                    Assert.isTrue(useStock <= totalStock, "使用库存已超过总库存！");
                    usableStock = totalStock - useStock;
                }
                // 封装保存数据
                saveSkuList.add(PmsSku.builder()
                        .id(skuId)
                        .spuId(item.getSpuId())
                        .code(item.getCode())
                        .specList(item.getSpecList())
                        .presellPrice(item.getPresellPrice())
                        .sellPrice(item.getSellPrice())
                        .limitCount(item.getLimitCount())
                        .coverImg(item.getCoverImg())
                        .isShow(true)
                        .totalStock(totalStock)
                        .usableStock(usableStock)
                        .useStock(useStock)
                        .sort(item.getSort())
                        .build());
            });
        });
        // 保存数据
        this.webPmsSkuService.batchInsertOrUpdate(saveSkuList);
    }

    /**
     * mq处理预售通知延时任务（tips:预售前5分钟通知）
     *
     * @param spuId            商品id
     * @param presellStartTime 预售开始时间
     * @return void
     * @author zhengqingya
     * @date 2022/1/13 15:20
     */
    private void spuPresellForMq(String spuId, Date presellStartTime) {
        if (presellStartTime.after(MyDateUtil.addTime(TimeUnit.MINUTES, 5))) {
            // 求预售时间前5分钟的时间
            Date noticeTime = MyDateUtil.timeAddAndSubTime(presellStartTime, TimeUnit.MINUTES, -5);
            long time = MyDateUtil.diffMillisecond(new Date(), noticeTime);
            this.rabbitTemplate.convertAndSend(MallRabbitMqConstant.MALL_EVENT_DELAY_EXCHANGE,
                    MallRabbitMqConstant.PMS_SPU_PRESELL_ROUTING_KEY,
                    PmsSpuPresellDTO.builder()
                            .tenantId(TenantIdContext.getTenantId())
                            .spuId(spuId)
                            .build(), message -> {
                        // 配置消息延时时间
                        message.getMessageProperties().setHeader("x-delay", time);
                        return message;
                    });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchPut(WebPmsSpuEditPutDTO params) {
        List<String> idList = params.getIdList();
        Boolean isPut = params.getIsPut();
        log.info("[商城] 批量更新商品上下架状态 商品ids:{} 是否上架：{}", idList, isPut);
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        this.pmsSpuMapper.updateBatchPut(idList, isPut);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchShow(WebPmsSpuEditShowDTO params) {
        List<String> idList = params.getIdList();
        Boolean isShow = params.getIsShow();
        log.info("[商城] 批量更新商品显示状态 商品ids:{} 是否显示：{}", idList, isShow);
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        this.pmsSpuMapper.updateBatchShow(idList, isShow);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchPresell(WebPmsSpuEditPresellDTO params) {
        log.info("[商城] 批量更新商品隐藏状态 提交参数:{}", params);
        List<String> idList = params.getIdList();
        Boolean isPresell = params.getIsPresell();
        Date presellStartTime = params.getPresellStartTime();
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        // 1、更新预售信息
        this.pmsSpuMapper.updateBatchPresell(params);
        // 2、mq处理预售通知延时任务
        if (isPresell) {
            idList.forEach(idItem -> this.spuPresellForMq(idItem, presellStartTime));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatchForBusiness(WebPmsSpuDeleteDTO params) {
        List<String> idList = params.getIdList();
        log.info("[商城] 批量删除商品 提交参数:{}", params);

        // 1、优惠券返回
        List<String> couponReSpuIdList = Lists.newArrayList(idList);
        try {
            this.reverseVirtualCouponStock(couponReSpuIdList);
        } catch (Exception e) {
            log.error("[商城] 删除商品时，退回优惠券库存失败：", e);
        }

        // 2、删除商品
        this.pmsSpuMapper.delete(new LambdaQueryWrapper<PmsSpu>().in(PmsSpu::getId, idList));

        // 3、删除商品关联规格
        this.webPmsSkuService.deleteDataBySpuIdList(idList);
    }

    @Override
    public void deleteBatch(List<String> spuIdList) {
        if (CollectionUtils.isEmpty(spuIdList)) {
            return;
        }
        // 1、删除商品数据
        this.pmsSpuMapper.deleteBatchIds(spuIdList);
        // 2、删除其商品规格数据
        this.webPmsSkuService.deleteDataBySpuIdList(spuIdList);
        // 3、删除关联分类绑定关系
        this.webPmsCategorySpuRelationService.deleteDataBySpuId(spuIdList);
    }

    /**
     * 优惠券库存返回
     *
     * @param idList 商品ids
     * @return void
     * @author zhengqingya
     * @date 2021/12/22 16:07
     */
    private void reverseVirtualCouponStock(List<String> idList) {
        // 记录含有优惠券类型的商品id数据
        List<String> spuIdReVirtualCouponList = Lists.newLinkedList();
        // 查询商品类型
        Map<String, PmsSpuTypeVO> spuTypeMap = this.mapSpuType(idList);
        spuTypeMap.forEach((spuId, spuInfo) -> {
            if (spuInfo.isVirtualCoupon()) {
                // 添加商品id
                spuIdReVirtualCouponList.add(spuId);
            }
        });
        if (CollectionUtils.isEmpty(spuIdReVirtualCouponList)) {
            return;
        }
        // 优惠券信息
        List<PmsSpuReCouponBO> couponInfoSaveList = Lists.newLinkedList();
        // 查询优惠券商品对应的库存
        Map<String, List<PmsSkuBO>> mapBySpuIdList = this.webPmsSkuService.mapBySpuId(
                spuIdReVirtualCouponList);
        // 循环带优惠券的商品
        spuIdReVirtualCouponList.forEach(spuIdItem -> {
            PmsSpuTypeVO spuTypeDataItem = spuTypeMap.get(spuIdItem);
            // 优惠券商品只会有一个sku
            PmsSkuBO pmsSkuBO = mapBySpuIdList.get(spuIdItem).get(0);
            couponInfoSaveList.add(PmsSpuReCouponBO.builder()
                    .couponId(spuTypeDataItem.getCouponId())
                    .couponName(spuTypeDataItem.getCouponName())
                    .couponNum(spuTypeDataItem.getCouponNum())
                    .usableStock(pmsSkuBO.getUsableStock())
                    .build());
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchSort(List<WebPmsSpuEditSortListDTO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        this.pmsSpuMapper.updateBatchSort(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchVirtualUseStock(List<WebPmsSpuEditVirtualUseStockDTO> list) {
        // 只要大于0的数据
        list = list.stream().filter(e -> e.getVirtualUseStock() > 0).collect(Collectors.toList());
        this.webPmsSkuService.updateBatchVirtualUseStock(list);
    }

}
