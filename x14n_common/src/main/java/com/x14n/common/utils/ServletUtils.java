package com.x14n.common.utils;

import com.x14n.common.core.text.Convert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletUtils {

    public static void renderString(HttpServletResponse response,String string){
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HttpServletRequest getRequest(){
        return getRequestAttributes().getRequest();
    }

    private static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    public static String getParameter(String name)
    {
        return getRequest().getParameter(name);
    }


    public static Boolean getParameterToBool(String name)
    {
        return Convert.toBool(getRequest().getParameter(name));
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name)
    {
        return Convert.toInt(getRequest().getParameter(name));
    }
}
