package com.zhengqing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.mall.entity.SmsShop;
import com.zhengqing.mall.model.dto.SmsShopPageDTO;
import com.zhengqing.mall.model.dto.WebSmsShopEditStatusDTO;
import com.zhengqing.mall.model.vo.SmsShopBaseVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 商城-店铺信息 Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/09/13 09:51
 */
public interface SmsShopMapper extends BaseMapper<SmsShop> {

    /**
     * 列表分页
     *
     * @param page   分页数据
     * @param filter 查询过滤参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/09/13 09:51
     */
    IPage<SmsShopBaseVO> selectDataPage(IPage page, @Param("filter") SmsShopPageDTO filter);

    /**
     * 批量更新状态
     *
     * @param filter 提交参数
     * @return void
     * @author zhengqingya
     * @date 2023/09/13 09:51
     */
    void updateBatchStatus(@Param("filter") WebSmsShopEditStatusDTO filter);

}
