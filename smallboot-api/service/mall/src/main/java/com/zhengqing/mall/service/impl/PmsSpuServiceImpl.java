package com.zhengqing.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.util.MyDateUtil;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.mall.constant.MallRabbitMqConstant;
import com.zhengqing.mall.constant.MallRedisConstant;
import com.zhengqing.mall.entity.PmsSku;
import com.zhengqing.mall.entity.PmsSpu;
import com.zhengqing.mall.enums.MallTabEnum;
import com.zhengqing.mall.mapper.PmsSpuMapper;
import com.zhengqing.mall.model.bo.MallDictBO;
import com.zhengqing.mall.model.bo.PmsSkuBO;
import com.zhengqing.mall.model.bo.PmsSkuStockBO;
import com.zhengqing.mall.model.bo.PmsSpuReCouponBO;
import com.zhengqing.mall.model.dto.*;
import com.zhengqing.mall.model.vo.*;
import com.zhengqing.mall.service.IMallCommonService;
import com.zhengqing.mall.service.IPmsCategorySpuRelationService;
import com.zhengqing.mall.service.IPmsSkuService;
import com.zhengqing.mall.service.IPmsSpuService;
import com.zhengqing.mall.util.MallSkuUtil;
import com.zhengqing.system.enums.SysDictTypeEnum;
import com.zhengqing.system.model.vo.SysDictVO;
import com.zhengqing.system.service.ISysDictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
@RequiredArgsConstructor
public class PmsSpuServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements IPmsSpuService {

    private final PmsSpuMapper pmsSpuMapper;
    private final IPmsSkuService iPmsSkuService;
    private final ISysDictService iSysDictService;
    private final IMallCommonService iMallCommonService;
    private final RabbitTemplate rabbitTemplate;
    @Lazy
    @Resource
    private IPmsCategorySpuRelationService iPmsCategorySpuRelationService;

    @Override
    public PmsSpu getSpu(String id) {
        PmsSpu pmsSpu = this.pmsSpuMapper.selectById(id);
        Assert.notNull(pmsSpu, "该商品不存在！");
        return pmsSpu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSkuStock(List<PmsSkuStockBO> skuList) {
        // 加锁
//        RLock redisLock = RedisUtil.lock(MallRedisConstant.SPU_SKU_STOCK_LOCK, 5, TimeUnit.SECONDS);
        try {
            return this.iPmsSkuService.updateSkuStock(skuList);
        } finally {
            // 释放锁
//            redisLock.unlock();
        }
    }

    @Override
    public void handleSpuData(PmsSpuBaseVO spuDetail) {
        List<String> serviceValueList = spuDetail.getServiceList().stream().map(MallDictBO::getValue).collect(Collectors.toList());
        List<String> explainValueList = spuDetail.getExplainList().stream().map(MallDictBO::getValue).collect(Collectors.toList());
        if (CollUtil.isEmpty(serviceValueList) && CollUtil.isEmpty(explainValueList)) {
            return;
        }
        // 查询服务和说明相关数据字典缓存
        List<String> codeList = Lists.newArrayList(SysDictTypeEnum.MALL_SPU_SERVICE.getCode(), SysDictTypeEnum.MALL_SPU_EXPLAIN.getCode());
        Map<String, List<SysDictVO>> dictDataMap = this.iSysDictService.listByOpenCode(codeList);
        // 服务
        if (CollUtil.isNotEmpty(serviceValueList)) {
            List<SysDictVO> dictListByService = dictDataMap.get(SysDictTypeEnum.MALL_SPU_SERVICE.getCode());
            List<SysDictVO> serviceDictList = dictListByService.stream().filter(e -> serviceValueList.contains(e.getValue())).collect(Collectors.toList());
            spuDetail.setServiceList(this.toDictBO(serviceDictList));
        }
        // 说明
        List<SysDictVO> dictListByExplain = dictDataMap.get(SysDictTypeEnum.MALL_SPU_EXPLAIN.getCode());
        if (CollUtil.isNotEmpty(explainValueList)) {
            List<SysDictVO> explainDictList = dictListByExplain.stream().filter(e -> explainValueList.contains(e.getValue())).collect(Collectors.toList());
            spuDetail.setExplainList(this.toDictBO(explainDictList));
        }
    }

    /**
     * 数据字典转换
     *
     * @param dictVOList 数据字典值
     * @return 转换后的值
     * @author zhengqingya
     * @date 2021/12/22 10:35 下午
     */
    private List<MallDictBO> toDictBO(List<SysDictVO> dictVOList) {
        List<MallDictBO> resultList = Lists.newLinkedList();
        if (CollUtil.isEmpty(dictVOList)) {
            return resultList;
        }
        dictVOList.forEach(item -> resultList.add(MallDictBO.builder()
                .code(item.getCode())
                .name(item.getName())
                .value(item.getValue())
                .sort(item.getSort())
                .remark(item.getRemark())
                .build()));
        return resultList;
    }

    @Override
    public List<PmsSkuVO> listBySku(PmsSkuDTO params) {
        List<String> skuIdList = params.getSkuIdList();
        if (CollUtil.isEmpty(skuIdList)) {
            return Lists.newArrayList();
        }
        List<PmsSkuVO> pmsSkuList = this.pmsSpuMapper.selectSkuList(params);
        if (!CollUtil.isEmpty(pmsSkuList)) {
            pmsSkuList.forEach(item -> item.handleData());
        }
        return pmsSkuList;
    }

    @Override
    public Map<String, PmsSkuVO> mapBySku(PmsSkuDTO params) {
        List<String> skuIdList = params.getSkuIdList();
        if (CollUtil.isEmpty(skuIdList)) {
            return Maps.newHashMap();
        }
        return this.listBySku(params).stream().collect(Collectors.toMap(PmsSkuVO::getSkuId, t -> t, (k1, k2) -> k1));
    }

    @Override
    public PmsSkuVO sku(String skuId) {
        return this.mapBySku(PmsSkuDTO.builder().skuIdList(Lists.newArrayList(skuId)).build()).get(skuId);
    }

    @Override
    public void presellRemind(MiniPmsSpuPresellRemindDTO params) {
        log.info("[商城] 预售提醒参数：{}", JSON.toJSONString(params));
        String spuId = params.getSpuId();
        String wxOpenid = params.getWxOpenid();
        Set<String> wxOpenidSaveSet = Sets.newHashSet(wxOpenid);
        /**
         * {@link PmsSpuServiceImpl#presellRemindToSendUser}
         * redis key: smallboot:mall:spu:presell_remind:1
         *       value-map: key->商品id
         *                  value->用户id集合
         */
        String key = MallRedisConstant.SPU_PRESELL_REMIND + TenantIdContext.getTenantId();
        // 1、查询旧数据
        Object wxOpenidJsonOld = RedisUtil.hGet(key, spuId);
        if (wxOpenidJsonOld != null) {
            // 2、加入旧用户
            List<String> wxOpenidSetOld = JSON.parseArray(wxOpenidJsonOld.toString(), String.class);
            wxOpenidSaveSet.addAll(wxOpenidSetOld);
        }
        // 3、储存新数据
        RedisUtil.hPut(key, spuId, JSON.toJSONString(wxOpenidSaveSet));
    }

    @Override
    public void presellRemindToSendUser(PmsSpuPresellDTO params) {
        log.info("[商城] 预售提醒 参数：[{}]", JSON.toJSONString(params));
        String spuId = params.getSpuId();
        // 1、查询商品详情
        PmsSpuBaseVO spuDetail = this.detail(spuId);
        if (!spuDetail.getIsPresell()) {
            log.warn("[商城] 预售提醒 参数：[{}] 已非预售状态 商品详情：[{}]", JSON.toJSONString(params), JSON.toJSONString(spuDetail));
            return;
        }
        /**
         * {@link PmsSpuServiceImpl#presellRemind}
         * redis key: smallboot:mall:spu:presell_remind:1
         *       value-map: key->商品id
         *                  value->用户id集合
         */
        String key = MallRedisConstant.SPU_PRESELL_REMIND + TenantIdContext.getTenantId();
        // 2、查询需要通知的用户
        Object wxOpenidJson = RedisUtil.hGet(key, spuId);
        if (wxOpenidJson == null) {
            log.warn("[商城] 预售提醒 参数：[{}] 暂无需要通知的用户 商品详情：[{}]", JSON.toJSONString(params), JSON.toJSONString(spuDetail));
            return;
        }
        // 3、删除此商品的预售通知人
        RedisUtil.hDelete(key, spuId);
    }

    @Override
    public List<MallTabConditionListVO> getTabCondition(PmsSpuPageDTO params) {
        params.setIsPut(null);
        // 查询tab条件数量
        List<MallTabConditionListVO> tabDataList = this.pmsSpuMapper.selectTabCondition(params);
        return this.iMallCommonService.getTabDataList(tabDataList, MallTabEnum.MALL_SPU_TAB_CONDITION);
    }

    @Override
    public IPage<PmsSpuBaseVO> page(PmsSpuPageDTO params) {
        IPage<PmsSpuBaseVO> result = this.pmsSpuMapper.selectDataList(new Page<>(), params);
        List<PmsSpuBaseVO> list = result.getRecords();
        if (CollUtil.isEmpty(list)) {
            return result;
        }
        // 商品ids
        List<String> spuIdList = list.stream().map(PmsSpuBaseVO::getId).collect(Collectors.toList());
        // 查询关联规格数据
        Map<String, List<PmsSkuBO>> skuDataMap = this.iPmsSkuService.mapBySpuId(spuIdList);
        for (PmsSpuBaseVO item : list) {
            String id = item.getId();
            List<PmsSkuBO> skuItemList = skuDataMap.get(id);
            item.setSkuList(skuItemList);
            // 实时获取关联服务和说明数据
//        this.handleSpuData(item);
            item.handleData(skuItemList);
        }
        return result;
    }

    @Override
    public List<WebPmsSpuListVO> list(PmsSpuListDTO params) {
        return this.pmsSpuMapper.selectListByWeb(params);
    }

    @Override
    public Map<String, PmsSpuTypeVO> mapSpuType(List<String> idList) {
        Map<String, PmsSpuTypeVO> resultMap = Maps.newHashMap();
        if (CollUtil.isEmpty(idList)) {
            return resultMap;
        }
        List<PmsSpuTypeVO> list = this.pmsSpuMapper.selectDataTypeList(idList);
        for (PmsSpuTypeVO item : list) {
            resultMap.put(item.getId(), item);
        }
        return resultMap;
    }

    @Override
    public PmsSpuBaseVO detail(String id) {
        return this.page(PmsSpuPageDTO.builder().id(id).build()).getRecords().get(0);
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
        // 最终需要删除的sku
        List<String> mysqlRemoveSkuIdList = Lists.newArrayList();
        // 根据spu分组处理sku
        Map<String, List<WebPmsSkuSaveDTO>> groupBySpu = skuList.stream().collect(Collectors.groupingBy(WebPmsSkuSaveDTO::getSpuId));
        groupBySpu.forEach((spuId, spuIdForSkuItemList) -> {
            /**
             * 通过sku属性值（蓝色,L）判断是 新增/更新 操作
             */

            // 1、准备数据
            // 旧sku数据
            List<PmsSku> mysqlSkuListOld = this.iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));
            // sku-id -> sku信息
            Map<String, PmsSku> mysqlSkuMap = mysqlSkuListOld.stream().collect(Collectors.toMap(e -> e.getId(), e -> e, (oldData, newData) -> newData));
            // sku属性值（蓝色,L） -> sku-id
            Map<String, String> mysqlSkuStrToIdMap = mysqlSkuListOld.stream().collect(Collectors.toMap(e -> MallSkuUtil.getSkuStr(spuId, e.getSpecList()), PmsSku::getId, (oldData, newData) -> newData));
            // 旧sku属性值 eg:[蓝色,L]
            List<String> mysqlSkuStrListOld = mysqlSkuListOld.stream().map(e -> MallSkuUtil.getSkuStr(spuId, e.getSpecList())).collect(Collectors.toList());

            // 最新sku属性值 eg:[蓝色,L]
            List<String> skuStrListNew = spuIdForSkuItemList.stream().map(e -> MallSkuUtil.getSkuStr(spuId, e.getSpecList())).collect(Collectors.toList());

            // 2、需删除的sku数据
            List<String> mysqlRemoveSkuList = mysqlSkuStrListOld.stream().filter(skuOld -> !skuStrListNew.contains(skuOld)).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(mysqlRemoveSkuList)) {
                mysqlRemoveSkuList.forEach(mysqlRemoveSku -> {
                    String mysqlRemoveSkuId = mysqlSkuStrToIdMap.get(mysqlRemoveSku);
                    mysqlRemoveSkuIdList.add(mysqlRemoveSkuId);
                });
            }

            // 3、需要保存的数据
            spuIdForSkuItemList.forEach(item -> {
                String skuStr = MallSkuUtil.getSkuStr(spuId, item.getSpecList());
                String skuId = mysqlSkuStrToIdMap.get(skuStr);
                Integer totalStock = item.getTotalStock();
                Integer useStock = 0;
                Integer usableStock = 0;

                if (StrUtil.isBlank(skuId)) {
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
                        .isShow(item.getIsShow())
                        .totalStock(totalStock)
                        .usableStock(usableStock)
                        .useStock(useStock)
                        .sort(item.getSort())
                        .build());
            });
        });

        // db操作
        this.iPmsSkuService.removeByIds(mysqlRemoveSkuIdList);
        this.iPmsSkuService.batchInsertOrUpdate(saveSkuList);
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
        if (CollUtil.isEmpty(idList)) {
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
        if (CollUtil.isEmpty(idList)) {
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
        if (CollUtil.isEmpty(idList)) {
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
        this.iPmsSkuService.deleteDataBySpuIdList(idList);
    }

    @Override
    public void deleteBatch(List<String> spuIdList) {
        if (CollUtil.isEmpty(spuIdList)) {
            return;
        }
        // 1、删除商品数据
        this.pmsSpuMapper.deleteBatchIds(spuIdList);
        // 2、删除其商品规格数据
        this.iPmsSkuService.deleteDataBySpuIdList(spuIdList);
        // 3、删除关联分类绑定关系
        this.iPmsCategorySpuRelationService.deleteDataBySpuId(spuIdList);
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
        if (CollUtil.isEmpty(spuIdReVirtualCouponList)) {
            return;
        }
        // 优惠券信息
        List<PmsSpuReCouponBO> couponInfoSaveList = Lists.newLinkedList();
        // 查询优惠券商品对应的库存
        Map<String, List<PmsSkuBO>> mapBySpuIdList = this.iPmsSkuService.mapBySpuId(
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
        if (CollUtil.isEmpty(list)) {
            return;
        }
        this.pmsSpuMapper.updateBatchSort(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchVirtualUseStock(List<WebPmsSpuEditVirtualUseStockDTO> list) {
        this.iPmsSkuService.updateBatchVirtualUseStock(list);
    }


}
