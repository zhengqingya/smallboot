package com.zhengqing.wxmp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.wxmp.entity.WxUser;
import com.zhengqing.wxmp.model.dto.WxUserDetailDTO;
import com.zhengqing.wxmp.model.dto.WxUserPageDTO;
import com.zhengqing.wxmp.model.dto.WxUserSaveDTO;
import com.zhengqing.wxmp.model.vo.WxUserDetailVO;
import com.zhengqing.wxmp.model.vo.WxUserPageVO;

/**
 * <p>  微信公众号-用户 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:28
 */
public interface IWxUserService extends IService<WxUser> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/03/15 18:28
     */
    IPage<WxUserPageVO> page(WxUserPageDTO params);

    /**
     * 详情
     *
     * @param params 查询参数
     * @return 详情
     * @author zhengqingya
     * @date 2023/03/15 18:28
     */
    WxUserDetailVO detail(WxUserDetailDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/03/15 18:28
     */
    void addOrUpdateData(WxUserSaveDTO params);

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
