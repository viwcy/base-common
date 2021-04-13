package com.fuqiang.basecommon.aspect;

import com.fuqiang.basecommon.constant.LoginUserEnum;
import com.fuqiang.jwtcommon.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * TODO //
 *
 * <p> Title: LoginUserAspect </p >
 * <p> Description: LoginUserAspect </p >
 * <p> History: 2021/4/13 15:17 </p >
 * <pre>
 *      Copyright (c) 2020 FQ (fuqiangvn@163.com) , ltd.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
@Aspect
@Component
@Slf4j
public class LoginUserAspect {

    @Autowired
    private JwtUtil jwtUtil;

    @Pointcut("@annotation(com.fuqiang.basecommon.aspect.LoginUser)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void around(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        LoginUser loginUser = method.getAnnotation(LoginUser.class);
        if (LoginUserEnum.CREATE.equals(loginUser.type())) {
            create(point);
        } else if (LoginUserEnum.UPDATE.equals(loginUser.type())) {
            update(point);
        } else {
            createAndUpdate(point);
        }
    }

    private void create(JoinPoint point) {
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            Object argument = args[0];
            BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
            if (beanWrapper.isWritableProperty("createUser")) {
                beanWrapper.setPropertyValue("createUser", jwtUtil.getUserId());
            }
            log.info("LoginUserAspect CREATE = {}", ToStringBuilder.reflectionToString(argument, ToStringStyle.SHORT_PREFIX_STYLE));
        }
    }

    private void update(JoinPoint point) {
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            Object argument = args[0];
            BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
            if (beanWrapper.isWritableProperty("updateUser")) {
                beanWrapper.setPropertyValue("updateUser", jwtUtil.getUserId());
            }
            log.info("LoginUserAspect UPDATE = {}", ToStringBuilder.reflectionToString(argument, ToStringStyle.SHORT_PREFIX_STYLE));
        }
    }

    private void createAndUpdate(JoinPoint point) {
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            Object argument = args[0];
            BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
            if (beanWrapper.isWritableProperty("createUser")) {
                beanWrapper.setPropertyValue("createUser", jwtUtil.getUserId());
            }
            if (beanWrapper.isWritableProperty("updateUser")) {
                beanWrapper.setPropertyValue("updateUser", jwtUtil.getUserId());
            }
            log.info("LoginUserAspect CREATE AND UPDATE = {}", ToStringBuilder.reflectionToString(argument, ToStringStyle.SHORT_PREFIX_STYLE));
        }
    }

}
