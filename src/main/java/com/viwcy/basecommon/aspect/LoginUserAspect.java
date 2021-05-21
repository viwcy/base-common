package com.viwcy.basecommon.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.viwcy.basecommon.constant.LoginUserEnum;
import com.viwcy.basecommon.entity.UserEntity;
import com.viwcy.jwtcommon.util.JwtUtil;
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
import java.time.LocalDateTime;

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

    @Pointcut("@annotation(com.viwcy.basecommon.aspect.LoginUser)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void around(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        LoginUser loginUser = method.getAnnotation(LoginUser.class);
        UserEntity userEntity = JSONObject.parseObject(JSON.toJSONString(jwtUtil.getUserInfo()), UserEntity.class);
        if (LoginUserEnum.CREATE.equals(loginUser.type())) {
            create(point, userEntity);
        } else if (LoginUserEnum.UPDATE.equals(loginUser.type())) {
            update(point, userEntity);
        } else {
            createAndUpdate(point, userEntity);
        }
    }

    private void create(JoinPoint point, UserEntity userEntity) {
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            Object argument = args[0];
            BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
            create(beanWrapper, userEntity);
            log.info("LoginUserAspect CREATE = {}", ToStringBuilder.reflectionToString(argument, ToStringStyle.SHORT_PREFIX_STYLE));
        }
    }

    private void update(JoinPoint point, UserEntity userEntity) {
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            Object argument = args[0];
            BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
            update(beanWrapper, userEntity);
            log.info("LoginUserAspect UPDATE = {}", ToStringBuilder.reflectionToString(argument, ToStringStyle.SHORT_PREFIX_STYLE));
        }
    }

    private void createAndUpdate(JoinPoint point, UserEntity userEntity) {
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            Object argument = args[0];
            BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
            create(beanWrapper, userEntity);
            update(beanWrapper, userEntity);
            log.info("LoginUserAspect CREATE AND UPDATE = {}", ToStringBuilder.reflectionToString(argument, ToStringStyle.SHORT_PREFIX_STYLE));
        }
    }

    private void create(BeanWrapper beanWrapper, UserEntity userEntity) {
        if (beanWrapper.isWritableProperty("createUser")) {
            beanWrapper.setPropertyValue("createUser", userEntity.getId());
        }
        if (beanWrapper.isWritableProperty("createId")) {
            beanWrapper.setPropertyValue("createId", userEntity.getId());
        }
        if (beanWrapper.isWritableProperty("createName")) {
            beanWrapper.setPropertyValue("createName", userEntity.getNickname());
        }
        if (beanWrapper.isWritableProperty("createTime")) {
            beanWrapper.setPropertyValue("createTime", LocalDateTime.now());
        }
    }

    private void update(BeanWrapper beanWrapper, UserEntity userEntity) {
        if (beanWrapper.isWritableProperty("updateUser")) {
            beanWrapper.setPropertyValue("updateUser", userEntity.getId());
        }
        if (beanWrapper.isWritableProperty("updateId")) {
            beanWrapper.setPropertyValue("updateId", userEntity.getId());
        }
        if (beanWrapper.isWritableProperty("updateName")) {
            beanWrapper.setPropertyValue("updateName", userEntity.getNickname());
        }
        if (beanWrapper.isWritableProperty("updateTime")) {
            beanWrapper.setPropertyValue("updateTime", LocalDateTime.now());
        }
    }
}
