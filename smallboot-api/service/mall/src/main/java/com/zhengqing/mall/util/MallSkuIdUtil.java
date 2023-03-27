package com.zhengqing.mall.util;

import com.zhengqing.mall.common.model.bo.PmsSkuSpecBO;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

/**
 * <p> 商城-商品-sku规格id值计算工具类 </p>
 *
 * @author zhengqingya
 * @description 计算规则：
 * 商品id+每一个商品属性的id值，用英文冒号分隔数据
 * @date 2021/10/13 10:59
 */
public class MallSkuIdUtil {

    /**
     * 获取sku id
     *
     * @param spuId    商品id
     * @param attrList 商品属性
     * @return 计算后的sku id值
     * @author zhengqingya
     * @date 2021/10/13 11:01
     */
    public static String getId(String spuId, List<PmsSkuSpecBO> attrList) {
        if (CollectionUtils.isEmpty(attrList)) {
            return String.valueOf(spuId);
        }
        StringJoiner attrValueIdSj = new StringJoiner(":");
        // 升序排序
        attrList.sort(Comparator.comparing(PmsSkuSpecBO::getAttrValueId));
        attrList.forEach(item -> attrValueIdSj.add(String.valueOf(item.getAttrValueId())));
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
        return getId(spuId, attrList).equals(skuId);
    }

}
