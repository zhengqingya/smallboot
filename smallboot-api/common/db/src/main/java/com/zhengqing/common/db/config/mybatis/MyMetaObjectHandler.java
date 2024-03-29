package com.zhengqing.common.db.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zhengqing.common.base.context.JwtUserContext;
import com.zhengqing.common.db.constant.MybatisConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * MyBatisPlus自定义字段自动填充处理类 - 实体类中使用 @TableField注解
 * </p>
 *
 * @author zhengqingya
 * @description 注意前端传值时要为null
 * @date 2019/8/18 1:46
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 用户id
        Long userId = Long.valueOf(JwtUserContext.getUserId());
        // 当前时间
        Date nowDate = new Date();

        // 判断对象中是否存在该参数，如果存在则插入数据
        if (metaObject.hasGetter(MybatisConstant.IS_DELETED)) {
            this.setFiledValue(MybatisConstant.IS_DELETED, false, metaObject);
        }
        if (metaObject.hasGetter(MybatisConstant.CREATE_BY)) {
            this.setFiledValue(MybatisConstant.CREATE_BY, userId, metaObject);
        }
        if (metaObject.hasGetter(MybatisConstant.CREATE_TIME)) {
            this.setFiledValue(MybatisConstant.CREATE_TIME, nowDate, metaObject);
        }
        if (metaObject.hasGetter(MybatisConstant.UPDATE_BY)) {
            this.setFiledValue(MybatisConstant.UPDATE_BY, userId, metaObject);
        }
        if (metaObject.hasGetter(MybatisConstant.UPDATE_TIME)) {
            this.setFiledValue(MybatisConstant.UPDATE_TIME, nowDate, metaObject);
        }
    }

    /**
     * 更新
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter(MybatisConstant.UPDATE_BY)) {
            this.setFiledValue(MybatisConstant.UPDATE_BY, Long.valueOf(JwtUserContext.getUserId()), metaObject);
        }
        if (metaObject.hasGetter(MybatisConstant.UPDATE_TIME)) {
            this.setFiledValue(MybatisConstant.UPDATE_TIME, new Date(), metaObject);
        }
    }


    private void setFiledValue(String fieldName, Object fieldVal, MetaObject metaObject) {
        Object value = this.getFieldValByName(fieldName, metaObject);
        if (value != null) {
            return;
        }
        this.setFieldValByName(fieldName, fieldVal, metaObject);
    }

}
