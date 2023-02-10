package com.zhengqing.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.system.entity.SysOauth;
import com.zhengqing.system.model.dto.SysOauthListDTO;
import com.zhengqing.system.model.dto.SysOauthRemoveBindDTO;
import com.zhengqing.system.model.dto.SysOauthSaveDTO;
import com.zhengqing.system.model.vo.SysOauthDataListVO;
import com.zhengqing.system.model.vo.SysOauthListVO;
import me.zhyd.oauth.model.AuthCallback;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 系统管理 - 用户三方授权表 服务类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/11/28 22:14
 */
public interface ISysOauthService extends IService<SysOauth> {

    /**
     * 登录授权
     *
     * @param oauthType: 授权数据类型
     * @param response   响应数据
     * @return 重定向url
     * @author zhengqingya
     * @date 2020/11/27 17:37
     */
    String handleOauth(String oauthType, HttpServletResponse response);

    /**
     * 处理登录授权后回调数据
     *
     * @param oauthType: 授权数据类型
     * @param callback   回调信息
     * @param response   响应
     * @return void
     * @author zhengqingya
     * @date 2020/11/27 17:39
     */
    void handleCallback(String oauthType, AuthCallback callback, HttpServletResponse response);

    /**
     * 处理绑定后回调数据
     *
     * @param oauthType: 授权数据类型
     * @param callback   回调信息
     * @param response   响应
     * @return void
     * @author zhengqingya
     * @date 2020/12/6 18:44
     */
    void handleCallbackBind(String oauthType, AuthCallback callback, HttpServletResponse response);

    /**
     * 新增或更新
     *
     * @param params: 授权用户信息
     * @return 授权id
     * @author zhengqingya
     * @date 2020/11/28 22:14
     */
    Integer addOrUpdateData(SysOauthSaveDTO params);

    /**
     * 列表
     *
     * @param params: 查询参数
     * @return 列表数据
     * @author zhengqingya
     * @date 2020/12/6 13:59
     */
    List<SysOauthListVO> list(SysOauthListDTO params);

    /**
     * 获取指定用户的三方账号绑定数据信息
     *
     * @param userId: 用户id
     * @return 三方账号绑定数据信息
     * @author zhengqingya
     * @date 2020/12/6 13:54
     */
    List<SysOauthDataListVO> getOauthDataList(Integer userId);

    /**
     * 解除绑定
     *
     * @param params: 提交参数
     * @return void
     * @author zhengqingya
     * @date 2020/12/6 14:55
     */
    void removeBind(SysOauthRemoveBindDTO params);

}
