package com.zhengqing.common.core.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p> CollectionUtil 工具类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/29 16:57
 */
public class MyCollectionUtil {

    /**
     * list转map
     *
     * @param list          列表数据
     * @param keyFunc       eg: User::getName
     * @param valueFunc     eg: User::getAge
     * @param mergeFunction eg: (oldData, newData) -> newData
     * @return java.util.Map<K, V>
     * @author zhengqingya
     */
    public static <T, K, V> Map<K, V> toMap(Collection<T> list, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction) {
        if (CollUtil.isEmpty(list)) {
            return Maps.newHashMap();
        }
        return list.stream().collect(Collectors.toMap(keyFunc, valueFunc, mergeFunction));
    }

    /**
     * list转map
     *
     * @param list    列表数据
     * @param keyFunc eg: User::getName
     * @return java.util.Map<K, java.util.List < T>>
     * @author zhengqingya
     */
    public static <T, K> Map<K, List<T>> toMapMultiList(Collection<T> list, Function<T, K> keyFunc) {
        if (CollUtil.isEmpty(list)) {
            return Maps.newHashMap();
        }
        return toMapMultiList(list, keyFunc, t -> t);
    }

    /**
     * list转map
     *
     * @param list      列表数据
     * @param keyFunc   eg: User::getName
     * @param valueFunc eg: User::getAge
     * @return java.util.Map<K, java.util.List < V>>
     * @author zhengqingya
     */
    public static <T, K, V> Map<K, List<V>> toMapMultiList(Collection<T> list, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (CollUtil.isEmpty(list)) {
            return Maps.newHashMap();
        }
        return list.stream().collect(Collectors.groupingBy(keyFunc, Collectors.mapping(valueFunc, Collectors.toList())));
    }

    /**
     * list转map
     *
     * @param list      列表数据
     * @param keyFunc   eg: User::getName
     * @param valueFunc eg: User::getAge
     * @return java.util.Map<K, java.util.Set < V>>
     * @author zhengqingya
     */
    public static <T, K, V> Map<K, Set<V>> toMapMultiSet(Collection<T> list, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (CollUtil.isEmpty(list)) {
            return Maps.newHashMap();
        }
        return list.stream().collect(Collectors.groupingBy(keyFunc, Collectors.mapping(valueFunc, Collectors.toSet())));
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        List<User> list = Lists.newArrayList(
                User.builder().name("小白").age(10).build(),
                User.builder().name("java").age(18).build(),
                User.builder().name("python").age(18).build(),
                User.builder().name("python").age(20).build()
        );

        Map<String, Integer> map1 = MyCollectionUtil.toMap(list, User::getName, User::getAge, (oldData, newData) -> newData);
        System.out.println(JSONUtil.toJsonStr(map1));

        Map<String, List<User>> map2 = MyCollectionUtil.toMapMultiList(list, User::getName);
        System.out.println(JSONUtil.toJsonStr(map2));

        Map<String, Set<User>> map3 = MyCollectionUtil.toMapMultiSet(list, User::getName, t -> t);
        System.out.println(JSONUtil.toJsonStr(map3));

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    static class User {
        private Boolean isDeleted;
        private String name;
        private Integer age;
    }

}
