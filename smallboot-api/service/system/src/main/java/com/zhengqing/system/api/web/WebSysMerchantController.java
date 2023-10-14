package com.zhengqing.system.api.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.system.model.dto.SysMerchantAppOperationDTO;
import com.zhengqing.system.model.dto.SysMerchantListDTO;
import com.zhengqing.system.model.dto.SysMerchantPageDTO;
import com.zhengqing.system.model.dto.SysMerchantSaveDTO;
import com.zhengqing.system.model.vo.SysMerchantListVO;
import com.zhengqing.system.model.vo.SysMerchantPageVO;
import com.zhengqing.system.service.ISysMerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p> 系统管理-商户管理 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/13 11:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/merchant")
@Api(tags = {"web-系统管理-商户管理"})
public class WebSysMerchantController extends BaseController {

    private final ISysMerchantService iSysMerchantService;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<SysMerchantPageVO> page(@Validated @ModelAttribute SysMerchantPageDTO params) {
        if (this.isFillMerchantId()) {
            params.setId(this.getCurrentUserReMerchantId());
        }
        return this.iSysMerchantService.page(params);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<SysMerchantListVO> list(@Validated @ModelAttribute SysMerchantListDTO params) {
        if (this.isFillMerchantId()) {
            params.setId(this.getCurrentUserReMerchantId());
        }
        return this.iSysMerchantService.list(params);
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody SysMerchantSaveDTO params) {
        params.setId(null);
        this.iSysMerchantService.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody SysMerchantSaveDTO params) {
        this.iSysMerchantService.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.iSysMerchantService.deleteData(id);
    }


    @PostMapping("appOperationBatch")
    @ApiOperation("批量操作(小程序提审、发布)")
    public Boolean appOperationBatch(@Validated @RequestBody SysMerchantAppOperationDTO params) {
        this.iSysMerchantService.appOperationBatch(params);
        return true;
    }

}
