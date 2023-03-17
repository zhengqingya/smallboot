package com.zhengqing.wxmp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.wxmp.entity.WxMpUser;
import com.zhengqing.wxmp.model.dto.WxMpUserDetailDTO;
import com.zhengqing.wxmp.model.dto.WxMpUserPageDTO;
import com.zhengqing.wxmp.model.dto.WxMpUserSaveDTO;
import com.zhengqing.wxmp.model.vo.WxMpUserDetailVO;
import com.zhengqing.wxmp.model.vo.WxMpUserPageVO;

/**
 * <p>  微信公众号-用户 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:28
 */
public interface IWxMpUserService extends IService<WxMpUser> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/03/15 18:28
     */
    IPage<WxMpUserPageVO> page(WxMpUserPageDTO params);

    /**
     * 详情
     *
     * @param params 查询参数
     * @return 详情
     * @author zhengqingya
     * @date 2023/03/15 18:28
     */
    WxMpUserDetailVO detail(WxMpUserDetailDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/03/15 18:28
     */
    void addOrUpdateData(WxMpUserSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/03/15 18:28
     */
    void deleteData(Integer id);

}
