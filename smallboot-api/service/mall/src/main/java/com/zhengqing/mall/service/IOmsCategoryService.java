package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.entity.PmsCategory;
import com.zhengqing.mall.model.dto.MiniPmsCategoryReSpuListDTO;
import com.zhengqing.mall.model.dto.WebPmsCategoryBaseDTO;
import com.zhengqing.mall.model.dto.WebPmsCategoryEditShowDTO;
import com.zhengqing.mall.model.dto.WebPmsCategorySaveDTO;
import com.zhengqing.mall.model.vo.MiniPmsCategoryReSpuListVO;
import com.zhengqing.mall.model.vo.WebPmsCategoryBaseVO;

import java.util.List;

/**
 * <p>  商城-分类 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 14:01
 */
public interface IOmsCategoryService extends IService<PmsCategory> {

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    PmsCategory detail(String id);

    /**
     * 批量删除分类数据
     *
     * @param idList ids
     * @return void
     * @author zhengqingya
     * @date 2022/3/2 11:39
     */
    void deleteBatch(List<String> idList);

    /**
     * 列表(包含关联商品数据)
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    List<MiniPmsCategoryReSpuListVO> reSpuList(MiniPmsCategoryReSpuListDTO params);

    /**
     * 分页列表(包含4个商品数据)
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    IPage<MiniPmsCategoryReSpuListVO> reSpuPage(MiniPmsCategoryReSpuListDTO params);

    // web ------------------------------------------

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    IPage<WebPmsCategoryBaseVO> page(WebPmsCategoryBaseDTO params);

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2022/02/10 14:01
     */
    List<WebPmsCategoryBaseVO> list(WebPmsCategoryBaseDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return 主键id
     * @author zhengqingya
     * @date 2021/08/20 17:38
     */
    String addOrUpdateData(WebPmsCategorySaveDTO params);

    /**
     * 删除数据 -- 批量
     *
     * @param idList 主键ids
     * @return void
     * @author zhengqingya
     * @date 2021/8/22 2:52 下午
     */
    void deleteBatchForBusiness(List<String> idList);

    /**
     * 批量更新显示状态
     *
     * @param params 提交参数
     * @return void
     * @author zhengqingya
     * @date 2021/8/20 16:20
     */
    void updateBatchShow(WebPmsCategoryEditShowDTO params);

}
