package com.viwcy.basecommon.aspect;

import com.viwcy.basecommon.annotation.PopulateField;
import com.viwcy.basecommon.constant.FieldEnum;
import com.viwcy.basecommon.entity.User;
import com.viwcy.basecommon.util.JwtUtil;
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
import org.springframework.stereotype.Component;
import reactor.util.annotation.Nullable;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

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
public class PopulateFieldAspect {

    private final Date date = new Date();

    private final JwtUtil jwtUtil;

    public PopulateFieldAspect(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Pointcut("@annotation(com.viwcy.basecommon.annotation.PopulateField)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void around(@Nullable final JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        PopulateField populateField = method.getAnnotation(PopulateField.class);
        User user = jwtUtil.getUser(User.class);
        FieldEnum[] fieldEnums = populateField.fields();
        if (fieldEnums.length == 0) {
            return;
        }
        Arrays.stream(fieldEnums).forEach(type -> {
            if (type.equals(FieldEnum.CREATE)) {
                create(point, user);
            }
            if (type.equals(FieldEnum.UPDATE)) {
                update(point, user);
            }
        });
    }

    private final void create(@Nullable final JoinPoint point, @Nullable final User user) {
        Object[] args = point.getArgs();
        if (args == null && args.length <= 0) return;

        Object argument = args[0];
        BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
        createSet(beanWrapper, user.getId());
        log.info("LoginUserAspect CREATE = {}", ToStringBuilder.reflectionToString(argument, ToStringStyle.SHORT_PREFIX_STYLE));
    }

    private final void update(@Nullable final JoinPoint point, @Nullable final User user) {
        Object[] args = point.getArgs();
        if (args == null || args.length <= 0) return;

        Object argument = args[0];
        BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
        updateSet(beanWrapper, user.getId());
        log.info("LoginUserAspect UPDATE = {}", ToStringBuilder.reflectionToString(argument, ToStringStyle.SHORT_PREFIX_STYLE));
    }

    private final void createSet(@Nullable final BeanWrapper beanWrapper, @Nullable final Long createId) {
        if (beanWrapper.isWritableProperty("createId")) {
            beanWrapper.setPropertyValue("createId", createId);
        }
        if (beanWrapper.isWritableProperty("createTime")) {
            beanWrapper.setPropertyValue("createTime", date);
        }
    }

    private final void updateSet(@Nullable final BeanWrapper beanWrapper, @Nullable final Long updateId) {
        if (beanWrapper.isWritableProperty("updateId")) {
            beanWrapper.setPropertyValue("updateId", updateId);
        }
        if (beanWrapper.isWritableProperty("updateTime")) {
            beanWrapper.setPropertyValue("updateTime", date);
        }
    }
}
