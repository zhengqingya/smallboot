package ${ package.vo };

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>${tableComment}-分页列表-响应参数</p>
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
@ApiModel("${tableComment}-分页列表-响应参数")
public class ${entity}PageVO extends BaseVO {

<#list columnInfoList as item>
    <#if item.columnNameDb != "create_by" && item.columnNameDb != "create_time" && item.columnNameDb != "update_by" && item.columnNameDb != "update_time" && item.columnNameDb != "is_deleted">
    @ApiModelProperty("${item.columnComment}")
    private ${item.columnTypeJava} ${item.columnNameJavaLower};

    </#if>
</#list>

    public void handleData() {

    }

}
