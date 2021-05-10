package com.viwcy.basecommon.aspect;

import com.viwcy.basecommon.constant.LoginUserEnum;

import java.lang.annotation.*;

/**
 * TODO //
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
public @interface LoginUser {

    /**
     * 操作类型
     * LoginUserEnum.CREATE     注入字段createUser
     * LoginUserEnum.UPDATE     注入字段updateUser
     * LoginUserEnum.CREATE_AND_UPDATE      注入字段createUser和updateUser
     */
    LoginUserEnum type() default LoginUserEnum.CREATE_AND_UPDATE;
}
