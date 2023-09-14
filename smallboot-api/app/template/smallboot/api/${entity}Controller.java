package ${package.api};

import java.util.List;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ${package.vo}.${entity}DetailVO;
import ${package.vo}.${entity}PageVO;
import ${package.dto}.${entity}PageDTO;
import ${package.dto}.${entity}DetailDTO;
import ${package.dto}.${entity}SaveDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.core.api.BaseController;
import com.zhengqing.common.core.custom.repeatsubmit.NoRepeatSubmit;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import com.zhengqing.modules.smalltools.db.service.IStDbDataSourceService;
import lombok.RequiredArgsConstructor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * <p> ${tableComment} 接口 </p>
 *
 * @author ${author}
 * @description
 * @date ${date}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${moduleName}/${entityNameLower}")
@Api(tags = {"${tableComment}"})
public class ${entity}Controller extends BaseController {

    private final I${entity}Service ${entityNameLower}Service;

    @GetMapping("page")
    @ApiOperation("分页列表")
    public IPage<${entity}PageVO> page(@Validated @ModelAttribute ${entity}PageDTO params) {
        return this.${entityNameLower}Service.page(params);
    }

    @GetMapping("detail")
    @ApiOperation("详情")
    public ${entity}DetailVO detail(@Validated @ModelAttribute ${entity}DetailDTO params) {
        return this.${entityNameLower}Service.detail(params);
    }

    @NoRepeatSubmit
    @PostMapping("add")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody ${entity}SaveDTO params) {
        params.setId(null);
        this.${entityNameLower}Service.addOrUpdateData(params);
    }

    @NoRepeatSubmit
    @PutMapping("update")
    @ApiOperation("更新")
    public void update(@Validated(UpdateGroup.class) @RequestBody ${entity}SaveDTO params) {
        this.${entityNameLower}Service.addOrUpdateData(params);
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public void delete(@RequestParam ${primaryColumnTypeJava} ${primaryColumnNameJavaLower}) {
        this.${entityNameLower}Service.deleteData(${primaryColumnNameJavaLower});
    }

}
