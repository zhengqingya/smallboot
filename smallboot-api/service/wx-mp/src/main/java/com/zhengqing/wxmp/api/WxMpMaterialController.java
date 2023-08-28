package com.zhengqing.wxmp.api;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.util.MyFileUtil;
import com.zhengqing.wxmp.model.dto.WxMpMaterialPageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMaterialService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNewsBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
    public Object page(@Validated @ModelAttribute WxMpMaterialPageDTO params) {
        String mediaType = params.getMediaType();
        Integer pageNum = params.getPageNum();
        Integer pageSize = params.getPageSize();
        int offset = (pageNum - 1) * pageSize;

        IPage result = new Page<>();
        result.setCurrent(pageNum);
        result.setSize(pageSize);

        WxMpMaterialService materialService = this.wxMpService.switchoverTo(params.getAppId()).getMaterialService();

        if (WxConsts.MaterialType.NEWS.equals(mediaType)) {
            // 图文素材
            WxMpMaterialNewsBatchGetResult wxMpMaterialNewsBatchGetResult = materialService.materialNewsBatchGet(offset, pageSize);
            result.setTotal(wxMpMaterialNewsBatchGetResult.getTotalCount());
            result.setRecords(wxMpMaterialNewsBatchGetResult.getItems());
            return result;
        }

        // 其它素材
        WxMpMaterialFileBatchGetResult wxMpMaterialFileBatchGetResult = materialService.materialFileBatchGet(mediaType, offset, pageSize);
        result.setTotal(wxMpMaterialFileBatchGetResult.getTotalCount());
        result.setRecords(wxMpMaterialFileBatchGetResult.getItems());
        return result;
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    @SneakyThrows(Exception.class)
    public WxMpMaterialUploadResult add(@RequestPart @RequestParam MultipartFile file,
                                        @RequestParam String appId,
                                        @RequestParam String mediaType) {
        File wxFile = MyFileUtil.multipartFileToFile(file);
        WxMpMaterial wxMaterial = new WxMpMaterial();
        wxMaterial.setFile(wxFile);
        WxMpMaterialUploadResult wxMpMaterialUploadResult = this.wxMpService.switchoverTo(appId).getMaterialService().materialFileUpload(mediaType, wxMaterial);
        FileUtil.del(wxFile);
        return wxMpMaterialUploadResult;
    }


}
