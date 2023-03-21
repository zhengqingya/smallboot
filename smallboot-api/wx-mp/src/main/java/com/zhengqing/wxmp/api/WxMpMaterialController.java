package com.zhengqing.wxmp.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.wxmp.model.dto.WxMpMaterialPageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNewsBatchGetResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 微信公众号-素材管理 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/3/21 11:19
 */
@AllArgsConstructor
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_WXMP + "/material")
@Api(tags = {"微信公众号-素材管理"})
public class WxMpMaterialController {
    private final WxMpService wxMpService;

    @GetMapping("/page")
    @ApiOperation("分页列表")
    @SneakyThrows(Exception.class)
    public Object page(@ModelAttribute WxMpMaterialPageDTO params) {
        String type = params.getType();
        Integer pageNum = params.getPageNum();
        Integer pageSize = params.getPageSize();
        int offset = (pageNum - 1) * pageSize;

        IPage result = new Page<>();
        result.setCurrent(pageNum);
        result.setSize(pageSize);

        WxMpMaterialService materialService = this.wxMpService.switchoverTo(params.getAppId()).getMaterialService();

        if (WxConsts.MaterialType.NEWS.equals(type)) {
            // 图文素材
            WxMpMaterialNewsBatchGetResult wxMpMaterialNewsBatchGetResult = materialService.materialNewsBatchGet(offset, pageSize);
            result.setTotal(wxMpMaterialNewsBatchGetResult.getTotalCount());
            result.setRecords(wxMpMaterialNewsBatchGetResult.getItems());
            return result;
        }

        // 其它素材
        WxMpMaterialFileBatchGetResult wxMpMaterialFileBatchGetResult = materialService.materialFileBatchGet(type, offset, pageSize);
        result.setTotal(wxMpMaterialFileBatchGetResult.getTotalCount());
        result.setRecords(wxMpMaterialFileBatchGetResult.getItems());
        return result;
    }


}
