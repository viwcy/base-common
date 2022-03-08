package com.viwcy.basecommon.config;

import com.viwcy.basecommon.annotation.IgnoreToken;
import com.viwcy.basecommon.exception.BusinessException;
import com.viwcy.basecommon.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;
    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtProperties jwtProperties, JwtUtil jwtUtil) {
        this.jwtProperties = jwtProperties;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        IgnoreToken ignoreToken;
        if (handler instanceof HandlerMethod) {
            ignoreToken = ((HandlerMethod) handler).getMethodAnnotation(IgnoreToken.class);
        } else {
            return true;
        }

        if (ignoreToken != null) {
            return true;
        }
        //校验token
        String token = request.getHeader(jwtProperties.getHeader());
        if (StringUtils.isBlank(token)) {
            throw new BusinessException("JWT does not exist");
        }
        try {
            jwtUtil.parsingJwt(token);
        } catch (ExpiredJwtException e) {
            throw new BusinessException("JWT has expired");
        } catch (Exception e) {
            throw new BusinessException("JWT validation error");
        }
        return true;
    }
}
