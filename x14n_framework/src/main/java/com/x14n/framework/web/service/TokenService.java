package com.x14n.framework.web.service;


import com.x14n.common.constant.Constants;
import com.x14n.common.core.domain.model.LoginUser;
import com.x14n.common.core.redis.RedisCache;
import com.x14n.common.utils.ServletUtils;
import com.x14n.common.utils.StringUtils;
import com.x14n.common.utils.ip.AddressUtils;
import com.x14n.common.utils.ip.IpUtils;
import com.x14n.common.utils.uuid.IdUtils;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TokenService {

    @Value("${token.header}")
    private String header;

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;


    @Autowired
    private RedisCache redisCache;

    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {

            try {
                Claims claims = parseToken(token);
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                LoginUser user = redisCache.getCacheObject(userKey);
                return user;
            } catch (Exception e) {
            }
        }
        return null;

    }

    public void verifyToken(LoginUser loginUser) {
        Long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();

        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }


    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_SECOND);
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }


    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    public String createToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        setUserAgent(loginUser);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        return createToken(claims);

    }

    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    public void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIp(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    private String getTokenKey(String uuid) {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }

    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)){
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    public void setLoginUser(LoginUser loginUser)
    {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken()))
        {
            refreshToken(loginUser);
        }
    }
}

