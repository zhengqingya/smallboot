package com.zhengqing.system.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.system.model.dto.SysConfigPageDTO;
import com.zhengqing.system.model.dto.SysConfigSaveDTO;
import com.zhengqing.system.model.vo.SysConfigPageVO;
import com.zhengqing.system.model.vo.SysConfigVO;
import com.zhengqing.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * <p> 系统管理-系统配置 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/09/06 22:57
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/config")
@Api(tags = {"系统管理-系统配置接口"})
public class SysConfigController extends BaseController {

    @Resource
    private ISysConfigService iSysConfigService;

    @GetMapping("listPage")
    @ApiOperation("列表分页")
    public IPage<SysConfigPageVO> listPage(@ModelAttribute SysConfigPageDTO params) {
        return this.iSysConfigService.listPage(params);
    }

    @PostMapping("")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody SysConfigSaveDTO params) {
        params.setId(null);
        this.iSysConfigService.addOrUpdateData(params);
    }

    @PutMapping("")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody SysConfigSaveDTO params) {
        this.iSysConfigService.addOrUpdateData(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@RequestParam Integer id) {
        this.iSysConfigService.removeById(id);
    }

    // ----------------------------------------------------------------------------------------

    @GetMapping("listByKey")
    @ApiOperation("根据属性key查询")
    public Map<String, SysConfigVO> listByKey(@RequestParam List<String> keyList) {
        return this.iSysConfigService.mapByKey(keyList);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<SysConfigVO> list(@RequestParam List<String> keyList) {
        return this.iSysConfigService.listByKey(keyList);
    }

    @NoRepeatSubmit
    @PostMapping("saveBatch")
    @ApiOperation("批量保存")
    public void saveBatch(@Validated @RequestBody ValidList<SysConfigSaveDTO> dataList) {
        this.iSysConfigService.saveBatch(dataList);
    }

//    @DeleteMapping("deleteByKey")
//    @ApiOperation("根据属性key删除数据")
//    public void deleteByKey(@RequestParam String key) {
//        this.iSysConfigService.deleteByKey(key);
//    }

}
