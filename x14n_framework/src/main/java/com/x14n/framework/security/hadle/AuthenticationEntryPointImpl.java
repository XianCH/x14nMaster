package com.x14n.framework.security.hadle;

import com.alibaba.fastjson.JSON;
import com.x14n.common.constant.HttpStatus;
import com.x14n.common.core.domain.AjaxResult;
import com.x14n.common.utils.ServletUtils;
import com.x14n.common.utils.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;


@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StringUtils.format("请求访问:{},认证失败，无法访问资源", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(msg)));
    }
}
