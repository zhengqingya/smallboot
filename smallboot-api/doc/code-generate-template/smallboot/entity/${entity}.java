package ${package.entity};

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p> ${tableComment} </p>
 *
 * @author ${author}
 * @description
 * @date ${date}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("${tableName}")
@ApiModel("${tableComment}")
public class ${entity} extends BaseEntity<${entity}> {

    <#list columnInfoList as item>
    <#if item.columnNameDb != "create_by" && item.columnNameDb != "create_time" && item.columnNameDb != "update_by" && item.columnNameDb != "update_time" && item.columnNameDb != "is_deleted">
    @ApiModelProperty("${item.columnComment}")
    <#if item.ifPrimaryKey>
    @TableId(value = "${item.columnNameDb}", type = IdType.AUTO)
    </#if>
    private ${item.columnTypeJava} ${item.columnNameJavaLower};

    </#if>
    </#list>
}
