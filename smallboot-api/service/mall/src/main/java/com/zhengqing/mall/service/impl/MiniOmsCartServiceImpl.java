package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.base.context.UmsUserContext;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.common.redis.util.RedissonUtil;
import com.zhengqing.mall.common.model.bo.PmsSpuBuyNumInfoBO;
import com.zhengqing.mall.common.model.dto.PmsSkuDTO;
import com.zhengqing.mall.common.model.vo.PmsSkuVO;
import com.zhengqing.mall.constant.MallRedisConstant;
import com.zhengqing.mall.mini.model.bo.MiniOmsCartBO;
import com.zhengqing.mall.mini.model.dto.MiniOmsCartDeleteDTO;
import com.zhengqing.mall.mini.model.dto.MiniOmsCartSaveDTO;
import com.zhengqing.mall.mini.model.dto.MiniOmsCartUpdateNumDTO;
import com.zhengqing.mall.mini.model.vo.MiniOmsCartVO;
import com.zhengqing.mall.service.MallCommonService;
import com.zhengqing.mall.service.MiniOmsCartService;
import com.zhengqing.mall.service.MiniOmsOrderItemService;
import com.zhengqing.mall.service.MiniPmsSpuService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p> 商城-购物车 服务实现类 </p>
 *
 * @author zhengqingya
 * @description 购物车redis存储设计：
 * 1. key   -> 租户ID+用户ID
 * 2. field -> 商品规格属性ID
 * 3. value -> 商品信息
 * @date 2021/08/17 18:22
 */
@Slf4j
@Service
public class MiniOmsCartServiceImpl implements MiniOmsCartService {

    @Resource
    private MiniPmsSpuService miniPmsSpuService;

    @Resource
    private MiniOmsOrderItemService miniOmsOrderItemService;

    @Resource
    private MallCommonService mallCommonService;

    @Override
    public List<MiniOmsCartVO> list(Long userId) {
        log.info("[商城] 购物车-查询-用户id：[{}]", userId);
        // 1、查询redis中存储的数据
        Map<Object, Object> redisCartMap = RedisUtil.hGetAll(this.getCartKey(userId));
        if (CollectionUtils.isEmpty(redisCartMap)) {
            return Lists.newArrayList();
        }

        // 2、获取sku-id
        List<String> skuIdList = Lists.newLinkedList();
        for (Map.Entry<Object, Object> entry : redisCartMap.entrySet()) {
            skuIdList.add((String) entry.getKey());
        }

        // 3、查询mysql中关联商品sku数据
        Map<String, PmsSkuVO> mysqlSkuMap = this.miniPmsSpuService.mapBySku(
                PmsSkuDTO.builder()
                        .skuIdList(skuIdList)
                        .build());

        // 4、最后组装数据返回
        List<MiniOmsCartVO> resultList = Lists.newLinkedList();
        for (Map.Entry<Object, Object> entry : redisCartMap.entrySet()) {
            // redis缓存数据
            Object skuId = entry.getKey();
            Object cartRedisData = entry.getValue();
            MiniOmsCartBO cartRedis = JSON.parseObject(cartRedisData.toString(), MiniOmsCartBO.class);
            MiniOmsCartVO cartItem = MiniOmsCartVO.builder()
                    .userId(userId)
                    .spuId(cartRedis.getSpuId())
                    .skuId(cartRedis.getSkuId())
                    .num(cartRedis.getNum())
                    .time(cartRedis.getTime())
                    .build();
            // mysql实时数据
            PmsSkuVO mysqlSku = mysqlSkuMap.get(skuId);
            if (mysqlSku == null) {
                cartItem.setIsLose(true);
            } else {
                cartItem.setIsLose(false);
                // 补充商品其它实时同步数据
                cartItem.setSpecList(mysqlSku.getSpecList());
                cartItem.setName(mysqlSku.getName());
                cartItem.setCoverImg(mysqlSku.getCoverImg());
                cartItem.setPrice(mysqlSku.getPrice());
                cartItem.setUsableStock(mysqlSku.getUsableStock());
                cartItem.setLimitCount(mysqlSku.getLimitCount());
                cartItem.setFreight(mysqlSku.getFreight());
            }
            cartItem.handleData();
            resultList.add(cartItem);
        }
        // 5、根据时间降序，最新的数据放最前面
        resultList = resultList.stream().sorted(Comparator.comparing(MiniOmsCartVO::getTime, Comparator.reverseOrder())).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public void addData(MiniOmsCartSaveDTO params) {
        log.info("[商城] 购物车-保存参数：[{}]", params);
        Long userId = UmsUserContext.getUserId();
        String spuId = params.getSpuId();
        String skuId = params.getSkuId();
        Integer num = params.getNum();
        // sku校验
        this.checkSkuData(skuId, num, userId);
        // 保存数据
        this.saveCartData(userId, spuId, skuId, num);
    }

    /**
     * 校验sku 库存和限购
     *
     * @param skuId  商品sku-id
     * @param num    商品购买数
     * @param userId 用户id
     * @return void
     * @author zhengqingya
     * @date 2021/12/22 11:04
     */
    private void checkSkuData(String skuId, Integer num, Long userId) {
        // 查询商品sku数据
        PmsSkuVO mysqlSku = this.miniPmsSpuService.sku(skuId);
        Assert.notNull(mysqlSku, "购买商品已失效！");
        Assert.isTrue(mysqlSku.getUsableStock() - num >= 0, "商品库存不足");
        // 校验此人历史是否限购
        Map<String, Integer> mysqlSkuHistoryLimitMap = this.miniOmsOrderItemService.mapSkuLimit(userId, Lists.newArrayList(skuId));
        this.mallCommonService.checkSkuLimit(Lists.newArrayList(PmsSpuBuyNumInfoBO.builder()
                        .skuId(skuId)
                        .num(num)
                        .build()),
                new HashMap<String, PmsSkuVO>(1) {
                    {
                        this.put(skuId, mysqlSku);
                    }
                }, mysqlSkuHistoryLimitMap);
    }


    public void saveCartData(Long userId, String spuId, String skuId, Integer num) {
        String cartKey = this.getCartKey(userId);
        // 加锁
        RLock redisLock = RedissonUtil.lock(String.format("%s%s:%s", MallRedisConstant.MINI_CART_LOCK, TenantIdContext.getTenantId(), userId), 5, TimeUnit.SECONDS);
        try {
            // 查询之前购物车中的数据
            Object cartData = RedisUtil.hGet(cartKey, skuId);
            if (cartData != null) {
                // 如果之前加入过购物车，则数量做累计
                MiniOmsCartBO cartBO = JSON.parseObject(cartData.toString(), MiniOmsCartBO.class);
                num = cartBO.getNum() + num;
            }
            if (num <= 0) {
                // 删除操作
                RedisUtil.hDelete(cartKey, skuId);
                return;
            }
            // 更新操作
            RedisUtil.hPut(cartKey, skuId,
                    JSON.toJSONString(MiniOmsCartBO.builder()
                            .userId(userId)
                            .spuId(spuId)
                            .skuId(skuId)
                            .num(num)
                            .time(new Date())
                            .build()));
        } finally {
            // 释放锁
            redisLock.unlock();
        }
    }

    @Override
    public void updateNum(MiniOmsCartUpdateNumDTO params) {
        log.info("[商城] 购物车-更新数量：[{}]", params);
        // 保存数据
        this.saveCartData(UmsUserContext.getUserId(), params.getSpuId(), params.getSkuId(), params.getNum());
    }


    @Override
    public void deleteData(MiniOmsCartDeleteDTO params) {
        log.info("[商城] 购物车-删除-提交参数：[{}]", params);
        String cartKey = this.getCartKey(UmsUserContext.getUserId());
        List<String> skuIdList = params.getSkuIdList();
        skuIdList.forEach(skuIdItem -> RedisUtil.hDelete(cartKey, skuIdItem));
    }

    /**
     * 获取个人用户购物车key
     *
     * @param userId 用户id
     * @return redis-key
     * @author zhengqingya
     * @date 2021/10/12 15:09
     */
    private String getCartKey(Long userId) {
        return String.format("%s%s:%s", MallRedisConstant.MINI_CART, TenantIdContext.getTenantId(), userId);
    }

}
