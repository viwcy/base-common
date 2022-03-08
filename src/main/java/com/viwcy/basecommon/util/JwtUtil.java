package com.viwcy.basecommon.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.viwcy.basecommon.config.JwtProperties;
import com.viwcy.basecommon.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.stereotype.Component;
import reactor.util.annotation.Nullable;

import javax.servlet.http.HttpServletRequest;
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
//@ConfigurationProperties(prefix = "jwt.config")
@Component
@Slf4j
public class JwtUtil {

    /**
     * 时间模板，线程安全
     */
    private static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    private static volatile JwtUtil jwtUtil = null;
    private final JwtProperties jwtProperties;
    private final HttpServletRequest request;

    private JwtUtil(JwtProperties jwtProperties, HttpServletRequest request) {
        this.jwtProperties = jwtProperties;
        this.request = request;
    }

    /**
     * @param
     * @return {@link com.alibaba.fastjson.JSONObject}
     * @Description TODO    根据身份ID标识，生成Token。jwtSecret:密钥，jwtExpire:过期时间（分钟）
     * @Param subject   jwt的主体，相当于唯一标识（推荐雪花ID）
     * @Param jwtExpire 过期时间，单位分钟
     * @date 2020/9/3 17:39
     * @author Fuqiang
     */
    public final <T> JSONObject createJwt(@Nullable final T entity, @Nullable final String subject) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + jwtProperties.getExpire() * 60 * 1000L);
        String jwt = Jwts.builder()
                .claim("user", entity)
                .setHeaderParam("typ", jwtProperties.getHeaderType())//类型
                .setSubject(subject)//代表这个JWT的主体，相当于唯一标识
                .setIssuedAt(now)//是一个时间戳，代表这个JWT的签发时间
                .setExpiration(expireDate)//过期时间
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())//签名
                .setNotBefore(now)//是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
                .compact();
        JSONObject json = new JSONObject();
        json.put("_token", jwt);
        json.put("_type", jwtProperties.getHeaderType());
        json.put("_header", jwtProperties.getHeader());
        json.put("_expire", FAST_DATE_FORMAT.format(expireDate));
        return json;
    }

    /**
     * @param jwt
     * @return {@link Claims}
     * @Description TODO    解析jwt
     * @date 2020/9/3 17:40
     * @author Fuqiang
     */
    public final Claims parsingJwt(String jwt) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(jwt.replace(jwtProperties.getPrefix() + " ", "")).getBody();
    }

    /**
     * @param object 入参类型
     * @return {@link T}
     * @throws Exception
     * @author Fau  2022/2/24 10:20
     * TODO 请求头获取用户信息
     */
    public final <T extends User> T getUser(@Nullable final Class<T> object) {
        String jwt = request.getHeader("Authorization");

        if (StringUtils.isBlank(jwt)) {
            log.error("Request header[Authorization] for the user is blank");
            return null;
        }
        try {
            Object user = this.parsingJwt(jwt).get("user");
            return JSON.parseObject(JSON.toJSONString(user), object);
        } catch (Exception e) {
            log.error("jwt get the user information , e = " + e);
        }
        return null;
    }

    public static final JwtUtil getInstance(JwtProperties jwtProperties, HttpServletRequest request) {
        if (jwtUtil == null) {
            synchronized (JwtUtil.class) {
                if (jwtUtil == null) {
                    jwtUtil = new JwtUtil(jwtProperties, request);
                }
            }
        }
        return jwtUtil;
    }
}
