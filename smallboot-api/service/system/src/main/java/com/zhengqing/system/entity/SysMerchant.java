package com.zhengqing.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.IsDeletedBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * <p>  系统管理-商户管理 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2023/10/13 11:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_sys_merchant")
@ApiModel("系统管理-商户管理")
public class SysMerchant extends IsDeletedBaseEntity<SysMerchant> {

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("租户ID")
    private Integer tenantId;


    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("管理员用户id")
    private Integer adminUserId;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("状态(0:停用 1:正常)")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

    /**
     * {@link com.zhengqing.system.enums.SysMerchantTypeEnum}
     */
    @ApiModelProperty("商户类型")
    private Integer type;

    @ApiModelProperty("过期时间")
    private Date expireTime;

    @ApiModelProperty("最大用户数")
    private Integer userNum;

    @ApiModelProperty("最大职位发布数")
    private Integer jobNum;

    @ApiModelProperty("AppID")
    private String appId;

    @ApiModelProperty("AppSecret")
    private String appSecret;

}
