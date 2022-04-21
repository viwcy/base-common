package com.viwcy.basecommon.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
@ConfigurationProperties(prefix = "jwt.config")
public class JwtProperties implements Serializable {

    private static final long serialVersionUID = 5593325345685327979L;

    /**
     * 头部认证名称
     */
    private String header = "Authorization";

    /**
     * JWT头部标识
     */
    private String headerType = "JWT";

    /**
     * 前缀
     */
    private String prefix = "Bearer";

    /**
     * 签名秘钥
     */
    private String secret = "viwcy4611";

    /**
     * refreshJwt签名秘钥(最好设置和secret不一样)
     */
    private String refreshSecret = "viwcy4611@gmail.com";

    /**
     * 过期时间，默认30min
     */
    private long expire = 30L;
}
