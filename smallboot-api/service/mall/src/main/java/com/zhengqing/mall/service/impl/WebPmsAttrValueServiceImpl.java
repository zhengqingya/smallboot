package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.mall.entity.PmsAttrValue;
import com.zhengqing.mall.mapper.PmsAttrValueMapper;
import com.zhengqing.mall.model.dto.WebPmsAttrValueListDTO;
import com.zhengqing.mall.model.dto.WebPmsAttrValueSaveDTO;
import com.zhengqing.mall.model.vo.WebPmsAttrValueListVO;
import com.zhengqing.mall.service.WebPmsAttrValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 商城-属性value 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/22 15:06
 */
@Slf4j
@Service
public class WebPmsAttrValueServiceImpl extends PmsAttrValueServiceImpl<PmsAttrValueMapper, PmsAttrValue> implements WebPmsAttrValueService {

    @Resource
    private PmsAttrValueMapper pmsAttrValueMapper;


    @Override
    public List<WebPmsAttrValueListVO> list(WebPmsAttrValueListDTO params) {
        return this.pmsAttrValueMapper.selectDataList(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addOrUpdateData(WebPmsAttrValueSaveDTO params) {
        String id = params.getId();
        String attrKeyId = params.getAttrKeyId();
        String attrValueName = params.getAttrValueName();
        Integer sort = params.getSort();

        // 校验名称是否重复
        PmsAttrValue pmsAttrValueOld = this.pmsAttrValueMapper.selectOne(new LambdaQueryWrapper<PmsAttrValue>()
                .eq(PmsAttrValue::getAttrKeyId, attrKeyId)
                .eq(PmsAttrValue::getAttrValueName, attrValueName)
                .last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(pmsAttrValueOld == null || pmsAttrValueOld.getId().equals(id), "名称重复，请重新输入！");

        PmsAttrValue pmsAttrValue = PmsAttrValue.builder()
                .id(id)
                .attrKeyId(attrKeyId)
                .attrValueName(attrValueName)
                .sort(sort)
                .build();

        if (id == null) {
            // 新增
            id = IdGeneratorUtil.nextStrId();
            pmsAttrValue.setId(id);
            pmsAttrValue.insert();
        } else {
            // 更新
            pmsAttrValue.updateById();

            // FIXME 关联商品价格和库存清0
        }
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(String id) {
        this.pmsAttrValueMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDataByAttrKeyId(String attrKeyId) {
        this.pmsAttrValueMapper.delete(new LambdaQueryWrapper<PmsAttrValue>()
                .eq(PmsAttrValue::getAttrKeyId, attrKeyId));
    }

}
