package com.zhengqing.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.mall.entity.PmsAttrValue;
import com.zhengqing.mall.model.dto.WebPmsAttrValueListDTO;
import com.zhengqing.mall.model.dto.WebPmsAttrValueSaveDTO;
import com.zhengqing.mall.model.vo.WebPmsAttrValueListVO;

import java.util.List;

/**
 * <p>  商城-属性value 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/22 15:06
 */
public interface IPmsAttrValueService extends IService<PmsAttrValue> {

    /**
     * 列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2021/08/22 15:06
     */
    List<WebPmsAttrValueListVO> list(WebPmsAttrValueListDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return 主键id
     * @author zhengqingya
     * @date 2021/08/22 15:06
     */
    String addOrUpdateData(WebPmsAttrValueSaveDTO params);

    /**
     * 删除数据
     *
     * @param attrValueId 主键ID
     * @return void
     * @author zhengqingya
     * @date 2021/08/22 15:06
     */
    void deleteData(String attrValueId);

    /**
     * 根据属性key删除该属性下所有数据
     *
     * @param attrKeyId 属性key
     * @return void
     * @author zhengqingya
     * @date 2021/08/22 15:06
     */
    void deleteDataByAttrKeyId(String attrKeyId);

}
