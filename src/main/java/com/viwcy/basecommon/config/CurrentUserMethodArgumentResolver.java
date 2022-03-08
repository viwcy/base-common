package com.viwcy.basecommon.config;

import com.viwcy.basecommon.annotation.LoginUser;
import com.viwcy.basecommon.entity.User;
import com.viwcy.basecommon.util.JwtUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */

@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;

    public CurrentUserMethodArgumentResolver(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //判断方法参数是否带有@CurrentUser注解且参数类型为User或其子类
        return methodParameter.hasParameterAnnotation(LoginUser.class) && methodParameter.getParameterType().isAssignableFrom(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return jwtUtil.getUser(User.class);
    }

}
