package com.fuqiang.basecommon.exception;

import com.fuqiang.basecommon.common.ResultEntity;
import com.fuqiang.basecommon.constant.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO //、、在Controller层进行异常拦截和包装，给前端返回
 *
 * <p> Title: GlobalExceptionHandler </p >
 * <p> Description: GlobalExceptionHandler </p >
 * <p> History: 2020/9/27 14:35 </p >
 * <pre>
 *      Copyright (c) 2020 FQ (fuqiangvn@163.com) , ltd.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResultEntity<?> exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        log.error("Request path = [{}]，Error Message = [{}]", request.getRequestURI(), e.toString());
        //业务异常
        if (e instanceof BusinessException) {
            BusinessException ex = (BusinessException) e;
            return new ResultEntity<>(ExceptionEnum.BusinessException.getCode(), ex.getMessage(), null);
        } else if (e instanceof NullPointerException) {
            return new ResultEntity<>(ExceptionEnum.NullPointerException.getCode(), e.toString(), null);
        } else if (e instanceof ArithmeticException) {
            return new ResultEntity<>(ExceptionEnum.ArithmeticException.getCode(), e.toString(), null);
        } else {
            return new ResultEntity<>(ExceptionEnum.SystemException.getCode(), e.toString(), null);
        }
    }

}
