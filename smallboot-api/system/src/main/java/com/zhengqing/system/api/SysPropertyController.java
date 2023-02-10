package com.zhengqing.system.api;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.system.model.dto.SysPropertySaveDTO;
import com.zhengqing.system.model.vo.SysPropertyVO;
import com.zhengqing.system.service.ISysPropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * <p> 系统管理-系统属性 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/09/06 22:57
 */
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_WEB_SYSTEM + "/property")
@Api(tags = {"系统管理 - 系统属性接口"})
public class SysPropertyController extends BaseController {

    @Resource
    private ISysPropertyService sysPropertyService;

    @GetMapping("listByKey")
    @ApiOperation("根据属性key查询")
    public Map<String, SysPropertyVO> listByKey(@RequestParam List<String> keyList) {
        return this.sysPropertyService.mapByKey(keyList);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public List<SysPropertyVO> list(@RequestParam List<String> keyList) {
        return this.sysPropertyService.listByKey(keyList);
    }

    @NoRepeatSubmit
    @PostMapping("saveBatch")
    @ApiOperation("批量保存")
    public void saveBatch(@Validated @RequestBody ValidList<SysPropertySaveDTO> dataList) {
        this.sysPropertyService.saveBatch(dataList);
    }

    @DeleteMapping("deleteByKey")
    @ApiOperation("根据属性key删除数据")
    public void deleteByKey(@RequestParam String key) {
        this.sysPropertyService.deleteByKey(key);
    }

}
