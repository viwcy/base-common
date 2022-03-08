package com.viwcy.basecommon.config;

import com.viwcy.basecommon.util.JwtUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Configuration
@ConditionalOnClass(value = {JwtUtil.class})
@EnableConfigurationProperties({JwtProperties.class})
public class JwtAutoConfiguration {

    private final JwtProperties jwtProperties;
    private final HttpServletRequest request;

    public JwtAutoConfiguration(JwtProperties jwtProperties, HttpServletRequest request) {
        this.jwtProperties = jwtProperties;
        this.request = request;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "jwt.config", value = "enabled", havingValue = "true", matchIfMissing = true)
    public JwtUtil jwtUtil() {
        return JwtUtil.getInstance(jwtProperties, request);
    }
}
