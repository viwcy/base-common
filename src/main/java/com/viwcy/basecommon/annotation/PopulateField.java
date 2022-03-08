package com.viwcy.basecommon.annotation;

import com.viwcy.basecommon.constant.FieldEnum;

import java.lang.annotation.*;

/**
 * TODO //  填充创建时间/用户(create_time,create_id)，更新时间(update_time,update_id)/用户
 *
 * <p> Title: LoginUser </p >
 * <p> Description: LoginUser </p >
 * <p> History: 2021/4/13 15:12 </p >
 * <pre>
 *      Copyright (c) 2020 FQ (fuqiangvn@163.com) , ltd.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PopulateField {

    FieldEnum[] fields() default {FieldEnum.CREATE, FieldEnum.UPDATE};
}
