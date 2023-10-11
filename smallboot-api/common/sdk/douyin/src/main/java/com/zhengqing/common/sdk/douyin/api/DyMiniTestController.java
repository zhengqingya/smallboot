package com.zhengqing.common.sdk.douyin.api;


import com.zhengqing.common.sdk.douyin.mini.model.dto.DyMiniLoginDTO;
import com.zhengqing.common.sdk.douyin.mini.util.DyMiniApiUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 抖音API测试 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/5 2:36 下午
 */
@Slf4j
@RestController
@RequestMapping("api/test/douyin/mini")
@Api(tags = "test-douyin-mini")
public class DyMiniTestController {

    @ApiOperation("登录")
    @PostMapping("jscode2session")
    public Object jscode2session(@RequestBody DyMiniLoginDTO params) {
        return DyMiniApiUtil.jscode2session(params);
    }

}
