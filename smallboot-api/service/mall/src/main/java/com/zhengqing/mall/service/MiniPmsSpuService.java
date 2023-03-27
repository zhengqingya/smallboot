package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.common.model.dto.PmsSpuPresellDTO;
import com.zhengqing.mall.entity.PmsSpu;
import com.zhengqing.mall.mini.model.dto.MiniPmsSpuPageDTO;
import com.zhengqing.mall.mini.model.dto.MiniPmsSpuPresellRemindDTO;
import com.zhengqing.mall.mini.model.vo.MiniPmsSpuDetailVO;
import com.zhengqing.mall.mini.model.vo.MiniPmsSpuPageVO;

/**
 * <p>  商城-商品 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
public interface MiniPmsSpuService extends PmsSpuService<PmsSpu> {

    /**
     * 列表分页
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/17 15:33
     */
    IPage<MiniPmsSpuPageVO> page(MiniPmsSpuPageDTO params);

    /**
     * 详情
     *
     * @param spuId 商品id
     * @return 商品详情
     * @author zhengqingya
     * @date 2021/8/20 9:18
     */
    MiniPmsSpuDetailVO detail(String spuId);


    /**
     * 预售提醒 -- 存储需要通知的用户信息
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/12/31 10:31
     */
    void presellRemind(MiniPmsSpuPresellRemindDTO params);

    /**
     * 预售提醒 -- 给用户发送消息通知
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/12/31 10:31
     */
    void presellRemindToSendUser(PmsSpuPresellDTO params);

}
