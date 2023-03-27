package com.zhengqing.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengqing.system.entity.SysOauth;
import com.zhengqing.system.model.dto.SysOauthListDTO;
import com.zhengqing.system.model.vo.SysOauthListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统管理 - 用户三方授权表 Mapper 接口
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/28 22:14
 */
public interface SysOauthMapper extends BaseMapper<SysOauth> {

    /**
     * 列表
     *
     * @param filter: 过滤参数
     * @return 列表数据
     * @author zhengqingya
     * @date 2020/12/6 13:59
     */
    List<SysOauthListVO> selectDataList(@Param("filter") SysOauthListDTO filter);

    /**
     * 查询授权信息
     *
     * @param oauthType: 授权类型
     * @param openId     三方id
     * @return 授权信息
     * @author zhengqingya
     * @date 2020/11/28 22:24
     */
    SysOauth detail(@Param("oauthType") Integer oauthType, @Param("openId") Integer openId);

}
