package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysOauthClient;
import com.zhengqing.system.model.vo.SysOauthClientVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p> oauth客户端 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/06/10 16:25
 */
public interface SysOauthClientMapper extends BaseMapper<SysOauthClient> {

    /**
     * 详情
     *
     * @param clientId 客户端ID
     * @return 详情
     * @author zhengqingya
     * @date 2022/06/10 16:25
     */
    @Select("SELECT * FROM t_sys_oauth_client WHERE client_id = #{clientId}")
    SysOauthClientVO selectClient(@Param("clientId") String clientId);

}
