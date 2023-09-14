package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhengqing.system.entity.SysProvinceCityArea;
import com.zhengqing.system.enums.SysProvinceCityAreaTypeEnum;
import com.zhengqing.system.mapper.SysProvinceCityAreaMapper;
import com.zhengqing.system.model.dto.SysProvinceCityAreaTreeDTO;
import com.zhengqing.system.model.vo.SysProvinceCityAreaTreeVO;
import com.zhengqing.system.service.ISysProvinceCityAreaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        List<SysProvinceCityAreaTreeVO> result = Lists.newArrayList();
        List<SysProvinceCityAreaTreeVO> list = this.sysProvinceCityAreaMapper.selectDataList(params);
        // 拿到省数据
        List<SysProvinceCityAreaTreeVO> provinceList = list.stream().filter(e -> SysProvinceCityAreaTypeEnum.PROVINCE.getType().equals(e.getType())).collect(Collectors.toList());
        provinceList.forEach(provinceItem -> {
            // 市数据
            List<SysProvinceCityAreaTreeVO> cityList = list.stream().filter(e -> SysProvinceCityAreaTypeEnum.CITY.getType().equals(e.getType()) && provinceItem.getCode().equals(e.getParentCode())).collect(Collectors.toList());
            // 区数据
            cityList.forEach(cityItem -> cityItem.setChildren(list.stream().filter(e -> SysProvinceCityAreaTypeEnum.AREA.getType().equals(e.getType()) && cityItem.getCode().equals(e.getParentCode())).collect(Collectors.toList())));
            provinceItem.setChildren(cityList);
            result.add(provinceItem);
        });
        return result;
    }

}
