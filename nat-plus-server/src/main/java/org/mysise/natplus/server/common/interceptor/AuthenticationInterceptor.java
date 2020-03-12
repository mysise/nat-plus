package org.mysise.natplus.server.common.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import org.mysise.natplus.common.exception.BizException;
import org.mysise.natplus.server.common.JWTUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * token拦截器
 *
 * @author haizi
 * @since 2020/03/12 16:25
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token

        // 执行认证
        if (token == null) {
            throw new BizException("无token，请重新登录");
        }

        Map<String, Claim> claimMap = JWTUtils.verifyToken(token);
        if (claimMap == null) {
            throw new BizException("401");
        }

        if(JWTUtils.isTokenExpired(claimMap.get("exp").asDate())){
            throw new BizException("token失效，请重新登录");
        }
        return true;
    }
}
