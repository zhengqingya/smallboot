package com.zhengqing.mall.mini.model.vo;

import com.google.common.collect.Lists;
import com.zhengqing.common.base.model.vo.BaseVO;
import com.zhengqing.mall.common.model.vo.PmsCategoryReSpuListVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>商城-分类-含商品-列表-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/02/10 14:01
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("mini-商城-分类-含商品-列表-响应参数")
public class MiniPmsCategoryReSpuListVO extends BaseVO {

    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("商品数据")
    private List<PmsCategoryReSpuListVO> spuList;

    public void handleData() {
        if (CollectionUtils.isEmpty(this.spuList)) {
            this.spuList = Lists.newLinkedList();
        }
    }

}
