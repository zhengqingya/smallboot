package com.zhengqing.mall.api.mini;

import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.context.UmsUserContext;
import com.zhengqing.mall.model.dto.MiniOmsCartBatchUpdateNumDTO;
import com.zhengqing.mall.model.dto.MiniOmsCartDeleteDTO;
import com.zhengqing.mall.model.dto.MiniOmsCartSaveDTO;
import com.zhengqing.mall.model.dto.MiniOmsCartUpdateNumDTO;
import com.zhengqing.mall.model.vo.MiniOmsCartVO;
import com.zhengqing.mall.service.IOmsCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p> 商城-购物车 接口 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/17 15:33
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_MINI_MALL + "/cart")
@Api(tags = {"mini-购物车"})
public class MiniOmsCartController {

    private final IOmsCartService iOmsCartService;

    @GetMapping("list")
    @ApiOperation("列表")
    public List<MiniOmsCartVO> list(@RequestParam(required = false) Long userId) {
        if (userId == null) {
            userId = UmsUserContext.getUserId();
        }
        return this.iOmsCartService.list(userId);
    }

    @PostMapping("")
    @ApiOperation("新增")
    public void add(@Validated @RequestBody MiniOmsCartSaveDTO params) {
        this.iOmsCartService.addData(params);
    }

    @PutMapping("updateNum")
    @ApiOperation("更新数量")
    public void updateNum(@Validated @RequestBody MiniOmsCartUpdateNumDTO params) {
        this.iOmsCartService.updateNum(params);
    }

    @PutMapping("batchUpdateNum")
    @ApiOperation("批量更新数量")
    public void batchUpdateNum(@Validated @RequestBody MiniOmsCartBatchUpdateNumDTO params) {
        this.iOmsCartService.batchUpdateNum(params);
    }

    @DeleteMapping("")
    @ApiOperation("删除")
    public void delete(@Validated @RequestBody MiniOmsCartDeleteDTO params) {
        this.iOmsCartService.deleteData(params);
    }

}
