package com.viwcy.basecommon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@ConfigurationProperties(prefix = "jwt.config")
public class JwtProperties implements Serializable {
    private static final long serialVersionUID = -2796854826671034367L;

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
     * 过期时间，默认30min
     */
    private long expire = 30L;

    public JwtProperties() {
    }

    public JwtProperties(String header, String headerType, String prefix, String secret, long expire) {
        this.header = header;
        this.headerType = headerType;
        this.prefix = prefix;
        this.secret = secret;
        this.expire = expire;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeaderType() {
        return headerType;
    }

    public void setHeaderType(String headerType) {
        this.headerType = headerType;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}
