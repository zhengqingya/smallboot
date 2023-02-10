package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysDictType;
import com.zhengqing.system.model.dto.SysDictTypeSaveDTO;
import com.zhengqing.system.model.vo.SysDictTypeListVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典类型-服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:53
 */
public interface ISysDictTypeService extends IService<SysDictType> {

    /**
     * 查询已启用的数据字典类型列表信息
     *
     * @return 数据字典类型列表信息
     * @author zhengqingya
     * @date 2020/9/12 18:51
     */
    List<SysDictTypeListVO> listByOpen();

    /**
     * 查询字典类型
     *
     * @param codeList 字典编码list
     * @return 编码code -> 字典类型ID
     * @author zhengqingya
     * @date 2021/8/28 4:45 上午
     */
    Map<String, Integer> getDictTypeIdMap(List<String> codeList);

    /**
     * 详情
     *
     * @param dictTypeId 字典类型id
     * @return 字典类型数据
     * @author zhengqingya
     * @date 2021/8/19 10:22
     */
    SysDictType detail(Integer dictTypeId);

    /**
     * 详情
     *
     * @param code 字段类型编码
     * @return 字典类型数据
     * @author zhengqingya
     * @date 2021/8/27 11:41 下午
     */
    SysDictType detailByCode(String code);

    /**
     * 新增或更新
     *
     * @param params:
     * @return java.lang.Integer
     * @author zhengqingya
     * @date 2020/9/12 17:28
     */
    Integer addOrUpdateData(SysDictTypeSaveDTO params);

    /**
     * 根据id删除数据字典类型及其数据字典
     *
     * @param id 数据字典id
     * @return void
     * @author zhengqingya
     * @date 2020/9/12 17:26
     */
    void deleteType(Integer id);

}
