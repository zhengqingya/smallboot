package com.zhengqing.ums.api;


import cn.hutool.json.JSONUtil;
import com.zhengqing.common.auth.custom.open.ApiOpen;
import com.zhengqing.common.base.constant.ServiceConstant;
import com.zhengqing.common.base.util.MyBeanUtil;
import com.zhengqing.common.web.custom.noreturnhandle.NoReturnHandle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p> 抖音-事件回调通知 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/5 2:36 下午
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.SERVICE_API_PREFIX_DOUYIN)
@Api(tags = "抖音-事件回调通知")
public class DyCallbackController {

    /**
     * /api/douyin/event/notice
     */
    @ApiOpen
    @NoReturnHandle
    @ApiOperation("事件通知")
    @PostMapping("/event/notice")
    public Object notice(@RequestBody(required = false) Object params) {
        String noticeData = JSONUtil.toJsonStr(params);
        System.out.println(noticeData);
        Map<String, Object> noticeMap = MyBeanUtil.objToMap(params);
        String encrypt = String.valueOf(noticeMap.get("Encrypt"));

        return "success";
    }

}
