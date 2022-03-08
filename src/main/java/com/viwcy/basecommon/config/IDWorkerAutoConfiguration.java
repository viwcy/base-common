package com.viwcy.basecommon.config;

import com.viwcy.basecommon.util.IDWorker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Configuration
@ConditionalOnClass(value = {IDWorker.class})
@EnableConfigurationProperties({IDWorkerProperties.class})
public class IDWorkerAutoConfiguration {

    private final IDWorkerProperties properties;

    public IDWorkerAutoConfiguration(IDWorkerProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "snowflake.config", value = "enabled", havingValue = "true", matchIfMissing = true)
    public IDWorker idWorker() {
        return IDWorker.getInstance(properties);
    }
}
