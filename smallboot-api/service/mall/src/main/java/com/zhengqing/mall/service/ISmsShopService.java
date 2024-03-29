package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.entity.SmsShop;
import com.zhengqing.mall.model.dto.SmsShopLatelyDTO;
import com.zhengqing.mall.model.dto.SmsShopPageDTO;
import com.zhengqing.mall.model.dto.WebSmsShopEditStatusDTO;
import com.zhengqing.mall.model.dto.WebSmsShopSaveDTO;
import com.zhengqing.mall.model.vo.SmsShopBaseVO;

/**
 * <p>  商城-店铺信息 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/13 09:51
 */
public interface ISmsShopService extends IService<SmsShop> {

    /**
     * 详情
     *
     * @param shopId 店铺ID
     * @return 详情
     * @author zhengqingya
     * @date 2023/09/13 09:51
     */
    SmsShop detailData(Integer shopId);

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/09/13 09:51
     */
    IPage<SmsShopBaseVO> page(SmsShopPageDTO params);

    /**
     * 详情
     *
     * @param params 查询参数
     * @return 详情
     * @author zhengqingya
     * @date 2023/09/13 09:51
     */
    SmsShopBaseVO detail(SmsShopPageDTO params);

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

    /**
     * 最近门店
     *
     * @param params 查询参数
     * @return 详情
     * @author zhengqingya
     * @date 2023/09/13 09:51
     */
    SmsShopBaseVO lately(SmsShopLatelyDTO params);

    /**
     * 批量更新状态
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2023/09/13 09:51
     */
    void updateBatchStatus(WebSmsShopEditStatusDTO params);

}
