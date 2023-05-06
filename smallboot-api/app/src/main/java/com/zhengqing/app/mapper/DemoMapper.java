package com.zhengqing.app.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 测试demo Mapper 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/01/13 10:11
 */
@Mapper
public interface DemoMapper {

    /**
     * 更新数量
     *
     * @param id  主键id
     * @param num 数量
     * @return 更新条数
     * @author zhengqingya
     * @date 2022/1/17 6:55 下午
     */
    long updateNum(@Param("id") Integer id, @Param("num") Integer num);

}
