package ${package.dto};

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <p> ${tableComment}-详情-请求参数 </p>
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
@ApiModel("${tableComment}-详情-请求参数")
public class ${entity}DetailDTO extends BaseDTO {

    <#list queryColumnInfoList as item>
    @ApiModelProperty("${item.columnComment}")
    private ${item.columnTypeJava} ${item.columnNameJavaLower};

    </#list>
}
