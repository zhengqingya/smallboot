package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysProvinceCityArea;
import com.zhengqing.system.model.dto.SysProvinceCityAreaTreeDTO;
import com.zhengqing.system.model.vo.SysProvinceCityAreaTreeVO;

import java.util.List;

/**
 * <p>  系统管理-省市区 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/14 11:38
 */
public interface ISysProvinceCityAreaService extends IService<SysProvinceCityArea> {

    /**
     * 省市区树
     *
     * @param params 提交参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/09/14 11:38
     */
    List<SysProvinceCityAreaTreeVO> tree(SysProvinceCityAreaTreeDTO params);

}
