package com.zhengqing.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengqing.system.entity.SysProvinceCityArea;
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
        List<SysProvinceCityAreaTreeVO> list = this.sysProvinceCityAreaMapper.selectDataList(params);
        List<SysProvinceCityAreaTreeVO> result = list.stream().filter(e -> params.getType().equals(e.getType())).collect(Collectors.toList());
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

}
