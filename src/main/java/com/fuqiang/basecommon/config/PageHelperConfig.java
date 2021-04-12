package com.fuqiang.basecommon.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * TODO //
 *
 * <p> Title: PageHelper </p >
 * <p> Description: PageHelper </p >
 * <p> History: 2021/4/9 11:05 </p >
 * <pre>
 *      Copyright (c) 2020 FQ (fuqiangvn@163.com) , ltd.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
@Configuration
public class PageHelperConfig {

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offset-as-page-num", "true");
        properties.setProperty("row-bounds-with-count", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("helperDialect", "mysql");    //配置mysql数据库的方言
        properties.setProperty("supportMethodsArguments", "true");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
