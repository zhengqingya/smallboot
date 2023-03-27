package com.zhengqing.mall.service.impl;

import com.zhengqing.mall.entity.PmsSku;
import com.zhengqing.mall.mapper.PmsSkuMapper;
import com.zhengqing.mall.service.MiniPmsSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> 商城-商品规格 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
@Slf4j
@Service
public class MiniPmsSkuServiceImpl extends PmsSkuServiceImpl<PmsSkuMapper, PmsSku> implements MiniPmsSkuService {

    @Resource
    private PmsSkuMapper pmsSkuMapper;


}
