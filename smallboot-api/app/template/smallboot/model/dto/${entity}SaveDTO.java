package ${package.dto};

import com.zhengqing.common.base.model.dto.BaseDTO;
import com.zhengqing.common.core.custom.validator.common.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p> ${tableComment}-保存-提交参数 </p>
 *
 * @author ${ author }
 * @description
 * @date ${date}
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("${tableComment}-保存-提交参数")
public class ${entity}SaveDTO extends BaseDTO {

<#list columnInfoList as item>
<#if item.columnNameDb != "create_by" && item.columnNameDb != "create_time" && item.columnNameDb != "update_by" && item.columnNameDb != "update_time" && item.columnNameDb != "is_deleted">
    @ApiModelProperty("${item.columnComment}")
<#if item.ifPrimaryKey>
    @NotNull(groups = {UpdateGroup.class}, message = "${item.columnComment}不能为空!")
</#if>
    private ${item.columnTypeJava} ${item.columnNameJavaLower};

</#if>
</#list>

}
