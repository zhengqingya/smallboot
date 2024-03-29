package com.zhengqing.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.system.entity.SysConfig;
import com.zhengqing.system.enums.SysConfigKeyEnum;
import com.zhengqing.system.model.dto.SysConfigPageDTO;
import com.zhengqing.system.model.dto.SysConfigSaveDTO;
import com.zhengqing.system.model.vo.SysConfigPageVO;
import com.zhengqing.system.model.vo.SysConfigVO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * <p> 系统管理-系统配置 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/09/06 22:57
 */
public interface ISysConfigService extends IService<SysConfig> {

    /**
     * 初始化缓存
     *
     * @return void
     * @author zhengqingya
     * @date 2020/9/12 17:37
     */
    void initCache();

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 结果
     * @author zhengqingya
     * @date 2020/9/10 14:44
     */
    IPage<SysConfigPageVO> listPage(SysConfigPageDTO params);

    /**
     * 通过属性key查询数据
     *
     * @param keyList 属性key
     * @return 属性key -> 系统配置
     * @author zhengqingya
     * @date 2021/09/06 22:57
     */
    Map<String, SysConfigVO> mapByKey(@NotEmpty(message = "属性key不能为空!") List<String> keyList);

    /**
     * 通过key查询数据 -- 业务方使用，会判断非空
     *
     * @param sysConfigKeyEnum 属性key
     * @return value
     * @author zhengqingya
     * @date 2021/09/06 22:57
     */
    Object getValue(SysConfigKeyEnum sysConfigKeyEnum);

    /**
     * 列表
     *
     * @param keyList 属性key
     * @return 数据查询结果
     * @author zhengqingya
     * @date 2021/9/7 11:04
     */
    List<SysConfigVO> listByKey(@NotEmpty(message = "属性key不能为空!") List<String> keyList);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     * @author zhengqingya
     * @date 2021/09/06 22:57
     */
    SysConfig detail(Integer id);

    /**
     * 详情
     *
     * @param key 属性key
     * @return 详情
     * @author zhengqingya
     * @date 2021/09/06 22:57
     */
    SysConfig detailByKey(@NotBlank(message = "属性不能为空!") String key);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2021/09/06 22:57
     */
    void addOrUpdateData(SysConfigSaveDTO params);

    /**
     * 批量保存
     *
     * @param dataList 保存参数
     * @return void
     * @author zhengqingya
     * @date 2021/09/06 22:57
     */
    void saveBatch(@NotEmpty(message = "属性不能为空!") ValidList<SysConfigSaveDTO> dataList);

    /**
     * 根据属性key删除数据
     *
     * @param key 属性key
     * @return void
     * @author zhengqingya
     * @date 2021/09/06 22:57
     */
    void deleteByKey(@NotBlank(message = "属性key不能为空!") String key);

}
