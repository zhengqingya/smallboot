package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.mall.constant.MallAppConstant;
import com.zhengqing.mall.model.bo.PmsSpuBuyNumInfoBO;
import com.zhengqing.mall.enums.MallResultCodeEnum;
import com.zhengqing.mall.enums.OmsOrderAfterSaleTabEnum;
import com.zhengqing.mall.enums.PmsSpuTabEnum;
import com.zhengqing.mall.enums.WebOmsOrderTabEnum;
import com.zhengqing.mall.model.vo.MallTabConditionListVO;
import com.zhengqing.mall.model.vo.PmsSkuVO;
import com.zhengqing.mall.service.IMallCommonService;
import com.zhengqing.system.enums.SysConfigKeyEnum;
import com.zhengqing.system.enums.SysDictTypeEnum;
import com.zhengqing.system.model.dto.SysConfigSaveDTO;
import com.zhengqing.system.model.dto.SysDictSaveBatchDTO;
import com.zhengqing.system.service.ISysConfigService;
import com.zhengqing.system.service.ISysDictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 商城-common 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/8/30 19:21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MallCommonServiceImpl implements IMallCommonService {

    private final ISysDictService iSysDictService;
    private final ISysConfigService iSysConfigService;

    @Override
    public long getAutoReceiptMillisecond() {
        return MallAppConstant.AUTO_RECEIPT_MILLISECOND;
    }

    @Override
    public Date getAutoReceiptTime() {
        long autoReceiveMillisecond = this.getAutoReceiptMillisecond();
        return new Date(System.currentTimeMillis() + autoReceiveMillisecond);
    }

    @Override
    public List<MallTabConditionListVO> getTabDataList(List<MallTabConditionListVO> tabDataList, SysDictTypeEnum dictTypeEnum) {
        int sumNum = tabDataList.stream().mapToInt(MallTabConditionListVO::getNum).sum();
        Map<Byte, Integer> tabDataMap = tabDataList.stream()
                .collect(
                        Collectors.toMap(MallTabConditionListVO::getValue,
                                MallTabConditionListVO::getNum,
                                (k1, k2) -> k1)
                );
        List<MallTabConditionListVO> dictListByTab = this.getTabList(dictTypeEnum);
        List<MallTabConditionListVO> resultList = Lists.newLinkedList();
        for (MallTabConditionListVO item : dictListByTab) {
            Byte value = item.getValue();
            Integer num = tabDataMap.get(value);
            // -1：全部
            if (value == -1) {
                num = sumNum;
            }
            item.setNum(num == null ? 0 : num);
            resultList.add(item);
        }
        // 根据sort升序
        resultList = resultList.stream().sorted(Comparator.comparing(MallTabConditionListVO::getSort)).collect(Collectors.toList());
        return resultList;
    }

    /**
     * 根据字典类型获取tab条件list
     *
     * @param dictTypeEnum 字典类型
     * @return tab条件list
     * @author zhengqingya
     * @date 2021/10/25 14:23
     */
    private List<MallTabConditionListVO> getTabList(SysDictTypeEnum dictTypeEnum) {
        List<MallTabConditionListVO> tabList = Lists.newLinkedList();
        switch (dictTypeEnum) {
            case MALL_SPU_TAB_CONDITION:
                for (PmsSpuTabEnum spuTabEnum : PmsSpuTabEnum.LIST) {
                    tabList.add(MallTabConditionListVO.builder().
                            sort(spuTabEnum.getTabSort())
                            .name(spuTabEnum.getName())
                            .value(spuTabEnum.getValue())
                            .build());
                }
                break;
            case MALL_ORDER_TAB_CONDITION_WEB:
                for (WebOmsOrderTabEnum orderTabEnum : WebOmsOrderTabEnum.LIST) {
                    tabList.add(MallTabConditionListVO.builder().
                            sort(orderTabEnum.getTabSort())
                            .name(orderTabEnum.getName())
                            .value(orderTabEnum.getValue())
                            .build());
                }
                break;
            case MALL_ORDER_AFTER_SALE_TAB_CONDITION:
                for (OmsOrderAfterSaleTabEnum afterSaleTabEnum : OmsOrderAfterSaleTabEnum.LIST) {
                    tabList.add(MallTabConditionListVO.builder().
                            sort(afterSaleTabEnum.getTabSort())
                            .name(afterSaleTabEnum.getName())
                            .value(afterSaleTabEnum.getValue())
                            .build());
                }
                break;
            default:
                throw new MyException(MallResultCodeEnum.暂无此tab条件数据.getDesc());
        }
        return tabList;
    }

    @Override
    public void initData() {
        // 初始化商品设置数据
        this.initSpuData();
        // 初始化订单设置数据
        this.initOrderSetData();
    }

    @Override
    public void checkSkuLimit(List<PmsSpuBuyNumInfoBO> skuBuyInfoList,
                              Map<String, PmsSkuVO> skuInfoMap,
                              Map<String, Integer> skuBuyHistoryInfoMap) {
        // 校验此人历史是否限购
        // 每人限购
        for (PmsSpuBuyNumInfoBO spuItem : skuBuyInfoList) {
            String skuId = spuItem.getSkuId();
            Integer num = spuItem.getNum();
            PmsSkuVO mysqlSku = skuInfoMap.get(skuId);
            if (mysqlSku.getIsLimit()) {
                Integer historyNum = skuBuyHistoryInfoMap.get(skuId);
                Integer limitCount = mysqlSku.getLimitCount();
                Assert.isTrue(num + historyNum <= limitCount, String.format("[%s]限购数量: %s", mysqlSku.getName(), limitCount));
            }
        }
    }


    /**
     * 初始化商品设置数据
     *
     * @author zhengqingya
     * @date 2021/9/24 15:43
     */
    private void initSpuData() {
        Map<String, ValidList<SysDictSaveBatchDTO>> saveDictDataMap = Maps.newHashMap();
        // 商品-品类大标题
        String codeForTitle = SysDictTypeEnum.MALL_SPU_CATEGORY_BIG_TITLE.getCode();
        String descForTitle = SysDictTypeEnum.MALL_SPU_CATEGORY_BIG_TITLE.getDesc();
        ValidList<SysDictSaveBatchDTO> titleList = new ValidList<>();
        titleList.add(SysDictSaveBatchDTO.builder().code(codeForTitle).dictTypeName(descForTitle).name("商品详情").value("商品详情").sort(1).build());
        titleList.add(SysDictSaveBatchDTO.builder().code(codeForTitle).dictTypeName(descForTitle).name("商品说明").value("商品说明").sort(2).build());
        saveDictDataMap.put(codeForTitle, titleList);

        // 商品-按钮文案
        String codeForButton = SysDictTypeEnum.MALL_SPU_BUTTON_DOC.getCode();
        String descForButton = SysDictTypeEnum.MALL_SPU_BUTTON_DOC.getDesc();
        ValidList<SysDictSaveBatchDTO> buttonList = new ValidList<>();
        buttonList.add(SysDictSaveBatchDTO.builder().code(codeForButton).dictTypeName(descForButton).name("立即购买").value("立即购买").sort(1).build());
        buttonList.add(SysDictSaveBatchDTO.builder().code(codeForButton).dictTypeName(descForButton).name("加入购物车").value("加入购物车").sort(2).build());
        buttonList.add(SysDictSaveBatchDTO.builder().code(codeForButton).dictTypeName(descForButton).name("预售提醒").value("预售提醒").sort(3).build());
        saveDictDataMap.put(codeForButton, buttonList);

        // 商品-服务
        String codeForService = SysDictTypeEnum.MALL_SPU_SERVICE.getCode();
        String descForService = SysDictTypeEnum.MALL_SPU_SERVICE.getDesc();
        ValidList<SysDictSaveBatchDTO> serviceList = new ValidList<>();
        serviceList.add(SysDictSaveBatchDTO.builder().code(codeForService).dictTypeName(descForService).name("服务标题一").value("这里是服务内容").sort(1).build());
        saveDictDataMap.put(codeForService, serviceList);

        // 商品-说明
        String codeForExplain = SysDictTypeEnum.MALL_SPU_EXPLAIN.getCode();
        String descForExplain = SysDictTypeEnum.MALL_SPU_EXPLAIN.getDesc();
        ValidList<SysDictSaveBatchDTO> explainList = new ValidList<>();
        explainList.add(SysDictSaveBatchDTO.builder().code(codeForExplain).dictTypeName(descForExplain).name("说明标题一").value("这里是说明内容").sort(1).build());
        saveDictDataMap.put(codeForExplain, explainList);

        // 保存
        this.iSysDictService.addOrUpdateBatch(saveDictDataMap, true);
    }

    /**
     * 初始化订单设置数据
     *
     * @author zhengqingya
     * @date 2021/9/24 15:43
     */
    private void initOrderSetData() {
        // 订单-设置
        ValidList<SysConfigSaveDTO> dataList = new ValidList<>();
        SysConfigKeyEnum.LIST_MALL_ORDER_SET.forEach(item -> dataList.add(SysConfigSaveDTO.builder().key(item.getKey()).value(item.getValue()).remark(item.getDesc()).build()));
        this.iSysConfigService.saveBatch(dataList);

        // 订单-发货微信消息通知
        String codeForMsg = SysDictTypeEnum.MALL_ORDER_DELIVER_WX_MSG_NOTICE.getCode();
        String descForMsg = SysDictTypeEnum.MALL_ORDER_DELIVER_WX_MSG_NOTICE.getDesc();
        Map<String, ValidList<SysDictSaveBatchDTO>> saveDictDataMap = Maps.newHashMap();
        ValidList<SysDictSaveBatchDTO> msgList = new ValidList<>();
        msgList.add(SysDictSaveBatchDTO.builder().code(codeForMsg).dictTypeName(descForMsg)
                .name("亲，您购买的商品已经发货了，请注意查收！")
                .value("亲，您购买的商品已经发货了，请注意查收！")
                .sort(1)
                .build());
        saveDictDataMap.put(SysDictTypeEnum.MALL_ORDER_DELIVER_WX_MSG_NOTICE.getCode(), msgList);
        this.iSysDictService.addOrUpdateBatch(saveDictDataMap, true);
    }

}
