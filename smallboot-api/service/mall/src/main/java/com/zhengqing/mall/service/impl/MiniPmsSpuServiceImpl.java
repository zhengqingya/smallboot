package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import com.zhengqing.common.base.context.TenantIdContext;
import com.zhengqing.common.redis.util.RedisUtil;
import com.zhengqing.mall.constant.MallRedisConstant;
import com.zhengqing.mall.entity.PmsSpu;
import com.zhengqing.mall.mapper.PmsSpuMapper;
import com.zhengqing.mall.model.bo.PmsSkuBO;
import com.zhengqing.mall.model.dto.MiniPmsSpuPageDTO;
import com.zhengqing.mall.model.dto.MiniPmsSpuPresellRemindDTO;
import com.zhengqing.mall.model.dto.PmsSpuPresellDTO;
import com.zhengqing.mall.model.vo.MiniPmsSpuDetailVO;
import com.zhengqing.mall.model.vo.MiniPmsSpuPageVO;
import com.zhengqing.mall.service.MiniPmsSkuService;
import com.zhengqing.mall.service.MiniPmsSpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class MiniPmsSpuServiceImpl extends PmsSpuServiceImpl<PmsSpuMapper, PmsSpu> implements MiniPmsSpuService {

    @Resource
    private PmsSpuMapper pmsSpuMapper;

    @Resource
    private MiniPmsSkuService miniPmsSkuService;

    @Override
    public IPage<MiniPmsSpuPageVO> page(MiniPmsSpuPageDTO params) {
        IPage<MiniPmsSpuPageVO> result = this.pmsSpuMapper.selectDataListByMini(new Page<>(), params);
        List<MiniPmsSpuPageVO> list = result.getRecords();
        if (!CollectionUtils.isEmpty(list)) {
            // 商品ids
            List<String> spuIdList = list.stream().map(MiniPmsSpuPageVO::getId).collect(Collectors.toList());
            // 查询关联规格数据
            Map<String, List<PmsSkuBO>> skuDataMap = this.miniPmsSkuService.mapBySpuId(spuIdList);
            // 封装数据
            list.forEach(item -> {
                String id = item.getId();
                List<PmsSkuBO> miniPmsSkuList = skuDataMap.get(id);
                item.setSkuList(miniPmsSkuList);
                // 处理数据
                item.handleData(miniPmsSkuList);
            });
        }
        return result;
    }

    @Override
    public MiniPmsSpuDetailVO detail(String spuId) {
        MiniPmsSpuDetailVO spuDetail = this.pmsSpuMapper.detailByMini(spuId);
        Assert.notNull(spuDetail, "该数据不存在！");
        // 查询关联规格数据
        List<PmsSkuBO> skuList = this.miniPmsSkuService.listBySpuId(spuId);
        spuDetail.setSkuList(skuList);
        // 处理数据
        spuDetail.handleData(skuList);
        // 实时获取关联服务和说明数据
//        this.handleSpuData(spuDetail);
        return spuDetail;
    }

    @Override
    public void presellRemind(MiniPmsSpuPresellRemindDTO params) {
        log.info("[商城] 预售提醒参数：{}", JSON.toJSONString(params));
        String spuId = params.getSpuId();
        String wxOpenid = params.getWxOpenid();
        Set<String> wxOpenidSaveSet = Sets.newHashSet(wxOpenid);
        /**
         * {@link MiniPmsSpuServiceImpl#presellRemindToSendUser}
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
        MiniPmsSpuDetailVO spuDetail = this.detail(spuId);
        if (!spuDetail.getIsPresell()) {
            log.warn("[商城] 预售提醒 参数：[{}] 已非预售状态 商品详情：[{}]", JSON.toJSONString(params), JSON.toJSONString(spuDetail));
            return;
        }
        /**
         * {@link MiniPmsSpuServiceImpl#presellRemind}
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

}
