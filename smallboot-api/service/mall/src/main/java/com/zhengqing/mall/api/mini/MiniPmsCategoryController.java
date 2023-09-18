package com.zhengqing.mall.api.mini;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.mall.model.dto.MiniPmsCategoryReSpuListDTO;
import com.zhengqing.mall.model.vo.MiniPmsCategoryReSpuListVO;
import com.zhengqing.mall.service.IOmsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p> 商城-分类 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_MALL + "/category")
@Api(tags = {"mini-分类"})
public class MiniPmsCategoryController {

    private final IOmsCategoryService iOmsCategoryService;

    @GetMapping("reSpuList")
    @ApiOperation("列表(包含关联商品数据)")
    public List<MiniPmsCategoryReSpuListVO> reSpuList(@Validated @ModelAttribute MiniPmsCategoryReSpuListDTO params) {
        return this.iOmsCategoryService.reSpuList(params);
    }

    @GetMapping("reSpuPage")
    @ApiOperation("分页列表(包含4个商品数据)")
    public IPage<MiniPmsCategoryReSpuListVO> reSpuPage(@Validated @ModelAttribute MiniPmsCategoryReSpuListDTO params) {
        return this.iOmsCategoryService.reSpuPage(params);
    }

}
