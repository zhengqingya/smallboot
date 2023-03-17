package com.zhengqing.wxmp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengqing.wxmp.entity.WxMpTemplateMsg;
import com.zhengqing.wxmp.model.dto.WxMpTemplateMsgDetailDTO;
import com.zhengqing.wxmp.model.dto.WxMpTemplateMsgPageDTO;
import com.zhengqing.wxmp.model.dto.WxMpTemplateMsgSaveDTO;
import com.zhengqing.wxmp.model.vo.WxMpTemplateMsgDetailVO;
import com.zhengqing.wxmp.model.vo.WxMpTemplateMsgPageVO;

/**
 * <p>  微信公众号-模板消息 服务类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/03/15 18:29
 */
public interface IWxMpTemplateMsgService extends IService<WxMpTemplateMsg> {

    /**
     * 分页列表
     *
     * @param params 查询参数
     * @return 查询结果
     * @author zhengqingya
     * @date 2023/03/15 18:29
     */
    IPage<WxMpTemplateMsgPageVO> page(WxMpTemplateMsgPageDTO params);

    /**
     * 详情
     *
     * @param params 查询参数
     * @return 详情
     * @author zhengqingya
     * @date 2023/03/15 18:29
     */
    WxMpTemplateMsgDetailVO detail(WxMpTemplateMsgDetailDTO params);

    /**
     * 新增或更新
     *
     * @param params 保存参数
     * @return void
     * @author zhengqingya
     * @date 2023/03/15 18:29
     */
    void addOrUpdateData(WxMpTemplateMsgSaveDTO params);

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
