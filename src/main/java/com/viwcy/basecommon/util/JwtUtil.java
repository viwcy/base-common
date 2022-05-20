package com.viwcy.basecommon.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.viwcy.basecommon.config.JwtProperties;
import com.viwcy.basecommon.entity.User;
import com.viwcy.basecommon.exception.BusinessException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * TODO //jwt工具模板
 *
 * <p> Title: JwtTemplate </p>
 * <p> Description: JwtTemplate </p>
 * <p> History: 2020/9/4 23:02 </p>
 * <pre>
 *      Copyright: Create by FQ, ltd. Copyright(©) 2020.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
@Slf4j
public class JwtUtil {

    /**
     * 时间模板，线程安全
     */
    private static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    private static volatile JwtUtil jwtUtil = null;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private HttpServletRequest request;

    public final JSONObject createJwt(@Nullable final User entity, @Nullable final String subject) {

        Date now = getNow();
        Date expireDate = new Date(now.getTime() + jwtProperties.getExpire() * 60 * 1000L);
        Date refreshExpireDate = new Date(2L * now.getTime());
        String accessToken = buildJwt(entity, expireDate, subject, now, jwtProperties.getSecret());
        String refreshToken = buildJwt(entity, refreshExpireDate, subject, now, jwtProperties.getRefreshSecret());
        JSONObject json = new JSONObject();
        json.put("access_token", accessToken);
        json.put("refresh_token", refreshToken);
        json.put("type", jwtProperties.getHeaderType());
        json.put("header", jwtProperties.getHeader());
        json.put("expire", FAST_DATE_FORMAT.format(expireDate));
        return json;
    }

    /**
     * 线程安全获取时间
     */
    private final synchronized Date getNow() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 构建jwt
     */
    private final String buildJwt(Object claim, Date expiration, String subject, Date now, String secret) {
        return Jwts.builder()
                .claim("user", claim)
                .setHeaderParam("typ", jwtProperties.getHeaderType())//类型
                .setSubject(subject)//代表这个JWT的主体，相当于唯一标识
                .setIssuedAt(now)//是一个时间戳，代表这个JWT的签发时间
                .setExpiration(expiration)//过期时间
                .signWith(SignatureAlgorithm.HS256, secret)//签名
                .setNotBefore(now)//是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
                .compact();
    }

    public final Claims parsingJwt(@Nullable String jwt) {
        try {
            return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(jwt.replace(jwtProperties.getPrefix() + " ", "")).getBody();
        } catch (ExpiredJwtException e) {
            throw new BusinessException("Parsing token exception , e = " + e);
        }
    }

    /**
     * 刷新jwt
     */
    public final JSONObject refreshJwt(@Nullable final String jwt, @Nullable final String subject) {
        Claims body;
        try {
            body = Jwts.parser().setSigningKey(jwtProperties.getRefreshSecret()).parseClaimsJws(jwt.replace(jwtProperties.getPrefix() + " ", "")).getBody();
        } catch (Exception e) {
            throw new BusinessException("Parsing token exception , e = " + e);
        }
        User user = JSON.parseObject(JSON.toJSONString(body.get("user")), User.class);
        return createJwt(user, subject);
    }

    public final User getUser() {

        String jwt = request.getHeader(jwtProperties.getHeader());
        if (StringUtils.isBlank(jwt)) {
            throw new BusinessException("Request header[Authorization] for the user is blank");
        }
        try {
            Object user = this.parsingJwt(jwt).get("user");
            return JSON.parseObject(JSON.toJSONString(user), User.class);
        } catch (Exception e) {
            throw new BusinessException("jwt get the user information , e = " + e);
        }
    }

    public static final JwtUtil getInstance() {
        if (jwtUtil == null) {
            synchronized (JwtUtil.class) {
                if (jwtUtil == null) {
                    jwtUtil = new JwtUtil();
                }
            }
        }
        return jwtUtil;
    }
}
