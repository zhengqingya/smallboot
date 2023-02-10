package com.zhengqing.common.core.custom.requestparamalias;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.servlet.ServletRequest;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> 请求的字段别名-参数解析器 </p>
 *
 * @author zhengqingya
 * @description 通过自定义spring属性编辑器解决
 * @date 2022/10/20 11:00
 */
public class RequestParamAliasProcessor extends ServletModelAttributeMethodProcessor {
    private static final Map<Class<?>, Map<String, String>> PARAM_CACHE_MAP = new ConcurrentHashMap<>();
    private final ApplicationContext context;

    public RequestParamAliasProcessor(ApplicationContext applicationContext) {
        super(true);
        this.context = applicationContext;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 接口方法不含 注解 RequestParam、入参不是 简单参数、并且参数对象内的字段包含自定义注解 RequestParamAlias
        return !parameter.hasParameterAnnotation(RequestParam.class)
                && !parameter.hasParameterAnnotation(RequestBody.class)
                && !BeanUtils.isSimpleProperty(parameter.getParameterType())
                && Arrays.stream(parameter.getParameterType().getDeclaredFields())
                .anyMatch(field -> field.getAnnotation(RequestParamAlias.class) != null);
    }

    @Override
    protected void bindRequestParameters(@NotNull WebDataBinder binder, @NotNull NativeWebRequest request) {
        Map<String, String> asMap = this.cacheMap(Objects.requireNonNull(binder.getTarget()).getClass());
        RequestParamAliasDataBinder dataBinder = new RequestParamAliasDataBinder(binder.getTarget(), binder.getObjectName(), asMap);
        RequestMappingHandlerAdapter adapter = this.context.getBean(RequestMappingHandlerAdapter.class);
        Objects.requireNonNull(adapter.getWebBindingInitializer()).initBinder(dataBinder);
        dataBinder.bind(Objects.requireNonNull(request.getNativeRequest(ServletRequest.class)));
        super.bindRequestParameters(binder, request);
    }

    private Map<String, String> cacheMap(Class<?> target) {
        if (PARAM_CACHE_MAP.containsKey(target)) {
            return PARAM_CACHE_MAP.get(target);
        }
        Map<String, String> map = this.analyzeClass(target, "", "");
        PARAM_CACHE_MAP.put(target, map);
        return map;
    }

    private Map<String, String> analyzeClass(Class<?> target, String prtAs, String prtField) {
        Field[] fields = target.getDeclaredFields();
        Map<String, String> map = new HashMap<>(fields.length);
        RequestParamAlias rpa;
        boolean boo;
        String as;
        for (Field field : fields) {
            boo = (null == (rpa = field.getAnnotation(RequestParamAlias.class)) || rpa.value().isEmpty());
            as = boo ? field.getName() : rpa.value();
            if (this.isExtendBean(field.getType().getInterfaces())) {
                // 如果字段类实现了自定义接口，就认为是自定义是对象类，继续解析字段
                // 通过自定义注解在类上面使用，在这里判断也可以
                map.putAll(this.analyzeClass(field.getType(), prtAs + as + '.', prtField + field.getName() + '.'));
            } else if (!boo || !"".equals(prtAs)) {
                // 只绑定需要的映射
                map.put(prtAs + as, prtField + field.getName());
            }
        }
        return map;
    }

    private boolean isExtendBean(Class<?>[] interfaces) {
        for (Class<?> face : interfaces) {
            if (face == RequestParamAliasBean.class) {
                return true;
            }
        }
        return false;
    }
}
