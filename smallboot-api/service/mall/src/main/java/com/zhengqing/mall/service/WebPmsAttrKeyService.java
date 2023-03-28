package com.zhengqing.mall.service;

import com.zhengqing.mall.entity.PmsAttrKey;
import com.zhengqing.mall.model.dto.WebPmsAttrListDTO;
import com.zhengqing.mall.model.dto.WebPmsAttrSaveDTO;
import com.zhengqing.mall.model.vo.WebPmsAttrKeyListVO;
import com.zhengqing.mall.model.vo.WebPmsAttrVO;

import java.util.List;

/**
 * <p>  商城-属性key 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 18:22
 */
public interface WebPmsAttrKeyService extends PmsAttrKeyService<PmsAttrKey> {

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 列表数据
     * @author zhengqingya
     * @date 2021/8/22 2:41 下午
     */
    List<WebPmsAttrKeyListVO> list(WebPmsAttrListDTO params);

    /**
     * 根据ids查询列表数据
     *
     * @param idList ids
     * @return 列表数据
     * @author zhengqingya
     * @date 2021/8/31 19:38
     */
    List<WebPmsAttrVO> listByIdList(List<String> idList);

    /**
     * 新增或更新
     *
     * @param params: 保存参数
     * @return 主键id
     * @author zhengqingya
     * @date 2021/08/20 17:38
     */
    String addOrUpdateData(WebPmsAttrSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键id
     * @return void
     * @author zhengqingya
     * @date 2021/8/22 2:52 下午
     */
    void deleteData(String id);

}
