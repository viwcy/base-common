package com.viwcy.basecommon.config;

import com.viwcy.basecommon.util.JwtUtil;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 * <p>
 *
 * @see ConditionalOnBean 当容器中有指定的Bean的条件下
 * @see ConditionalOnClass 当类路径下有指定的类的条件下
 * @see ConditionalOnExpression 基于SpEL表达式作为判断条件
 * @see ConditionalOnJava 基于JVM版本作为判断条件
 * @see ConditionalOnJndi 在JNDI存在的条件下查找指定的位置
 * @see ConditionalOnMissingBean 当容器中没有指定Bean的情况下
 * @see ConditionalOnMissingClass 当类路径下没有指定的类的条件下
 * @see ConditionalOnNotWebApplication 当前项目不是Web项目的条件下
 * @see ConditionalOnProperty 指定的属性是否有指定的值
 * @see ConditionalOnResource 类路径下是否有指定的资源
 * @see ConditionalOnSingleCandidate 当指定的Bean在容器中只有一个，或者在有多个Bean的情况下，用来指定首选的Bean
 * @see ConditionalOnWebApplication 当前项目是Web项目的条件下
 */
@Configuration
@ConditionalOnClass(value = {JwtUtil.class})
@EnableConfigurationProperties({JwtProperties.class})
public class JwtAutoConfiguration {

    /**
     * <pre>
     *     havingValue: 与value 或 name 组合使用，只有当value 或 name 对应的值与havingValue的值相同时，注入生效
     *     matchIfMissing: 该属性为true时，配置文件中缺少对应的value或name的对应的属性值，也会注入成功
     * </pre>
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "jwt.config", value = "enabled", havingValue = "true", matchIfMissing = true)
    public JwtUtil jwtUtil() {
        return JwtUtil.getInstance();
    }
}
