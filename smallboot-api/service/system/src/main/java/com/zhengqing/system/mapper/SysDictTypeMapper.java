package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysDictType;
import com.zhengqing.system.model.bo.SysDictTypeBO;
import com.zhengqing.system.model.vo.SysDictTypeListVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 数据字典类型 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 20:53
 */
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {

    /**
     * 查询已启用的数据字典类型列表信息
     *
     * @return 数据字典类型列表信息
     * @author zhengqingya
     * @date 2020/9/12 18:51
     */
    @Select("SELECT id,code,name,sort FROM t_sys_dict_type WHERE status=1 AND is_deleted = 0")
    List<SysDictTypeListVO> selectDictTypeListByOpen();

    /**
     * 查询字典类型
     *
     * @param codeList 字典编码list
     * @return 编码code -> 字典类型ID
     * @author zhengqingya
     * @date 2021/8/28 4:45 上午
     */
    List<SysDictTypeBO> selectDataList(@Param("codeList") List<String> codeList);

}
