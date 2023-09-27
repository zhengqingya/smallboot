package com.zhengqing.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.common.db.constant.MybatisConstant;
import com.zhengqing.system.entity.SysProvinceCityArea;
import com.zhengqing.system.enums.SysProvinceCityAreaTypeEnum;
import com.zhengqing.system.mapper.SysProvinceCityAreaMapper;
import com.zhengqing.system.model.dto.SysProvinceCityAreaBindReShopDTO;
import com.zhengqing.system.model.dto.SysProvinceCityAreaTreeDTO;
import com.zhengqing.system.model.vo.SysProvinceCityAreaTreeVO;
import com.zhengqing.system.service.ISysProvinceCityAreaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> 系统管理-省市区 服务实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/14 11:38
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysProvinceCityAreaServiceImpl extends ServiceImpl<SysProvinceCityAreaMapper, SysProvinceCityArea> implements ISysProvinceCityAreaService {

    private final SysProvinceCityAreaMapper sysProvinceCityAreaMapper;

    @Override
    public List<SysProvinceCityAreaTreeVO> tree(SysProvinceCityAreaTreeDTO params) {
        List<SysProvinceCityAreaTreeVO> list = this.sysProvinceCityAreaMapper.selectDataList(params);
        List<SysProvinceCityAreaTreeVO> result = list.stream().filter(e -> e.getType().equals(params.getType())).collect(Collectors.toList());
        result.forEach(item -> item.setChildren(this.recurveTree(item.getId(), list)));
        return result;
    }

    /**
     * 递归树
     *
     * @param parentId 父ID
     * @param allList  所有省市区数据
     * @return 树列表
     * @author zhengqingya
     */
    private List<SysProvinceCityAreaTreeVO> recurveTree(Integer parentId, List<SysProvinceCityAreaTreeVO> allList) {
        List<SysProvinceCityAreaTreeVO> list = allList.stream().filter(e -> parentId.equals(e.getParentId())).collect(Collectors.toList());
        for (SysProvinceCityAreaTreeVO item : list) {
            item.setChildren(this.recurveTree(item.getId(), allList));
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindReShop(SysProvinceCityAreaBindReShopDTO params) {
        Boolean isShop = params.getIsShop();
        String provinceName = params.getProvinceName();
        String cityName = params.getCityName();
        String areaName = params.getAreaName();
        ArrayList<String> nameList = Lists.newArrayList(provinceName, cityName, areaName);

        List<SysProvinceCityArea> provinceCityAreaList = this.sysProvinceCityAreaMapper.selectList(
                new LambdaQueryWrapper<SysProvinceCityArea>()
                        .in(SysProvinceCityArea::getName, nameList)
        );

        Assert.notEmpty(provinceCityAreaList, areaName + "不存在！");


        provinceCityAreaList.forEach(e -> {
            Integer id = e.getId();
            switch (SysProvinceCityAreaTypeEnum.getEnum(e.getType())) {
                case PROVINCE:
                    e.setIsShop(this.isShop(isShop, id, SysProvinceCityAreaTypeEnum.CITY, nameList));
                    break;
                case CITY:
                    e.setIsShop(this.isShop(isShop, id, SysProvinceCityAreaTypeEnum.AREA, nameList));
                    break;
                case AREA:
                    e.setIsShop(isShop);
                    break;
                default:
                    break;
            }
        });

        super.updateBatchById(provinceCityAreaList);
    }

    /**
     * 判断关联省市区下是否存在关联门店数据
     *
     * @param isShop          是否关联
     * @param id              主键ID
     * @param typeEnum        省市区类型 {@link SysProvinceCityAreaTypeEnum}
     * @param excludeNameList 需要排除的省市区名称
     * @return true:存在 false:不存在
     * @author zhengqingya
     */
    private boolean isShop(boolean isShop, Integer id, SysProvinceCityAreaTypeEnum typeEnum, List<String> excludeNameList) {
        if (isShop) {
            return isShop;
        }
        List<SysProvinceCityArea> list = this.sysProvinceCityAreaMapper.selectList(
                new LambdaQueryWrapper<SysProvinceCityArea>()
                        .eq(SysProvinceCityArea::getType, typeEnum.getType())
                        .eq(SysProvinceCityArea::getParentId, id)
                        .eq(SysProvinceCityArea::getIsShop, true)
                        .notIn(SysProvinceCityArea::getName, excludeNameList)
                        .last(MybatisConstant.LIMIT_ONE)
        );
        return CollUtil.isNotEmpty(list);
    }

}
