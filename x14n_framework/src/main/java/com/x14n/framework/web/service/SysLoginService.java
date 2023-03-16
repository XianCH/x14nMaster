package com.x14n.framework.web.service;

import com.x14n.common.constant.Constants;
import com.x14n.common.core.domain.model.LoginUser;
import com.x14n.common.core.redis.RedisCache;
import com.x14n.common.utils.StringUtils;
import com.x14n.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class SysLoginService {

    @Autowired
    RedisCache redisCache;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    ISysConfigService configService;

    public String login(String username, String password, String code, String uuid) {
        boolean captchaOnOff = configService.selectCaptchaOnOff();
        if (captchaOnOff) {
            validateCaptcha(username, code, uuid);
        }
        Authentication authentication = null;


        authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(username, password));

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return tokenService.createToken(loginUser);
    }

    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {

        }
        if (!code.equalsIgnoreCase(captcha)) {

        }
    }
}
