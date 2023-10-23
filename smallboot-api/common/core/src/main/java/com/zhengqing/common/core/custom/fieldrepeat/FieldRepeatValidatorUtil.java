package com.zhengqing.common.core.custom.fieldrepeat;

import com.zhengqing.common.base.exception.MyException;
import com.zhengqing.common.base.util.ApplicationContextUtil;
import com.zhengqing.common.base.util.MyBeanUtil;
import com.zhengqing.common.base.util.MyStringUtil;
import com.zhengqing.common.db.mapper.MyBaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据库字段内容重复判断处理工具类
 * </p>
 *
 * @author zhengqingya
 * @date 2019/9/10 9:28
 */
@Slf4j
public class FieldRepeatValidatorUtil {

    /**
     * 表名
     */
    private String TABLE_NAME;

    /**
     * 数据库对应实体类主键id字段属性名
     */
    private String idName;
    /**
     * 数据库主键id字段属性名
     */
    private String idDbName;

    /**
     * 数据库主键id字段属性值
     */
    private Integer idValue;

    /**
     * 校验字段名组
     */
    private String[] fieldNames;

    /**
     * 校验辅助字段组值 - 字符串、数字、对象...
     */
    private List<Object> fieldValues = new LinkedList<>();

    /**
     * 校验辅助字段组固定值 - 字符串、数字、对象...
     */
    private String[] fieldFixedValues;

    /**
     * 储存校验辅助字段对应数据库字段值
     */
    private List<String> DB_FIELDS;

    /**
     * 顶级父包名
     */
    private final String PACKAGE_NAME = "java.lang.Object";

    /**
     * 实体类对象值
     */
    private Object object;

    /**
     * 错误提示信息
     */
    private String message;

    /**
     * @param tableName：                数据库表名
     * @param idDbName：                 数据库主键id字段属性名
     * @param fieldNames：校验字段组
     * @param dbFieldNames：数据库校验字段组
     * @param fieldFixedValues：校验字段组固定值
     * @param object：对象数据
     * @param message：回调到前端提示消息
     */
    public FieldRepeatValidatorUtil(String tableName, String idDbName, String[] fieldNames, String[] dbFieldNames,
                                    String[] fieldFixedValues, Object object, String message) {
        this.TABLE_NAME = tableName;

        this.idDbName = idDbName;
        // id 转 驼峰命名
        this.idName = MyStringUtil.dbStrToHumpLower(idDbName);

        this.fieldNames = fieldNames;
        this.DB_FIELDS = new ArrayList<>(Arrays.asList(dbFieldNames));
        this.fieldFixedValues = fieldFixedValues;
        this.object = object;
        this.message = message;
        this.getFieldValue();
    }

    /**
     * 校验数据
     *
     * @return boolean
     */
    public boolean fieldRepeat() {
        // 4、 校验字段内容是否重复
        // 工厂模式 + ar动态语法 -> 【这里已修改为`MyBaseMapper`方式查询数据】
        // Model entity = (Model)object;
        // if (object.getClass().getAnnotation(TableName.class) == null) {
        // try {
        // entity = (Model)object.getClass().getSuperclass().newInstance();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // list = entity.selectList((Wrapper)new QueryWrapper().allEq(queryMap));

        // 5、拼接sql
        Map<Object, Object> queryMap = new HashMap<>(5);
        if (this.fieldFixedValues.length == 0) {
            for (int i = 0; i < this.fieldNames.length; i++) {
                queryMap.put(this.DB_FIELDS.get(i), this.fieldValues.get(i));
            }
        } else {
            for (int i = 0; i < this.fieldNames.length; i++) {
                if (StringUtils.isBlank(this.fieldFixedValues[i])) {
                    queryMap.put(this.DB_FIELDS.get(i), this.fieldValues.get(i));
                } else {
                    queryMap.put(this.DB_FIELDS.get(i), this.fieldFixedValues[i]);
                }

            }
        }

        List<Map<String, Object>> list = ApplicationContextUtil.getApplicationContext().getBean(MyBaseMapper.class).selectListBySomeColumn(this.TABLE_NAME, queryMap);

        // 6、如果数据重复返回false -> 再返回自定义错误消息到前端
        if (!CollectionUtils.isEmpty(list)) {
            if (this.idValue == null) {
                throw new MyException(this.message);
            } else {
                if (list.size() > 1) {
                    throw new MyException(this.message);
                }
                // 获取list中指定字段属性值 - 这里只获取主键id
                List<Object> idList = (List<Object>) MyBeanUtil.getFieldList(list, this.idDbName);
                boolean isContainsIdValue = false;
                for (Object itemId : idList) {
                    if (itemId.toString().equals(this.idValue.toString())) {
                        isContainsIdValue = true;
                        break;
                    }
                }
                if (!isContainsIdValue) {
                    throw new MyException(this.message);
                }
            }
        }
        return true;
    }

    /**
     * 获取主键id、校验字段、以及对应数据库字段值
     */
    private void getFieldValue() {
        try {
            // 1、获取所有的字段
            Class clz = this.object.getClass();
            // 当父类为null的时候说明到达了最上层的父类(Object类) -> 作递归取父类属性值使用
            Map<String, Field> fieldMap = new HashMap<>();
            while (clz != null && !this.PACKAGE_NAME.equals(clz.getName().toLowerCase())) {
                fieldMap.putAll(
                        Arrays.stream(clz.getDeclaredFields()).collect(Collectors.toMap(Field::getName, field -> field)));
                // 得到父类,然后赋给自己
                clz = clz.getSuperclass();
            }

            // 2、取校验字段值
            for (int i = 0; i < this.fieldNames.length; i++) {
                Field field = fieldMap.get(this.fieldNames[i]);
                if (field == null) {
                    this.fieldValues.add(null);
                } else {
                    // 设置对象中成员 属性private为可读
                    field.setAccessible(true);
                    // 校验字段名的值 【 fieldNames中第一个字段为校验字段，其后为辅助校验字段 】
                    Object fieldValue = field.get(this.object);
                    this.fieldValues.add(fieldValue);
                }
            }

            // 3、取主键id字段值 -> 作用：判断是插入还是更新操作
            Field fieldId = fieldMap.get(this.idName);
            if (fieldId != null) {
                fieldId.setAccessible(true);
                this.idValue = (Integer) fieldId.get(this.object);
            }
        } catch (Exception e) {
            throw new MyException("数据库字段内容验重校验取值失败：" + e.toString());
        }
    }

}
