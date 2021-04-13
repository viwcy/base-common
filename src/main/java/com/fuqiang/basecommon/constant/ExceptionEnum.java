package com.fuqiang.basecommon.constant;

import lombok.Getter;

/**
 * TODO //、、全局异常处理
 *
 * <p> Title: ExceptionEnum </p >
 * <p> Description: ExceptionEnum </p >
 * <p> History: 2021/4/13 10:55 </p >
 * <pre>
 *      Copyright (c) 2020 FQ (fuqiangvn@163.com) , ltd.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
@Getter
public enum ExceptionEnum {

    //业务异常
    BusinessException(30001, "BusinessException"),
    //NPE
    NullPointerException(30002, "NullPointerException"),
    //系统异常
    SystemException(30500, "System error");

    private int code;
    private String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
