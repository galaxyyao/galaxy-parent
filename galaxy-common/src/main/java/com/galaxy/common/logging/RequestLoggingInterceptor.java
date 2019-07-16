package com.galaxy.common.logging;

import com.galaxy.common.constant.LoggingConstant;
import com.galaxy.common.constant.RequestConstant;
import com.galaxy.common.id.IdWorker;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Component
public class RequestLoggingInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger("httpTraceLog");

    @Autowired
    Environment env;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String requestAuthHeader = request.getHeader(RequestConstant.REQUEST_HEADER_KEY_AUTHORIZATION);
        final String deviceId = request.getHeader(RequestConstant.REQUEST_HEADER_KEY_DEVICE_ID);
        String username = "";
        if (!Strings.isNullOrEmpty(requestAuthHeader) && requestAuthHeader.startsWith(RequestConstant.AUTHORIZATION_TOKEN_PREFIX)) {
            String authToken = requestAuthHeader.substring(RequestConstant.AUTHORIZATION_TOKEN_PREFIX.length());
            try {
                username = getUsernameFromToken(authToken);
            } catch (IllegalArgumentException | MalformedJwtException e) {
                // 未能成功解析token
                username = "no auth jwt secret";
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
            }
        }
        Long startTime = Instant.now().toEpochMilli();
        Long requestId = IdWorker.generateId();
        String userTokenLog = Strings.isNullOrEmpty(username) ? "no user token" : "loginId:" + username;
        logger.info("[request.log]Request id:" + requestId + ", ip:" + getIp(request) + ", deviceId:" + deviceId + ", url:" + request.getRequestURL().toString() +
                ", start time=" + LocalDateTime.now() + ", " + userTokenLog);

        request.setAttribute("requestId", requestId);
        request.setAttribute("startTime", startTime);
        request.setAttribute("loginId", username);
        return true;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        String secret = env.getProperty("auth.jwt.secret");
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {

        Long startTime = (Long) request.getAttribute("startTime");
        Long requestId = (Long) request.getAttribute("requestId");
        String loginId = (String) request.getAttribute("loginId");
        String userTokenLog = Strings.isNullOrEmpty(loginId) ? "no user token" : "userId:" + loginId;
        logger.info("[request.log]Request id:" + requestId + ", url:" + request.getRequestURL().toString() +
                ", time taken=" + (Instant.now().toEpochMilli() - startTime) + "ms, " + userTokenLog);
    }

    public String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!Strings.isNullOrEmpty(ip) && !LoggingConstant.IP_UNKNOWN.equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!Strings.isNullOrEmpty(ip) && !LoggingConstant.IP_UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
