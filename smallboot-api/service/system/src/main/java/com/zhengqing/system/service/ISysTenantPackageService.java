package com.zhengqing.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysTenantPackage;
import com.zhengqing.system.model.dto.SysTenantPackageListDTO;
import com.zhengqing.system.model.dto.SysTenantPackagePageDTO;
import com.zhengqing.system.model.dto.SysTenantPackageSaveDTO;
import com.zhengqing.system.model.vo.SysTenantPackageListVO;
import com.zhengqing.system.model.vo.SysTenantPackagePageVO;

import java.util.List;

/**
 * <p>  系统管理-租户套餐 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/08 10:34
 */
public interface ISysTenantPackageService extends IService<SysTenantPackage> {

    /**
     * 查询详情
     *
     * @param id 主键id
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/08 10:34
     */
    SysTenantPackage detail(Integer id);

    /**
     * 根据租户id查询关联套餐详情
     *
     * @param tenantId 租户id
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/08 10:34
     */
    SysTenantPackage detailReTenantId(Integer tenantId);

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/08 10:34
     */
    IPage<SysTenantPackagePageVO> page(SysTenantPackagePageDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/10/08 10:34
     */
    List<SysTenantPackageListVO> list(SysTenantPackageListDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/10/08 10:34
     */
    void addOrUpdateData(SysTenantPackageSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/10/08 10:34
     */
    void deleteData(Integer id);

}
