package com.zhengqing.wxmp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.wxmp.entity.WxAccount;
import com.zhengqing.wxmp.model.dto.WxAccountPageDTO;
import com.zhengqing.wxmp.model.dto.WxAccountSaveDTO;
import com.zhengqing.wxmp.model.vo.WxAccountPageVO;

/**
 * <p>  微信公众号-账号 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:30
 */
public interface IWxAccountService extends IService<WxAccount> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/03/15 18:30
     */
    IPage<WxAccountPageVO> page(WxAccountPageDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/03/15 18:30
     */
    void addOrUpdateData(WxAccountSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/03/15 18:30
     */
    void deleteData(Integer id);

}
