package com.zhengqing.common.core.custom.requestparamalias;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * <p> 请求的字段别名-数据绑定处理 </p>
 *
 * @author zhengqingya
 * @description 通过自定义spring属性编辑器解决
 * @date 2022/10/20 10:59
 */
@Slf4j
public class RequestParamAliasDataBinder extends ExtendedServletRequestDataBinder {
    private final Map<String, String> cacheMap;

    public RequestParamAliasDataBinder(Object target, String objectName, Map<String, String> asMap) {
        super(target, objectName);
        this.cacheMap = asMap;
    }

    /**
     * 复写addBindValues方法
     *
     * @param mpv 这里面存的就是请求参数的key-value
     * @param req 请求本身, 这里没有用到
     */
    @Override
    protected void addBindValues(@NotNull MutablePropertyValues mpv, @NotNull ServletRequest req) {
        super.addBindValues(mpv, req);
        Object obj;
        PropertyValue pv;
        log.info("字段别名-缓存,{}", this.cacheMap);
        for (Map.Entry<String, String> entry : this.cacheMap.entrySet()) {
            String alias = entry.getKey(), fieldName = entry.getValue();
            if (mpv.contains(alias)) {
                pv = mpv.getPropertyValue(alias);
                if (null == pv) {
                    continue;
                }
                obj = pv.getValue();
                log.debug("字段别名-数据绑定处理,{}<>{},{}", alias, fieldName, obj);
                // 给原始字段赋值
                mpv.add(fieldName, obj);
            }
        }
    }
}
