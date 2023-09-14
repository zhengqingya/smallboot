package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.entity.SmsShop;
import com.zhengqing.mall.model.dto.WebSmsShopDetailDTO;
import com.zhengqing.mall.model.dto.WebSmsShopPageDTO;
import com.zhengqing.mall.model.dto.WebSmsShopSaveDTO;
import com.zhengqing.mall.model.vo.WebSmsShopDetailVO;
import com.zhengqing.mall.model.vo.WebSmsShopPageVO;

/**
 * <p>  商城-店铺信息 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/13 09:51
 */
public interface ISmsShopService extends IService<SmsShop> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/09/13 09:51
     */
    IPage<WebSmsShopPageVO> page(WebSmsShopPageDTO params);

    /**
     * 详情
     *
     * @param params 查询参数
     * @return 详情
     * @author zhengqingya
     * @date 2023/09/13 09:51
     */
    WebSmsShopDetailVO detail(WebSmsShopDetailDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/09/13 09:51
     */
    void addOrUpdateData(WebSmsShopSaveDTO params);

    /**
     * 删除数据
     *
     * @param shopId 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/09/13 09:51
     */
    void deleteData(Integer shopId);

}
