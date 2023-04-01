package com.zhengqing.mall.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.zhengqing.common.core.util.IdGeneratorUtil;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.mall.entity.PmsAttrKey;
import com.zhengqing.mall.mapper.PmsAttrKeyMapper;
import com.zhengqing.mall.model.dto.WebPmsAttrKeyListDTO;
import com.zhengqing.mall.model.dto.WebPmsAttrSaveDTO;
import com.zhengqing.mall.model.vo.WebPmsAttrKeyListVO;
import com.zhengqing.mall.model.vo.WebPmsAttrVO;
import com.zhengqing.mall.service.WebPmsAttrKeyService;
import com.zhengqing.mall.service.WebPmsAttrValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 商城-属性key 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
@Slf4j
@Service
public class WebPmsAttrKeyServiceImpl extends PmsAttrKeyServiceImpl<PmsAttrKeyMapper, PmsAttrKey> implements WebPmsAttrKeyService {

    @Resource
    private PmsAttrKeyMapper pmsAttrKeyMapper;

    @Resource
    private WebPmsAttrValueService webPmsAttrValueService;


    @Override
    public List<WebPmsAttrKeyListVO> list(WebPmsAttrKeyListDTO params) {
        return this.pmsAttrKeyMapper.selectDataList(params);
    }

    @Override
    public List<WebPmsAttrVO> listByIdList(List<String> idList) {
        List<WebPmsAttrVO> resultList = Lists.newLinkedList();
        List<WebPmsAttrVO> list = this.pmsAttrKeyMapper.selectDataListByIdList(idList);
        // 通过指定属性id分组
        Map<String, List<WebPmsAttrVO>> groupByStatus = list.stream().collect(Collectors.groupingBy(WebPmsAttrVO::getAttrKeyId));
        for (Map.Entry<String, List<WebPmsAttrVO>> entry : groupByStatus.entrySet()) {
            String attrKeyId = entry.getKey();
            List<WebPmsAttrVO> attrValueList = entry.getValue();
            List<WebPmsAttrVO.AttrValueItem> attrValueItemList = Lists.newLinkedList();
            if (!CollectionUtils.isEmpty(attrValueList)) {
                for (WebPmsAttrVO item : attrValueList) {
                    String attrValueIdItem = item.getAttrValueId();
                    if (attrValueIdItem != null) {
                        attrValueItemList.add(WebPmsAttrVO.AttrValueItem.builder()
                                .attrValueId(attrValueIdItem)
                                .attrValueName(item.getAttrValueName())
                                .attrValueSort(item.getAttrValueSort())
                                .build());
                    }
                }
            }
            // 根据sort升序
            attrValueItemList = attrValueItemList.stream().sorted(Comparator.comparing(WebPmsAttrVO.AttrValueItem::getAttrValueSort)).collect(Collectors.toList());
            WebPmsAttrVO webPmsAttrVO = attrValueList.get(0);
            resultList.add(WebPmsAttrVO.builder()
                    .attrKeyId(attrKeyId)
                    .attrKeyName(webPmsAttrVO.getAttrKeyName())
                    .attrKeySort(webPmsAttrVO.getAttrKeySort())
                    .attrValueList(attrValueItemList)
                    .build());
        }
        // 根据sort升序
        resultList = resultList.stream().sorted(Comparator.comparing(WebPmsAttrVO::getAttrKeySort)).collect(Collectors.toList());
        return resultList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addOrUpdateData(WebPmsAttrSaveDTO params) {
        String id = params.getId();
        String attrKeyName = params.getAttrKeyName();
        Integer sort = params.getSort();

        // 校验名称是否重复
        PmsAttrKey pmsAttrKeyOld = this.pmsAttrKeyMapper.selectOne(new LambdaQueryWrapper<PmsAttrKey>()
                .eq(PmsAttrKey::getAttrKeyName, attrKeyName)
                .last(MybatisConstant.LIMIT_ONE));
        Assert.isTrue(pmsAttrKeyOld == null || pmsAttrKeyOld.getId().equals(id), "名称重复，请重新输入！");

        // 保存数据
        PmsAttrKey pmsAttrKey = PmsAttrKey.builder()
                .id(id)
                .attrKeyName(attrKeyName)
                .sort(sort)
                .build();

        if (id == null) {
            // 新增
            id = IdGeneratorUtil.nextStrId();
            pmsAttrKey.setId(id);
            pmsAttrKey.insert();
        } else {
            // 更新
            pmsAttrKey.updateById();

            // FIXME 关联商品价格和库存清0
        }
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(String id) {
        // 1、删除关联属性value
        this.webPmsAttrValueService.deleteDataByAttrKeyId(id);
        // 2、删除此属性key
        this.pmsAttrKeyMapper.deleteById(id);
    }

}
