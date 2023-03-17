package com.zhengqing.wxmp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.wxmp.entity.WxMpAccount;
import com.zhengqing.wxmp.model.dto.WxMpAccountPageDTO;
import com.zhengqing.wxmp.model.dto.WxMpAccountSaveDTO;
import com.zhengqing.wxmp.model.vo.WxMpAccountPageVO;

/**
 * <p>  微信公众号-账号 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:30
 */
public interface IWxMpAccountService extends IService<WxMpAccount> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/03/15 18:30
     */
    IPage<WxMpAccountPageVO> page(WxMpAccountPageDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/03/15 18:30
     */
    void addOrUpdateData(WxMpAccountSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/03/15 18:30
     */
    void deleteData(Integer id);

    /**
     * 初始化公众号配置
     *
     * @return void
     * @author zhengqingya
     * @date 2023/3/16 17:49
     */
    void initWxMpConfigStorages();

}
