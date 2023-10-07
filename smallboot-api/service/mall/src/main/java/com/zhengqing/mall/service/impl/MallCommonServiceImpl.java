package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.mall.constant.MallAppConstant;
import com.zhengqing.mall.enums.*;
import com.zhengqing.mall.model.bo.PmsSpuBuyNumInfoBO;
import com.zhengqing.mall.model.vo.MallTabConditionListVO;
import com.zhengqing.mall.model.vo.PmsSkuVO;
import com.zhengqing.mall.service.IMallCommonService;
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
    public List<MallTabConditionListVO> getTabDataList(List<MallTabConditionListVO> tabDataList, MallTabEnum tabEnum) {
        int sumNum = tabDataList.stream().mapToInt(MallTabConditionListVO::getNum).sum();
        Map<Byte, Integer> tabDataMap = tabDataList.stream()
                .collect(
                        Collectors.toMap(MallTabConditionListVO::getValue,
                                MallTabConditionListVO::getNum,
                                (k1, k2) -> k1)
                );
        List<MallTabConditionListVO> dictListByTab = this.getTabList(tabEnum);
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
     * @param tabEnum 类型
     * @return tab条件list
     * @author zhengqingya
     * @date 2021/10/25 14:23
     */
    private List<MallTabConditionListVO> getTabList(MallTabEnum tabEnum) {
        List<MallTabConditionListVO> tabList = Lists.newLinkedList();
        switch (tabEnum) {
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

}
