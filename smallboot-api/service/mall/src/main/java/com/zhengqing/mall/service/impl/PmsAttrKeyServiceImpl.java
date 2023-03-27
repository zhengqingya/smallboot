package com.zhengqing.mall.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.mall.entity.PmsAttrKey;
import com.zhengqing.mall.mapper.PmsAttrKeyMapper;
import com.zhengqing.mall.service.PmsAttrKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p> 商城-属性key 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/20 17:38
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PmsAttrKeyServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<PmsAttrKeyMapper, PmsAttrKey> implements PmsAttrKeyService<PmsAttrKey> {

    @Resource
    private PmsAttrKeyMapper pmsAttrKeyMapper;


}
