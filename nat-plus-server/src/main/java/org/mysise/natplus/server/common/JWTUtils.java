package org.mysise.natplus.server.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mysise.natplus.server.entity.SysUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  jwt 工具类
 * <p>
 *
 * @author fanwenjie
 * @since 2020/3/11 23:43
 */
@Component
@Slf4j
public class JWTUtils {
    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.ttl}")
    private Long ttl;
    public static String SECRET;
    public static Long TTL_MILLIS;


    @PostConstruct
    public void run(){
        SECRET = key;
        TTL_MILLIS = ttl;
    }

    /**
     * <p>
     *  生成jwt-token
     * <p>
     *
     * @author haizi
     * @since 2020/3/11 23:49
     */
    public static String createToken(SysUser user){
        Date expireDate = new Date(System.currentTimeMillis() + TTL_MILLIS * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        return JWT.create()
                .withHeader(map)
                .withClaim("id", user.getId())//userId
                .withClaim("email", user.getEmail())
                .withExpiresAt(expireDate) //超时设置,设置过期的日期
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(SECRET)); //SECRET加密
    }

    /**
     * 校验token并解析token
     *
     * @author haizi
     * @Date 2020/3/12
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("token解码异常");
            //解码异常则抛出异常
            return null;
        }
        return jwt.getClaims();
    }

    /**
     * Token 是否过期验证
     *
     * @author haizi
     * @since 2020/3/12
     */
    public static boolean isTokenExpired (Date expirationTime) {
        return expirationTime.before(new Date());
    }
}