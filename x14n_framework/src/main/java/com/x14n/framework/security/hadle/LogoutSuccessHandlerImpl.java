package com.x14n.framework.security.hadle;

import com.alibaba.fastjson.JSON;
import com.x14n.common.constant.HttpStatus;
import com.x14n.common.core.domain.AjaxResult;
import com.x14n.common.core.domain.model.LoginUser;
import com.x14n.common.utils.ServletUtils;
import com.x14n.common.utils.StringUtils;
import com.x14n.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);

        if (StringUtils.isNotNull(loginUser)){
            String userName = loginUser.getUsername();
            tokenService.delLoginUser(loginUser.getToken());
            //输出日志。。。
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(HttpStatus.SUCCESS, "退出成功")));
    }
}
