package com.zhengqing.mall.service;

import com.zhengqing.mall.model.dto.MiniOmsCartBatchUpdateNumDTO;
import com.zhengqing.mall.model.dto.MiniOmsCartDeleteDTO;
import com.zhengqing.mall.model.dto.MiniOmsCartSaveDTO;
import com.zhengqing.mall.model.dto.MiniOmsCartUpdateNumDTO;
import com.zhengqing.mall.model.vo.MiniOmsCartVO;

import java.util.List;

/**
 * <p>  商城-购物车 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
public interface IOmsCartService {

    /**
     * 列表
     *
     * @param userId 用戶id
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/17 18:22
     */
    List<MiniOmsCartVO> list(Long userId);

    /**
     * 新增
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/10/12 9:58
     */
    void addData(MiniOmsCartSaveDTO params);

    /**
     * 更新数量
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/10/12 9:58
     */
    void updateNum(MiniOmsCartUpdateNumDTO params);

    /**
     * 批量更新数量
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/10/12 9:58
     */
    void batchUpdateNum(MiniOmsCartBatchUpdateNumDTO params);

    /**
     * 删除数据
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/10/12 10:06
     */
    void deleteData(MiniOmsCartDeleteDTO params);

}
