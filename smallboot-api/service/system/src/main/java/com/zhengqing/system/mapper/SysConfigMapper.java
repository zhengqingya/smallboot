package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.system.entity.SysConfig;
import com.zhengqing.system.model.dto.SysConfigPageDTO;
import com.zhengqing.system.model.dto.SysConfigSaveDTO;
import com.zhengqing.system.model.vo.SysConfigPageVO;
import com.zhengqing.system.model.vo.SysConfigVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p> 系统管理-系统配置 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/09/06 22:57
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 分页列表
     *
     * @param filter 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/09/06 22:57
     */
    IPage<SysConfigPageVO> selectListPage(IPage<SysConfigPageVO> page, @Param("filter") SysConfigPageDTO filter);

    /**
     * 列表
     *
     * @param keyList 属性key
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/09/06 22:57
     */
    List<SysConfigVO> selectDataListByKey(@Param("keyList") List<String> keyList);

    /**
     * 根据属性key删除数据
     *
     * @param key 属性key
     * @return void
     * @author zhengqingya
     * @date 2021/9/6 11:36 下午
     */
    @Update("UPDATE t_sys_config SET is_deleted=1 WHERE `key` = #{key}")
    void deleteByKey(@Param("key") String key);

    /**
     * 根据属性key批量删除数据 -- 逻辑删除
     *
     * @param keyList 根据属性key删除数据
     * @return void
     * @author zhengqingya
     * @date 2021/9/6 11:52 下午
     */
    void logicDeleteByKeyList(@Param("keyList") List<String> keyList);

    /**
     * 根据属性key批量删除数据
     *
     * @param keyList 根据属性key删除数据
     * @return void
     * @author zhengqingya
     * @date 2021/9/6 11:52 下午
     */
    void deleteByKeyList(@Param("keyList") List<String> keyList);

    /**
     * 批量保存，主键id存在时，作修改处理；不存在时，作插入新数据处理。
     *
     * @param list 保存数据
     * @return void
     * @author zhengqingya
     * @date 2021/9/6 11:36 下午
     */
    void batchInsertOrUpdate(@Param("list") List<SysConfigSaveDTO> list);

}
