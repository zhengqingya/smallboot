package com.zhengqing.wxmp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.wxmp.entity.WxTemplateMsg;
import com.zhengqing.wxmp.model.dto.WxTemplateMsgDetailDTO;
import com.zhengqing.wxmp.model.dto.WxTemplateMsgPageDTO;
import com.zhengqing.wxmp.model.dto.WxTemplateMsgSaveDTO;
import com.zhengqing.wxmp.model.vo.WxTemplateMsgDetailVO;
import com.zhengqing.wxmp.model.vo.WxTemplateMsgPageVO;

/**
 * <p>  微信公众号-模板消息 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:29
 */
public interface IWxTemplateMsgService extends IService<WxTemplateMsg> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/03/15 18:29
     */
    IPage<WxTemplateMsgPageVO> page(WxTemplateMsgPageDTO params);

    /**
     * 详情
     *
     * @param params 查询参数
     * @return 详情
     * @author zhengqingya
     * @date 2023/03/15 18:29
     */
    WxTemplateMsgDetailVO detail(WxTemplateMsgDetailDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/03/15 18:29
     */
    void addOrUpdateData(WxTemplateMsgSaveDTO params);

    /**
     * 删除数据
     *
     * @param id 主键ID
     * @return void
     * @author zhengqingya
     * @date 2023/03/15 18:29
     */
    void deleteData(Integer id);

}
