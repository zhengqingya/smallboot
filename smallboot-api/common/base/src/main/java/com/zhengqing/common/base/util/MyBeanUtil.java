package com.zhengqing.common.base.util;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * bean 工具类
 * </p>
 *
 * @author zhengqingya <br/>
 * @date 2019/11/6$ 18:34$ <br/>
 */
@Slf4j
public class MyBeanUtil {

    /**
     * 对象属性拷贝
     *
     * @param source 源对象
     * @param target 目标对象
     * @return void
     * @author zhengqingya
     * @date 2020/9/5 23:22
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    /**
     * 对象属性拷贝 : 将源对象的属性拷贝到目标对象
     *
     * @param source 源对象
     * @param clz    目标对象class
     * @return 对象数据
     */
    public static <T> T copyProperties(Object source, Class<T> clz) {
        if (source == null) {
            return null;
        }
        T target = BeanUtils.instantiate(clz);
        try {
            BeanUtils.copyProperties(source, target);
        } catch (BeansException e) {
            log.error("BeanUtil property copy  failed :BeansException", e);
        } catch (Exception e) {
            log.error("BeanUtil property copy failed:Exception", e);
        }
        return target;
    }

    /**
     * 拷贝list
     *
     * @param inList 输入list
     * @param outClz 输出目标对象class
     * @return 返回集合
     */
    public static <E, T> List<T> copyList(List<E> inList, Class<T> outClz) {
        List<T> output = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(inList)) {
            for (E source : inList) {
                T target = BeanUtils.instantiate(outClz);
                BeanUtils.copyProperties(source, target);
                output.add(target);
            }
        }
        return output;
    }

    /**
     * 获取任意泛型类的指定属性值（暂只支持同时访问父类和子类属性，如果有多继承的话，还需要再修改）
     *
     * @param tList        list对象数据
     * @param field        要获取指定字段属性值对应的字段名
     * @param isSuperfield true->父类 false->子类
     * @return 字段属性值
     */
    public static <T> List<?> getFieldList(List<T> tList, String field, Boolean isSuperfield) {
        if (StringUtils.isBlank(field)) {
            return null;
        }

        // 拼接方法
        field =
                new StringBuffer("get").append(field.substring(0, 1).toUpperCase()).append(field.substring(1)).toString();
        List<Object> idList = Lists.newArrayList();
        Method method = null;
        try {
            if (isSuperfield) {
                method = tList.get(0).getClass().getSuperclass().getMethod(field);
            } else {
                method = tList.get(0).getClass().getMethod(field);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            for (T t : tList) {
                Object s = method.invoke(t);
                idList.add(s);
            }
        } catch (NullPointerException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return idList;
    }

    /**
     * 获取任意泛型类的指定属性值（暂只支持同时访问父类和子类属性，如果有多继承的话，还需要再修改）
     *
     * @param tList list对象数据
     * @param field 要获取指定字段属性值对应的字段名
     * @return 字段属性值
     */
    public static <T> List<?> getFieldList(List<Map<String, Object>> tList, String field) {
        if (StringUtils.isBlank(field)) {
            return null;
        }
        List<Object> idList = Lists.newArrayList();
        for (Map<String, Object> map : tList) {
            idList.add(map.get(field));
        }
        return idList;
    }

    /**
     * 将Map转换为对象 (注：暂无法转换含嵌套对象的map数据源)
     *
     * @param map 需转换map
     * @param clz 目标对象class
     * @return T
     * @author zhengqingya
     * @date 2020/11/27 18:39
     */
    public static <T> T mapToObj(Map<String, Object> map, Class<T> clz) {
        return JSONUtil.toBean(JSONUtil.toJsonStr(map), clz);
    }

    /**
     * 对象 转 map （通过反射获取类里面的值和名称）
     *
     * @param obj 对象
     * @return map
     * @author zhengqingya
     * @date 2021/1/26 16:09
     */
    @SneakyThrows(Exception.class)
    public static Map<String, String> objToMapStr(Object obj) {
        Map<String, String> map = Maps.newHashMap();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value == null ? "" : value.toString());
        }
        return map;
    }

    /**
     * 对象 转 map （通过反射获取类里面的值和名称）
     *
     * @param obj 对象
     * @return map
     * @author zhengqingya
     * @date 2021/1/26 16:09
     */
    @SneakyThrows(Exception.class)
    public static Map<String, Object> objToMap(Object obj) {
        Map<String, Object> map = Maps.newHashMap();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value == null ? "" : value.toString());
        }
        return map;
    }

}
