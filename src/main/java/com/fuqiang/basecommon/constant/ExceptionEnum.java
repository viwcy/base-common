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
    BusinessException(401, "BusinessException"),
    //NPE
    NullPointerException(500, "NullPointerException"),
    //数据异常
    ArithmeticException(500, "ArithmeticException"),
    //系统异常
    SystemException(501, "System error");

    private int code;
    private String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
