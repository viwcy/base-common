package com.viwcy.basecommon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;
    private final CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    public WebMvcConfiguration(JwtInterceptor jwtInterceptor, CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver) {
        this.jwtInterceptor = jwtInterceptor;
        this.currentUserMethodArgumentResolver = currentUserMethodArgumentResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
//                .excludePathPatterns("/loginOK")//对应的不拦截的请求
                .addPathPatterns("/**"); //拦截所有请求
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver);
    }

}
