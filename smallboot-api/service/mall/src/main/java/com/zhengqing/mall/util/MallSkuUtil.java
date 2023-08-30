package com.zhengqing.mall.util;

import com.zhengqing.mall.model.bo.PmsSkuSpecBO;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

/**
 * <p> 商城-商品-sku规格值计算工具类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/10/13 10:59
 */
public class MallSkuUtil {


    /**
     * 获取sku的属性值
     * 计算规则： 商品id:每一个商品属性名称值(用英文逗号分隔数据)  eg: 1:蓝色,XL
     * tips: 没有属性的情况下返回商品id
     *
     * @param spuId    商品id
     * @param attrList 一条商品规格的属性值
     * @return 计算后的sku属性值
     * @author zhengqingya
     * @date 2021/10/13 11:01
     */
    public static String getSkuStr(String spuId, List<PmsSkuSpecBO> attrList) {
        if (CollectionUtils.isEmpty(attrList)) {
            return String.valueOf(spuId);
        }
        StringJoiner attrValueIdSj = new StringJoiner(",");
        // 升序排序
        attrList.sort(Comparator.comparing(PmsSkuSpecBO::getAttrValueId));
        attrList.forEach(item -> attrValueIdSj.add(String.valueOf(item.getAttrValueName())));
        return String.format("%s:%s", spuId, attrValueIdSj);
    }


    /**
     * 校验sku id值是否与传入属性数据符合
     *
     * @param spuId    商品id
     * @param attrList 商品属性
     * @param skuId    商品规格ID
     * @return true->数据符合 false->数据不符合
     * @author zhengqingya
     * @date 2021/10/13 11:01
     */
    public static boolean check(String spuId, List<PmsSkuSpecBO> attrList, String skuId) {
        return getSkuStr(spuId, attrList).equals(skuId);
    }

}
