package com.zhengqing.mall.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.mall.entity.PmsAttrValue;
import com.zhengqing.mall.mapper.PmsAttrValueMapper;
import com.zhengqing.mall.service.PmsAttrValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> 商城-属性value 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/22 15:06
 */
@Slf4j
@Service
public class PmsAttrValueServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<PmsAttrValueMapper, PmsAttrValue> implements PmsAttrValueService<PmsAttrValue> {

    @Resource
    private PmsAttrValueMapper pmsAttrValueMapper;


}
